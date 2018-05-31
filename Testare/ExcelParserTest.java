package ro.uaic.info.optdist.internal;

import java.util.*;
import ro.uaic.info.optdist.internal.ExcelParserTest;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import java.lang.String.*;

public class ExcelParserTest {
    
    public ExcelParserTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test(expected = java.io.FileNotFoundException.class)
    void parseInvalidPath(){
    ExcelParser instance = new ExcelParser();
    String filePath = "wrong path";
    instance.parse(filePath);
    }
    
    @org.junit.Test
    public void testParse() {
        System.out.println("parse");
        String Path = "alegereOptionale/Testare/testExcelParser.xlsx";
        
        String[][] testData=new String[3][5];  //fisierul de test are 3 linii si 5 coloane
        testData[0][0]="17395";
        testData[0][1]="Popescu";
        testData[0][2]="Mihai";
        testData[0][3]="A3";
        testData[0][4]="7,40";
        testData[1][0]="28473";
        testData[1][1]="Briella";
        testData[1][2]="Janina";
        testData[1][3]="B4";
        testData[1][4]="6,70";
        testData[2][0]="34626";
        testData[2][1]="Zelma";
        testData[2][2]="Hannah";
        testData[2][3]="A1";
        testData[2][4]="9,50";
        ExcelDump testDump=new ExcelDump(3,5,testData);
        ExcelParser instance = new ExcelParser();
        ExcelDump result = instance.parse(Path);
        assertEquals(testDump, result);
    }
}
