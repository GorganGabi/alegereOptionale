package ro.uaic.info.optdist.internal;

import org.xwiki.component.annotation.Component;
import ro.uaic.info.optdist.ExcelDumpInterface;

@Component
public class ExcelDump implements ExcelDumpInterface{
    
    private String[][] data;
    private int nrOfRows, nrOfColumns;
    
    public String get(int x, int y)
    {
        return data[x][y];
    }
    
    public ExcelDump(int nrOfRows, int nrOfColumns, String[][] data)
    {
        this.nrOfRows = nrOfRows;
        this.nrOfColumns = nrOfColumns;
        this.data = data;
    }
}