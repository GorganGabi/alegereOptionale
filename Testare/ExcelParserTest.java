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
    void parse_InvalidPath(){
    ExcelParser instance = new ExcelParser();
    String filePath = "wrong path";
    instance.parse(filePath);
    }
    
    @org.junit.Test
    public void testParse() {
        System.out.println("parse");
        String Path = "alegereOptionale/Testare/testExcelParser.xlsx";
        
        String[][] testData=new String[3][5];  //fisierul de test are 3 linii si 5 coloane
        testData[1][1]="17395";
        testData[1][2]="Popescu";
        testData[1][3]="Mihai";
        testData[1][4]="A3";
        testData[1][5]="7,40";
        testData[2][1]="28473";
        testData[2][2]="Briella";
        testData[2][3]="Janina";
        testData[2][4]="B4";
        testData[2][5]="6,70";
        testData[3][1]="34626";
        testData[3][2]="Zelma";
        testData[3][3]="Hannah";
        testData[3][4]="A1";
        testData[3][5]="9,50";
        ExcelDump testDump=new ExcelDump(3,5,testData);
        ExcelParser instance = new ExcelParser();
        ExcelDump result = instance.parse(Path);
        assertEquals(testDump, result);
    }
}
