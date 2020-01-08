package indexer.ability.application.Command;

import shared.core.domain.Command.Command;
import indexer.pokedex.infrastructure.controller.PokedexController;

final public class SearchAbilitiesCommand implements Command {

    private final PokedexController receiver;

    private SearchAbilitiesCommand(PokedexController receiver) {
        this.receiver = receiver;
    }

    public static SearchAbilitiesCommand of(PokedexController receiver) {
        return new SearchAbilitiesCommand(receiver);
    }

    public void execute(String... args) {
        receiver.abilities();
    }
}
