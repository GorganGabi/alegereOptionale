package ro.uaic.info.optdist.internal;

import org.xwiki.component.annotation.Component;
import ro.uaic.info.optdist.ExcelDumpInterface;
/**
 * This class holds and provides access to the data extracted from an xls/xlsx file.
 * @author svitel
 */
@Component
public class ExcelDump implements ExcelDumpInterface{
    
    /**
     * The data member is a bidimensional array of strings which will hold the information from the xls/xlsx's cells.
     */
    private String[][] data;
    /**
     * nrOfRows and nrOfColumns represent the dimensions of the bidimensional array
     */
    private int nrOfRows, nrOfColumns;
    /**
     * This method gives acces to a cell of the bidimensional array
     * @param x the line of the String to be retrieved
     * @param y the column of the String to be retrieved
     * @return the desired String
     */
    public String get(int x, int y)
    {
        return data[x][y];
    }
    /**
     * A simple contructor for the class. It initializes the dimensions of the bidimensional array and the array itself.
     * @param nrOfRows
     * @param nrOfColumns
     * @param data 
     */
    public ExcelDump(int nrOfRows, int nrOfColumns, String[][] data)
    {
        this.nrOfRows = nrOfRows;
        this.nrOfColumns = nrOfColumns;
        this.data = data;
    }
}