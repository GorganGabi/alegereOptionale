package ro.uaic.info.optdist.internal;

import java.io.File;
import java.io.FileInputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import org.xwiki.component.annotation.Component;

@Component
public class ExcelParser {
    
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