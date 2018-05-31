/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.uaic.info.optdist.internal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Delia
 */
public class GetStudentByTest {

     @Test
    public void getStudentByRegTest(){
        
        StudentAdministration instance = new StudentAdministration();
        Student s = new Student("123AAA", "Popescu", "Elena", "A3", 10);
        Student s2 = new Student();
        instance.addStudent(s);  
        s2 = getStudentByReg("123AA5");
        assertEquals(s,s2);
    }
    



     @Test
    public void getStudentByYearTest(){
        
        StudentAdministration instance = new StudentAdministration();
        Student s = new Student("123AAA", "Popescu", "Elena", "A3", 10);
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
        
        List<Student> actualResult = new ArrayList<>();
        actualResult = getStudentByYear(2);
        
        List<Student> expectedResult = new ArrayList<>();
        expectedResult.addStudent(s);
        expectedResult.addStudent(s1);
        expectedResult.addStudent(s2);
        
        assertEquals(expectedResult, actualResult);

    }
}

