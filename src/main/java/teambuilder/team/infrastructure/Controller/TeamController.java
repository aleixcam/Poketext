package teambuilder.team.infrastructure.Controller;

import indexer.pokemon.domain.PokemonCollection;
import shared.core.infrastructure.Service.ReaderService;
import teambuilder.team.domain.Team;
import teambuilder.team.infrastructure.Injector.TeamInfrastructureInjector;

public class TeamController {

    public void create(String... args) {
        new Team(new PokemonCollection());
    }

    public void edit(String... args) {
        Team.retrieve();
    }

    public void remove(String... args) {
        TeamInfrastructureInjector.csvTeamRepository().remove(ReaderService.read());
    }
}
