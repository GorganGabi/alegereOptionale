package OptDist;

import java.util.*;
import OptDist.Student;
import OptDist.StudentAdministration;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class PackageTest {

    private List<Optional> optionals;

    public void testSetOptionals(){
        List<Optional> optionals1;
        Package packagesest = new Package(optionals,1,2,"pachetul1");
        packagesest.setOptionals(optionals1);

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
    public void GetID(){
        Package packagetest = new Package(optionals,1,2,"pachetul1");
        assertTrue(packagetest.ID == packagetest.getID());
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
        packagetest.setID(123);
        assertTrue(packagetest.getID()==123);
    }


}
