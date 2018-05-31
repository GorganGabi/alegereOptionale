package ro.uaic.info.optdist.internal;

import java.util.*;
import ro.uaic.info.optdist.internal.ExcelDump;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import java.lang.String.*;

public class ExcelDumpTest {

    @Test
    void getTest(){
        String[][] data = {{"Imporatant data"},{"very important data"}};
        ExcelDump instance = new ExcelDump(2,2,data);
        assertEquals(instance.get(0,0),"Important data");
    }
}
