
package OptDist;

import java.util.Map;

public class ExcelDump {
    
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