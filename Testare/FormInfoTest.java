/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OptDist;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Iulian
 */
public class FormInfoTest {
    
    public FormInfoTest() {
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

    /**
     * Test of getPackages method, of class FormInfo.
     */
    @Test
    public void testGetPackages() {
        System.out.println("getPackages");
        FormInfo instance = new FormInfo();
        List<Package> expResult = null;
        List<Package> result = instance.getPackages();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setPackages method, of class FormInfo.
     */
    @Test
    public void testSetPackages() {
        System.out.println("setPackages");
        List<Package> packages = null;
        FormInfo instance = new FormInfo();
        instance.setPackages(packages);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    public void testTTL()
    {
        System.out.println("setTTL"); // vom verifica daca TTL > data_curenta
        Calendar newTTL = GregorianCalendar.getInstance();
        newTTL.set(1997,6,20); // 20-mai-1997 (numerotarea lunilor se face de la 0
        FormInfo instance = new FormInfo(newTTL,null);
        fail("Invalid TTL");
    }
    
}
