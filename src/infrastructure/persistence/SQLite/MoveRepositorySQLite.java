package infrastructure.persistence.SQLite;

import java.util.List;
import java.util.Objects;

import domain.move.Move;
import domain.move.MoveCriteria;
import domain.move.MoveRepository;
import domain.move.MovesCollection;
import infrastructure.poketext.Poketext;

public class MoveRepositorySQLite extends SQLiteRepository implements MoveRepository {

    public MovesCollection findByCriteria(MoveCriteria criteria) {
        List<String[]> rowset = executeQuery("select distinct(m.id), n.name, t.name as type, d.identifier, m.power, m.accuracy, m.pp, f.flavor_text\n"
                + "from pokemon_moves p, move_names n, moves m, type_names t, move_flavor_text f, move_damage_classes d\n"
                + "where m.id = p.move_id\n"
                + "and m.id = n.move_id\n"
                + "and t.type_id = m.type_id\n"
                + "and m.id = f.move_id\n"
                + "and m.damage_class_id = d.id\n"
                + "and p.version_group_id = 16\n"
                + "and f.version_group_id = 16\n"
                + "and n.local_language_id = " + Poketext.env.getProperty("languageId") + "\n"
                + "and t.local_language_id = " + Poketext.env.getProperty("languageId") + "\n"
                + "and f.language_id = " + Poketext.env.getProperty("languageId") + "\n"
                + (criteria.getPokemonId() > 0 ? ("and p.pokemon_id = " + criteria.getPokemonId() + "\n") : "")
                + (!Objects.equals(criteria.getName(), "") ? ("and n.name like '%" + criteria.getName() + "%'\n") : "")
                + (!Objects.equals(criteria.getType(), "") ? ("and t.name like '%" + criteria.getType() + "%'\n") : "")
                + "order by m.id");

        return buildMoves(rowset);
    }

    private MovesCollection buildMoves(List<String[]> rowset) {
        MovesCollection moves = new MovesCollection();
        for (String[] row : rowset) {
            Move move = new Move();
            move.setId(row[0]);
            move.setName(row[1]);
            move.setType(row[2]);
            move.setCategory(row[3]);
            move.setPower(row[4]);
            move.setAccuracy(row[5]);
            move.setPp(row[6]);
            move.setEffect(row[7]);

            moves.add(move);
        }

        return moves;
    }
}