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

    @Test(expected = java.io.FileNotFoundException.class)
    void parse_InvalidPath(){
    ExcelParser instance = new ExcelParser();
    String filePath = "wrong path";
    instance.parse(filePath);
    }
}
