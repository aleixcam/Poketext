package shared.core.infrastructure.Persistence.FileSystem;

import shared.core.infrastructure.Service.FinalCSVService;

import java.util.List;

public class CSV3FileSystemManager extends FileSystemManager<String[][][]> {

    public final FinalCSVService csvService;

    public CSV3FileSystemManager(FinalCSVService csvService) {
        this.csvService = csvService;
    }

    protected String[][][] importData(List<String> data) {
        return this.csvService.toParallelepiped(data.toArray(new String[0]));
    }

    protected String[] exportData(String[][][] csv) {
        return this.csvService.toCSV(csv);
    }
}
