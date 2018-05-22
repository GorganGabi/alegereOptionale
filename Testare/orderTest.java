/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import optdist.Student;
import optdist.StudentAdministration;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class orderTest {
    
    //verifica daca order returneaza tot float
    @Test
    public void orderTestCompareReturnFloat() {
        StudentAdministration instance = new StudentAdministration();
        Student s1 = new Student("123AA7", "Ana", "Popescu", "A3", 7);
        Student s2 = new Student("123BB5", "Alex", "Ionescu", "B3", 8);
        //asa trece si prin toate testele de la addStudent
        instance.addStudent(s1);
        instance.addStudent(s2);
        if (!(compare(s1, s2)) instanceof Float)
            fail("Compare does not return a float value");
        
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
        float actualResult = compare(s1, s2); 
        if (expectedResult != actualResult)
            fail("Compare does not return the biggest value");
    }
    
}