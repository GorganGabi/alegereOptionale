package ro.uaic.info.optdist.internal;

import java.io.File;
import java.io.FileInputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import org.xwiki.component.annotation.Component;
import ro.uaic.info.optdist.ExcelParserInterface;

/**
 * This class has the methods required to extract information from and xls/xlsx file.
 * @author svitel
 */

@Component
public class ExcelParser implements ExcelParserInterface{
    
    /**
     * This method handles the actual parsing of the xls/xls document.
     * It takes a path (a String type value) and returns an object of type ExcelDump (see {@link ExcelDump} section).
     * The cells extracted from the xls/xlsx file are placed into a bidimensional array of Strings (a cell in the xls/xlsx = an entry into the bidimensional array).
     * The bidimensional array is indexed from 0.
     * IMPORTANT. The xls/xlsx document to be parsed needs to have the same number of completed cells on every row and column. 
     * For example, you CAN'T have 3 completed cells on ROW 1 and 2 completed cells on ROW 2.
     * If the xls/xlsx can have such cases please complete the remaining blank cells with a default value (Ex: N/A).  
     * @param Path the path to the xls/xlsx document to be parsed
     * @return an ExcelDump object
     */
    public ExcelDump parse(String Path)
    {   
        File fileHandle;
        FileInputStream inputStream;
        Workbook workbook;
        Sheet sheet;
        Row row = null;
        Cell cell = null;
        int nrOfRows = 0, nrOfCells = 0, i, j;
        String[][] parsedData = null;
        ExcelDump dump;
        
        try
        {
            fileHandle = new File(Path);
            inputStream = new FileInputStream(fileHandle);
            workbook = WorkbookFactory.create(inputStream);
            sheet = workbook.getSheetAt(0);
            
            nrOfRows = sheet.getPhysicalNumberOfRows();
            nrOfCells = sheet.getRow(0).getPhysicalNumberOfCells();
            
            parsedData = new String[nrOfRows][nrOfCells];
            
            for(i = 0; i < nrOfRows; i++)
            {
                row = sheet.getRow(i);
                if(row != null)
                    for(j = 0; j < nrOfCells; j++)
                    {
                        cell = row.getCell(j);
                        if(cell != null)
                            parsedData[i][j] = cell.toString();
                    }
            }
            
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
            
        dump = new ExcelDump(nrOfRows, nrOfCells, parsedData);
        return dump;
    }
}