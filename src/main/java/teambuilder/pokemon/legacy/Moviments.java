package teambuilder.pokemon.legacy;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import indexer.move.application.GetMoves.GetMovesRequest;
import indexer.move.infrastructure.injector.MoveApplicationInjector;
import shared.core.infrastructure.Presentation.MatrixPrinter;
import shared.core.infrastructure.Service.LanguageService;
import shared.core.infrastructure.Service.ReaderService;
import shared.core.legacy.Connector;

class Moviments {

    //**************************************************************************
    //****ESCOLLIR**************************************************************
    //**************************************************************************
    private static String consultarIDMoviment(String poke, String id) throws SQLException {
        ResultSet result;
        String res = "";

        PreparedStatement select = Connector.connection.prepareStatement("select move_id\n"
                + "from pokemon_moves\n"
                + "where version_group_id = 16\n"
                + "and pokemon_id = " + poke + "\n"
                + "and move_id = " + id + "");
        result = select.executeQuery();
        if (result.next()) {
            res = id;
        } else {
            System.out.println("No existeix cap Moviment amb l'ID seleccionat");
        }
        return res;
    }

    static String consultarNomMoviment(String id) throws SQLException {
        ResultSet result;
        String res = "";

        PreparedStatement select = Connector.connection.prepareStatement("select name\n"
                + "from move_names\n"
                + "where move_id = '" + id + "'\n"
                + "and local_language_id = " + LanguageService.ENGLISH);
        result = select.executeQuery();
        if (result.next()) {
            res = result.getString("name");
        }
        return res;
    }

    private static boolean comprovarMoviment(String[] moves, String id) {
        for (String move : moves) {
            if (id.equals(move)) {
                return false;
            }
        }

        return true;
    }

    private static String escollirMoviments(String[][] poke) {
        String res = "";
        String filter_type = "";
        String filter_name = "";
        String[] s;

        do {
            try {

                String[][] moves = MoveApplicationInjector.injectGetMovesUseCase().execute(
                    new GetMovesRequest(filter_name, filter_type)
                );

                // Mostrar per pantalla els pokèmons
                System.out.printf("%nNom: %s Tipus: %s%n", filter_name, filter_type);
                MatrixPrinter.print(moves);
                System.out.printf("Nom: %s Tipus: %s%n%n", filter_name, filter_type);

                // Opcions del menú
                System.out.println("S. Seleccionar ID");
                System.out.println("N. Filtrar per nom");
                System.out.println("T. Filtrar per tipus");
                System.out.println("E. Eliminar filtre");
                s = ReaderService.read().split(" ");

                // Seleccions del menú
                if ((s[0].equalsIgnoreCase("s")) && (s.length == 2)) {
                    if (comprovarMoviment(poke[2], s[1])) {
                        res = consultarIDMoviment(poke[0][0], s[1]);
                    } else {
                        System.out.println("No pots repetir moviments");
                    }
                } else if ((s[0].equalsIgnoreCase("n")) && (s.length == 2)) {
                    filter_name = s[1];
                } else if ((s[0].equalsIgnoreCase("t")) && (s.length == 2)) {
                    filter_type = s[1];
                } else if ((s[0].equalsIgnoreCase("e")) && (s.length == 2)) {
                    if (s[1].equalsIgnoreCase("n")) {
                        filter_name = "";
                    } else if (s[1].equalsIgnoreCase("t")) {
                        filter_type = "";
                    }
                } else {
                    System.out.println("Selecció incorrecte");
                }
            } catch (Exception ex) {
                System.err.println(ex.getMessage());
            }
        } while (res.equals(""));
        return res;
    }

    //**************************************************************************
    //****EDITAR****************************************************************
    //**************************************************************************
    // Mostar per pantalla els moviments d'un Pokemon
    private static void imprimirMoviments(String[][] poke) {
        int i;
        try {
            System.out.printf("%n%s%n", "Nom: " + poke[0][1]);
            for (i = 0; i <= 28; i++) {
                System.out.print("*");
            }
            System.out.printf("*%n*  %-26s*%n*", "Moviments:");
            for (i = 0; i < 28; i++) {
                System.out.print(" ");
            }
            System.out.printf("*%n");
            for (i = 0; i < poke[2].length; i++) {
                System.out.printf("*  %-26s*%n", consultarNomMoviment(poke[2][i]));
            }
            for (i = 0; i <= 28; i++) {
                System.out.print("*");
            }
            System.out.printf("*%n");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    // Crear un nou equip
    static void editarMoviments(String[][] poke) {
        boolean sortir = false;
        String[] s;

        do {
            try {
                imprimirMoviments(poke);

                // Opcions del menú
                System.out.printf("%nPOKETEXT: EDITOR DE MOVIMENTS%n");
                System.out.println("M. Modificar un moviment (1-4)");
                System.out.println("F. Finalitzar la edició");
                s = ReaderService.read().split(" ");

                // Seleccions del menú principal
                if ((s[0].equalsIgnoreCase("m")) && (s.length == 2)) {
                    poke[2][Integer.parseInt(s[1]) - 1] = "";
                    poke[2][Integer.parseInt(s[1]) - 1] = escollirMoviments(poke);
                } else if ((s[0].equalsIgnoreCase("f")) && (s.length == 1)) {
                    sortir = true;
                } else {
                    System.out.println("Selecció incorrecte");
                }
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException ex) {
                System.out.println("Paràmetres incorrectes");
            }
        } while (!sortir);
    }
}
