package teambuilder.team.legacy;

import indexer.pokemon.infrastructure.injector.PokemonInfrastructureInjector;
import indexer.pokemon.infrastructure.persistence.FileSystem.PokemonRepositoryImpl;
import teambuilder.pokemon.legacy.Pokes;
import teambuilder.team.infrastructure.injector.TeamInfrastructureInjector;
import teambuilder.team.infrastructure.persistence.FileSystem.TeamRepositoryImpl;
import shared.infrastructure.service.ReaderService;

public class Equips {

    // Comprovar si l'equip és vàid
    private static boolean equipValid(String[][][] equip) {
        boolean correcte = true;
        for (int i = 0; i < equip.length && correcte; i++) {
            if (equip[i] != null) {
                for (int j = 0; j < equip[i][2].length && correcte; j++) {
                    if (equip[i][2][j].equals("")) {
                        correcte = false;
                    }
                }
            }
        }
        return correcte;
    }

    // Comprovar si l'equip és vàid
    private static int seguentPokemon(String[][][] equip) {
        int n = 6;
        for (int i = 5; i > -1; i--) {
            if (equip[i] == null) {
                n = i;
            }
        }
        return n;
    }

    // Iniciar l'array d'equip
    private static String[][] iniciarPokemon() {
        String[][] poke = {
            new String[4],
            new String[6],
            new String[4],
            new String[6],
            new String[7],
            new String[6]
        };

        for (int i = 0; i < poke.length; i++) {
            for (int j = 0; j < poke[i].length; j++) {
                poke[i][j] = "";
            }
        }

        for (int i = 0; i < 6; i++) {
            poke[4][i] = "0";
            poke[5][i] = "31";
        }

        poke[1][0] = "100";
        poke[1][2] = "255";
        poke[1][3] = "false";
        poke[4][6] = "25";

        return poke;
    }

    private static String[][] importarPokemon() {
        String[] pokemons = pokemonFileSystemRepository().list();
        System.out.println("Saved pokemons:");
        for (String pokemon : pokemons) {
            System.out.println(pokemon);
        }

        return pokemonFileSystemRepository().read(ReaderService.read());
    }

    private static void exportarPokemon(String[][] pokemon) {
        pokemonFileSystemRepository().write(pokemon, ReaderService.read());
    }

    private static void exportarEquip(String[][][] equip) {
        teamFileSystemRepository().write(equip, ReaderService.read());
    }

    public static void crearEquip(String[][][] equip) {
        boolean sortir = false;
        int num;
        String[] s;

        do {
            try {

                // Opcions del menú
                System.out.printf("%nPOKETEXT: CONSTRUCTOR D'EQUIPS%n");
                System.out.println("S. Escollir un nou Pokèmon");
                System.out.println("M. Editar un Pokèmon (1-6)");
                System.out.println("D. Eliminar un Pokèmon (1-6)");
                System.out.println("V. Veure els Pokèmons");
                System.out.println("I. Importar Pokèmon");
                System.out.println("E. Exportar Pokèmon (1-6)");
                System.out.println("F. Finalitzar i guardar l'equip");
                System.out.println("Q. Cancelar i sortir");
                s = ReaderService.read().split(" ");

                // Seleccions del menú principal
                if ((s[0].equalsIgnoreCase("s")) && (s.length == 1)) {
                    if ((num = seguentPokemon(equip)) < 6) {
                        equip[num] = Pokes.escollirPoke(iniciarPokemon());
                    } else {
                        System.out.println("No pots escollir més Pokèmons");
                    }
                } else if ((s[0].equalsIgnoreCase("m")) && (s.length == 2)) {
                    if (equip[Integer.parseInt(s[1]) - 1] != null) {
                        equip[Integer.parseInt(s[1]) - 1] = Pokes.editarPoke(equip[Integer.parseInt(s[1]) - 1]);
                    } else {
                        System.out.println("No hi ha cap Pokèmon");
                    }
                } else if ((s[0].equalsIgnoreCase("d")) && (s.length == 2)) {
                    System.out.println("Eliminant Pokèmon...");
                    equip[Integer.parseInt(s[1]) - 1] = null;
                } else if ((s[0].equalsIgnoreCase("v")) && (s.length == 1)) {
                    for (String[][] poke : equip) {
                        if (poke != null) {
                            Pokes.imprimirPoke(poke);
                        }
                    }
                } else if ((s[0].equalsIgnoreCase("i")) && (s.length == 1)) {
                    if ((num = seguentPokemon(equip)) < 6) {
                        equip[num] = importarPokemon();
                    } else {
                        System.out.println("No pots escollir més Pokèmons");
                    }
                } else if ((s[0].equalsIgnoreCase("e")) && (s.length == 2)) {
                    if (equip[Integer.parseInt(s[1]) - 1] != null) {
                        exportarPokemon(equip[Integer.parseInt(s[1]) - 1]);
                    } else {
                        System.out.println("No hi ha cap Pokèmon");
                    }
                } else if ((s[0].equalsIgnoreCase("f")) && (s.length == 1)) {
                    if (equipValid(equip)) {
                        exportarEquip(equip);
                        sortir = true;
                    } else {
                        System.out.println("L'equip no és valid i no es pot guardar");
                    }
                } else if ((s[0].equalsIgnoreCase("q")) && (s.length == 1)) {
                    sortir = true;
                } else {
                    System.out.println("Selecció incorrecte");
                }
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException ex) {
                System.err.println("Paràmetres incorrectes");
            }
        } while (!sortir);
    }

    private static PokemonRepositoryImpl pokemonFileSystemRepository() {
        return PokemonInfrastructureInjector.injectFileSystemPokemonRepository();
    }

    private static TeamRepositoryImpl teamFileSystemRepository() {
        return TeamInfrastructureInjector.injectFileSystemTeamRepository();
    }
}
