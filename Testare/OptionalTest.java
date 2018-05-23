package OptDist;

/**
 *
 * @author Madalina
 */


public class OptionalTest {
    
    public void testGetCapacity()
    {
        Optional optional = new Optional("Matlab",2,2,100,15);
        assertTrue(optional.GetCapacity()==100);
        
    }
     public void testGetYear()
    {
        Optional optional;
        optional = new Optional("Matlab",2,2,100,15);
        assertTrue(optional.GetYear()==2);
        
    }
     
    public void testGetSemester()
    {
        Optional optional = new Optional("Matlab",2,2,100,15);
        assertTrue(optional.GetSemester()==2);
        
    }
      
    public void testGetName()
    {
        Optional optional = new Optional("Matlab",2,2,100,15);
        assertTrue("Matlab".equals(optional.GetName()));
        
    }
    
       public void testgetID()
    {
        Optional optional = new Optional("Matlab",2,2,100,15);
        assertTrue(optional.getID()== 15);
        
    }

    private void invalidSemesterTest(){ //semestru>2
       System.out.println("getSemester");
       int semester=3;
       Optional instance = new Optional ("IC",2,3,100,15);
       String expResult = null;
       float result = instance.GetSemester();
       assertEquals(expResult,result);
    }
    
      public void invalidSemesterTest2(){ //semestru<-1
       System.out.println("getSemester");
       int semester=-1;
       Optional instance = new Optional ("IC",2,-1,100,15);
       String expResult = null;
       float result = instance.GetSemester();
       assertEquals(expResult,result);
    }
      
      public void invalidYearTest(){ //an>3
       System.out.println("getYear");
       int year=4;
       Optional instance = new Optional ("IC",4,2,100,15);
       String expResult = null;
       float result = instance.GetYear();
       assertEquals(expResult,result);
    }
     
     public void invalidYearTest2(){ //an<2
       System.out.println("getYear");
       int year=1;
       Optional instance = new Optional ("IC",1,2,100,15);
       String expResult = null;
       float result = instance.GetYear();
       assertEquals(expResult,result);
    }
    
     public void invalidCapacityTest2(){ //capacitate <1
       System.out.println("getCapicity");
       int capacity=0;
       Optional instance = new Optional ("IC",1,2,0,15);
       String expResult = null;
       float result = instance.GetCapacity();
       assertEquals(expResult,result);
    }
      public void testInvaliName() { //Numele optionalului este null
        System.out.println("getName");
        Optional instance = new Optional (" ",1,2,0,15);
        String expResult = null;
        String result = instance.GetName();
        assertEquals(expResult, result);
       
       }
      public void testInvaliID() { //ID optionalului este null
        System.out.println("getID");
        Optional instance = new Optional (" ",1,2,0,0);
        int expResult = 0;
        int result = instance.getID();
        assertTrue(expResult == result);
      }

}
