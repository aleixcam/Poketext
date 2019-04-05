package infrastructure.presentation.transformer.matrix;

import domain.move.Move;
import application.move.MoveAssembler;
import domain.move.MovesCollection;

import java.util.ArrayList;

public class MoveAssemblerMatrix extends MatrixAssembler implements MoveAssembler {

    public String[][] assemble(MovesCollection collection) {
        ArrayList<Move> moves = collection.getMoves();
        String[] columns = {"ID", "Name", "Type", "Class", "Pow", "Acc", "PP", "Effect"};
        String[][] matrix = new String[moves.size() + 1][columns.length];

        matrix[0] = columns;
        for (int i = 0; i < moves.size(); i++) {
            matrix[i+1][0] = String.valueOf(moves.get(i).getId());
            matrix[i+1][1] = moves.get(i).getName();
            matrix[i+1][2] = moves.get(i).getType();
            matrix[i+1][3] = moves.get(i).getCategory();
            matrix[i+1][4] = String.valueOf(moves.get(i).getPower());
            matrix[i+1][5] = String.valueOf(moves.get(i).getAccuracy());
            matrix[i+1][6] = String.valueOf(moves.get(i).getPp());
            matrix[i+1][7] = moves.get(i).getEffect();
        }

        return generate(matrix);
    }
}