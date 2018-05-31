/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


package ro.uaic.info.optdist.internal;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/*

    @Update: Teodora
*/

public class orderTest {
    /*
        Check if a given registration number return the corresponding student
    
    */
    @Test
    public void getStudentByRegTest(){
        
        StudentAdministration instance = new StudentAdministration();
        Student s = new Student("123AAA", "Popescu", "Elena", "A3", 10);
        instance.addStudent(s);  
        assertEquals(s,instance.getStudentByReg(s.getNrMatricol()));
        fail("The registration number doesn't return the right student");
    }
    
    /*
        Check if a given year returns a list of students that are in the corresponding year of study
    */
    
    @Test
    public void getStudentByYearTest(){
        
        StudentAdministration instance = new StudentAdministration();
        Student s  = new Student("123AAA", "Popescu", "Elena", "A3", 10);
        Student s1 = new Student("124AAA", "Dedeaga", "Delia", "A3", 10);
        Student s2 = new Student("123BBB", "Stefanovici", "Andreea","A3", 10);
        Student s3 = new Student("123CCC", "Mare", "Vlad","B5", 9);
        Student s4 = new Student("123DDD", "Ionescu", "Alex","B1", 6);
        
        s.setYear(2);
        s1.setYear(2);
        s2.setYear(2);
        s3.setYear(3);
        s4.setYear(1);
        
        instance.addStudent(s);
        instance.addStudent(s1);
        instance.addStudent(s2);
        instance.addStudent(s3);
        instance.addStudent(s4);
        
        //second year students:
        List<Student> actualResult = new ArrayList<>();
        actualResult = instance.getStudentsByYear(2);
        
        List<Student> expectedResult = new ArrayList<>();
        expectedResult.add(s); expectedResult.add(s1); expectedResult.add(s2);
        
        assertEquals(expectedResult, actualResult);
        fail("the list doesn't contain the students in the given year of study");
    }
    
    
    @Test
    public void importStudentsTest(){
        StudentAdministration instance = new StudentAdministration();
        StudentAdministration expInstance = new StudentAdministration();


        String[][] testData = new String[3][5];
        testData[0][0]="17395";
        testData[0][1]="Popescu";
        testData[0][2]="Mihai";
        testData[0][3]="A3";
        testData[0][4]="7.40";
        testData[1][0]="28473";
        testData[1][1]="Briella";
        testData[1][2]="Janina";
        testData[1][3]="B4";
        testData[1][4]="6.70";
        testData[2][0]="34626";
        testData[2][1]="Zelma";
        testData[2][2]="Hannah";
        testData[2][3]="A1";
        testData[2][4]="9.50";
        ExcelDump testDump = new ExcelDump(3,5,testData);
        
        Student s1 = new Student(testData[0][0], testData[0][1], testData[0][2], testData[0][3], Float.parseFloat(testData[1][4]));
        Student s2 = new Student(testData[1][0], testData[1][1], testData[1][2], testData[1][3], Float.parseFloat(testData[1][4]));
        Student s3 = new Student(testData[2][0], testData[2][1], testData[2][2], testData[2][3], Float.parseFloat(testData[2][4]));
        
        instance.addStudent(s1); instance.addStudent(s2); instance.addStudent(s3);
        expInstance.importStudents(testDump);
        assertTrue(instance.equals(expInstance));
        
        
        
    }
            
    
    
    
    
    /*
    //verifica daca order returneaza tot float
    
    @Test
    public void orderTestCompareReturnFloat() {
        StudentAdministration instance = new StudentAdministration();
        Student s1 = new Student("123AA7", "Ana", "Popescu", "A3", 7);
        Student s2 = new Student("123BB5", "Alex", "Ionescu", "B3", 8);
        //asa trece si prin toate testele de la addStudent
        instance.addStudent(s1);
        instance.addStudent(s2);
        if (!(instance.orderStudents()) instanceof Float)
        
    }
    //verifica daca order returneaza nota mai mare
    @Test
    public void orderTestBiggestGrade() {
        StudentAdministration instance = new StudentAdministration();
        Student s1 = new Student("123AA7", "Ana", "Popescu", "A3", 7);
        Student s2 = new Student("123BB5", "Alex", "Ionescu", "B3", 8);
        instance.addStudent(s1);
        instance.addStudent(s2);
        float expectedResult = 8; 
        float actualResult = instance.orderStudents(); 
        if (expectedResult != actualResult)
}*/
    
}
