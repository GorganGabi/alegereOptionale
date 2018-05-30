package ro.uaic.info.optdist.internal;

import java.util.Calendar;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
/**
 *
 * @author Madalina
 * @update: Teodora
 * 
 */


public class OptionalTest {
    @Test
    public void testGetCapacity()
    {
        Optional optional = new Optional("1","Matlab",2,2);
        optional.setCapacity(100);
        assertTrue(optional.getCapacity()==100);
        
    }
    @Test
     public void testGetYear()
    {
        Optional optional;
        optional = new Optional("1", "Matlab",2,2);
        assertTrue(optional.getYear()==2);
        
    }
    @Test
    public void testGetSemester()
    {
        Optional optional = new Optional("1","Matlab",2,2);
        assertTrue(optional.getSemester()==2);
        
    }
    @Test
    public void testGetName()
    {
        Optional optional = new Optional("1","Matlab",2,2);
        assertTrue("Matlab".equals(optional.getName()));
        
    }
    @Test
       public void testgetID()
    {
        Optional optional = new Optional("1","Matlab",2,2);
        assertTrue(optional.getID()== "1");
        
    }
    @Test
    public void invalidSemesterTest(){ //semestru>2
       System.out.println("getSemester");
       Optional instance = new Optional ("2", "IC",2,3);
       String expResult = null;
       float result = instance.getSemester();
       assertEquals(expResult,result);
    }
    @Test
      public void invalidSemesterTest2(){ //semestru<-1
       System.out.println("getSemester");
       Optional instance = new Optional ("2", "IC",2,-1);
       String expResult = null;
       float result = instance.getSemester();
       assertEquals(expResult,result);
    }
     @Test 
      public void invalidYearTest(){ //an>3
       System.out.println("getYear");
       Optional instance = new Optional ("2","IC",4,2);
       String expResult = null;
       float result = instance.getYear();
       assertEquals(expResult,result);
    }
    @Test 
     public void invalidYearTest2(){ //an<2
       System.out.println("getYear");
       Optional instance = new Optional ("2", "IC",1,2);
       String expResult = null;
       float result = instance.getYear();
       assertEquals(expResult,result);
    }
    @Test
     public void invalidCapacityTest2(){ //capacitate <1
       System.out.println("getCapicity");
       Optional instance = new Optional ("2","IC",1,2);
       instance.setCapacity(0);
       String expResult = null;
       float result = instance.getCapacity();
       assertEquals(expResult,result);
    }
     @Test
      public void testInvaliName() { //Numele optionalului este null
        System.out.println("getName");
        Optional instance = new Optional ("3"," ",1,2);
        String expResult = null;
        String result = instance.getName();
        assertEquals(expResult, result);
       
       }
      @Test
      public void testInvaliID() { //ID optionalului este null
        System.out.println("getID");
        Optional instance = new Optional (null,"MCM",1,2);
        String expResult = null;
        String result = instance.getID();
        assertTrue(expResult == result);
      }

}
