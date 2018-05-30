package ro.uaic.info.optdist.internal;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/*
*  @update: Teodora
*
*/

public class PackageTest {

    List<Optional> optionals = new ArrayList<>();
    List<Optional> replaceOptionals = new ArrayList<>();
    Optional o1 = new Optional("1", "ACTN", 3, 2);
    Optional o2 = new Optional("2", "R PETRI", 3, 2);
    Optional o3 = new Optional("3", "MLMOS", 3, 2);
    
    /*
        Test to see if the current package is replaced with
        the package given as parameter
    */
    @Test
    public void testReplacePackage(){
        optionals.add(o1); optionals.add(o2); 
        replaceOptionals.add(o3);
        Package package1 = new Package(optionals, 1, 2, "pachet1");
        Package replacePackage = new Package(replaceOptionals, 1, 2, "pachet2");
        Package expectedPackage = new Package(null,0,0,null);
        expectedPackage.setYear(replacePackage.getYear());
        expectedPackage.setSemester(replacePackage.getSemester());
        expectedPackage.setID(replacePackage.getID());
        expectedPackage.setOptionals(replacePackage.getOptionals());
        package1.replace(replacePackage);
        
        /*assertEquals(expectedPackage.getYear(), package1.getYear());
        assertEquals(expectedPackage.getSemester(), package1.getSemester());
        assertEquals(expectedPackage.getID(), package1.getID());*/
        assertEquals(expectedPackage.getOptionals(), replacePackage.getOptionals());
        
        fail("The test case failed, the old package is not replaced with the new one");
    }


    @Test
    public void GetName(){
        Package packagetest = new Package(optionals,1,2,"pachetul1");
        assertTrue(packagetest.getName() == "pachetul1");
    }

    @Test
    public void GetSemester(){
        Package packagetest = new Package(optionals,1,2,"pachetul1");
        assertTrue(packagetest.getSemester() == 1);
    }

    @Test
    public void GetYear(){
        Package packagetest = new Package(optionals,1,2,"pachetul1");
        assertTrue(packagetest.getYear() == 1);
    }

    @Test
    public void SetYear(){
        Package packagetest = new Package(optionals,1,2,"pachetul1");
        packagetest.setYear(2);
        assertTrue(packagetest.getYear() == 2);
    }

    @Test
    public void SetSemester(){
        Package packagetest = new Package(optionals,1,2,"pachetul1");
        packagetest.setYear(3);
        assertTrue(packagetest.getYear() == 3);
    }

    @Test
    public void SetName(){
        Package packagetest = new Package(optionals,1,2,"pachetul1");
        packagetest.setName("paachetul2");
        assertTrue(packagetest.getName() == "pachetul2");
    }

    @Test
    public void SetID(){
        Package packagetest = new Package(optionals,1,2,"pachetul1");
        packagetest.setID("123");
        assertTrue(packagetest.getID() == "123");
    }


}
