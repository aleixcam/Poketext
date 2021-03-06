package indexer.item.infrastructure.transformer.Matrix;

import shared.core.infrastructure.Service.MatrixService;
import shared.item.domain.Item;
import indexer.item.application.ItemTransformer;
import indexer.item.domain.ItemCollection;

import java.util.ArrayList;

final public class MatrixItemTransformer implements ItemTransformer<String[][]> {

    private final String[] COLUMNS = {"ID", "Name", "Description"};

    private final MatrixService matrixService;

    public MatrixItemTransformer(MatrixService matrixService) {
        this.matrixService = matrixService;
    }

    public String[][] transform(ItemCollection collection) {
        ArrayList<Item> items = collection.getItems();
        String[][] matrix = matrixService.generate(COLUMNS, items.size());

        for (int i = 0; i < items.size(); i++) {
            matrix[i+1][0] = String.valueOf(items.get(i).id());
            matrix[i+1][1] = items.get(i).name();
            matrix[i+1][2] = items.get(i).description();
        }

        return matrixService.beautify(matrix);
    }
}
