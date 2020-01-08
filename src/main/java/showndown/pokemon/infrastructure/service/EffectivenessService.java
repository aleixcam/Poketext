package showndown.pokemon.infrastructure.service;

import showndown.move.domain.Move;
import showndown.pokemon.domain.Pokemon;
import showndown.type.domain.Type;
import showndown.type.infrastructure.injector.TypeInfrastructureInjector;
import showndown.type.infrastructure.persistence.SQLite.TypeRepositoryImpl;

public class EffectivenessService {

    public double calculate(Pokemon pokemon, Move move) {
        int typeFactor = damageFactor(pokemon.type(), move.type());
        int subtypeFactor = pokemon.subtype() == null ? 100 : damageFactor(pokemon.subtype(), move.type());

        return typeFactor * subtypeFactor * 0.0001;
    }

    private int damageFactor(Type target, Type damage) {
        return typeRepository().getDamageFactor(target, damage);
    }

    private TypeRepositoryImpl typeRepository() {
        return TypeInfrastructureInjector.injectTypeRepository();
    }
}