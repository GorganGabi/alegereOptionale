/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


package ro.uaic.info.optdist.internal;

import org.junit.Test;
import static org.junit.Assert.*;

public class addStudentTest {

    
    //daca se introduce in lista de studenti un student fara nume, prenume etc
    @Test
    public void addStudentTestStudentNull() {
        StudentAdministration instance = new StudentAdministration();
        Student s = new Student(null, null, null, null, 0); 
        if (s == null)
        {
            fail("The Student should not be null");
        }
    }
    
    //testul reuseste daca studentul null nu a fost adaugat 
    @Test
    public void addStudentTestStudentNullAdaugat() {
        StudentAdministration instance = new StudentAdministration();
        StudentAdministration instance2 = new StudentAdministration();
        Student s = new Student(null, null, null, null, 0);
        instance.addStudent(s);  
        assertEquals(instance, instance2); 
    }
    
     //daca se introduce in lista de studenti un student cu nrmatricol care nu este string
    @Test
    public void addStudentTestNrMatricolNotString() {
        StudentAdministration instance = new StudentAdministration();
        Student s = new Student("2", "Radu", "Andrei", "A3", 10);
        if (!(s.getNrMatricol() instanceof String))
        {
            fail("NrMatricol should be a string");
        }
    }
    
    //testul reuseste daca studentul cu nrmatricol!=string nu a fost adaugat 
    @Test
    public void addStudentTestNrMatricolNotStringAdaugat() {
        StudentAdministration instance = new StudentAdministration();
        StudentAdministration instance2 = new StudentAdministration();
        Student s = new Student("2", "Radu", "Andrei", "A3", 10);
        instance.addStudent(s);  
        assertEquals(instance, instance2); 
    }
    
     //daca se introduce in lista de studenti un student cu prenume care nu este cuvant
    @Test
    public void addStudentTestNumeNotString() {
        StudentAdministration instance = new StudentAdministration();
        Student s = new Student("123AA5", "12", "Andrei", "A3", 10);
        if (!(s.getName() instanceof String))
        {
            fail("Name should be a string");
        }
    }
    
    //testul reuseste daca studentul cu prenume!=string nu a fost adaugat 
    @Test
    public void addStudentTestNumeNotStringAdaugat() {
        StudentAdministration instance = new StudentAdministration();
        StudentAdministration instance2 = new StudentAdministration();
        Student s = new Student("123AA5", "12", "Andrei", "A3", 10);
        instance.addStudent(s);  
        assertEquals(instance, instance2); 
    }
    
      //daca se introduce in lista de studenti un student cu nume care nu este string
    @Test
    public void addStudentTestPrenumeNotString() {
        StudentAdministration instance = new StudentAdministration();
        Student s = new Student("123AA5", "Radu", "123", "A3", 10); 
        if (!(s.getSurname() instanceof String))
        {
            fail("Surname should be a string");
        }
    }
    
    //testul reuseste daca studentul cu nume!=string nu a fost adaugat 
    @Test
    public void addStudentTestPrenumeNotStringAdaugat() {
        StudentAdministration instance = new StudentAdministration();
        StudentAdministration instance2 = new StudentAdministration();
        Student s = new Student("123AA5", "Radu", "123", "A3", 10);
        instance.addStudent(s);  
        assertEquals(instance, instance2); 
    }
    
      //daca se introduce in lista de studenti un student cu grupa care nu este string
    @Test
    public void addStudentTestGroupNotString() {
        StudentAdministration instance = new StudentAdministration();
        Student s = new Student("123AA5", "Radu", "Andrei", "3", 10);  
        if (!(s.getGroup() instanceof String))
        {
            fail("Group should be a string");
        }
    }
    
    //testul reuseste daca studentul cu grupa!=string nu a fost adaugat 
    @Test
    public void addStudentTestGroupNotStringAdaugat() {
        StudentAdministration instance = new StudentAdministration();
        StudentAdministration instance2 = new StudentAdministration();
        Student s = new Student("123AA5", "Radu", "Andrei", "3", 10);
        instance.addStudent(s);  
        assertEquals(instance, instance2); 
    }
    
      //daca se introduce in lista de studenti un student cu grade care nu este float
    /*
    @Test
    public void addStudentTestGradeNotFloat() {
        StudentAdministration instance = new StudentAdministration();
        Student s = new Student("123BB6", "Radu", "Andrei", "A3", 8);  
        if (!(s.getGrade() instanceof Float))
        {
            fail("Grade should be a string");
        }
    }*/
    
    //testul reuseste daca studentul cu grade!=float nu a fost adaugat 
    @Test
    public void addStudentTestGradeNotFloatAdaugat() {
        StudentAdministration instance = new StudentAdministration();
        StudentAdministration instance2 = new StudentAdministration();
        Student s = new Student("123BB6", "Radu", "Andrei", "A3", 8);
        instance.addStudent(s);  
        assertEquals(instance, instance2); 
    }
    
      //daca se introduce in lista de studenti un student cu grade <1
    @Test
    public void addStudentTestGradeTooSmall() {
        StudentAdministration instance = new StudentAdministration();
        Student s = new Student("123BB6", "Radu", "Andrei", "A3", -1);  
        if ((s.getGrade() < 1))
        {
            fail("Grade too small.");
        }
    }
    
    //testul reuseste daca studentul cu grade <1 nu a fost adaugat 
    @Test
    public void addStudentTestGradeTooSmallAdaugat() {
        StudentAdministration instance = new StudentAdministration();
        StudentAdministration instance2 = new StudentAdministration();
        Student s = new Student("123BB6", "Radu", "Andrei", "A3", -1);
        instance.addStudent(s);  
        assertEquals(instance, instance2); 
    }
    
      //daca se introduce in lista de studenti un student cu grade >10
    @Test
    public void addStudentTestGradeTooBig() {
        StudentAdministration instance = new StudentAdministration();
        Student s = new Student("123BB6", "Radu", "Andrei", "A3", 11);  
        if ((s.getGrade() > 1))
        {
            fail("Grade too big.");
        }
    }
    
    //testul reuseste daca studentul cu grade >10 nu a fost adaugat 
    @Test
    public void addStudentTestGradeTooBigAdaugat() {
        StudentAdministration instance = new StudentAdministration();
        StudentAdministration instance2 = new StudentAdministration();
        Student s = new Student("123BB6", "Radu", "Andrei", "A3", 11);
        instance.addStudent(s);  
        assertEquals(instance, instance2); 
    }
    
    //grupa nu a fost introdusa in formatul dat
    @Test
    public void addStudentTestGroupNotInTheRequiredForm() {
        StudentAdministration instance = new StudentAdministration();
        Student s = new Student("123AA4", "Radu", "Andrei", "A*3", 8); 
        String group = s.getGroup();
        if (group.length() != 2)
        {
            fail("Group does not have the required number of characters");
        }
        else
            if ((group.charAt(0) != 'A') && (group.charAt(0) != 'B'))
            {
                fail("Group is not in the required format");
            }
            else
                if (group.charAt(1) < 48 || group.charAt(1) > 57)
                    {
                        fail("Group is not in the required format");
                    }
    }
    
    //testul reuseste daca studentul cu grupa introdusa in format gresit  nu a fost adaugat 
    @Test
    public void addStudentTestGroupNotInTheRequiredFormAdaugat() {
        StudentAdministration instance = new StudentAdministration();
        StudentAdministration instance2 = new StudentAdministration();
        Student s = new Student("123AA4", "Radu", "Andrei", "A*3", 8);
        instance.addStudent(s);  
        assertEquals(instance, instance2); 
    }
    
    //numele nu a fost introdus in formatul dat
    @Test
    public void addStudentTestSurnameNotInTheRequiredForm() {
        StudentAdministration instance = new StudentAdministration();
        Student s = new Student("123AA4", "Radu", "andr*ei", "A3", 8); 
        String surname = s.getSurname();
        if (surname.charAt(0) < 65 || surname.charAt(0) > 90)
        {
            fail("First letter is not big");
        }
        for (int index = 1; index < surname.length(); index++)
        {
            if (surname.charAt(index) < 97 || surname.charAt(index) > 122)
            {
                fail("Surname is not in the required format");
            }
        }
    }
    
    //testul reuseste daca studentul cu numele introdus in format gresit  nu a fost adaugat 
    @Test
    public void addStudentTestSurnameNotInTheRequiredFormAdaugat() {
        StudentAdministration instance = new StudentAdministration();
        StudentAdministration instance2 = new StudentAdministration();
        Student s = new Student("123AA4", "Radu", "andr*ei", "A3", 8);
        instance.addStudent(s);  
        assertEquals(instance, instance2); 
    }
    
    //prenumele nu a fost introdus in formatul dat
    @Test
    public void addStudentTestNameNotInTheRequiredForm() {
        StudentAdministration instance = new StudentAdministration();
        Student s = new Student("123AA4", "r_adu", "Andrei", "A3", 8); 
        String name = s.getName();
        if (name.charAt(0) < 65 || name.charAt(0) > 90)
        {
            fail("First letter is not big");
        }
        for (int index = 1; index < name.length(); index++)
        {
            if (name.charAt(index) < 97 || name.charAt(index) > 122)
            {
                fail("Name is not in the required format");
            }
        }
    }
    
    //testul reuseste daca studentul cu prenumele introdus in format gresit  nu a fost adaugat 
    @Test
    public void addStudentTestNameNotInTheRequiredFormAdaugat() {
        StudentAdministration instance = new StudentAdministration();
        StudentAdministration instance2 = new StudentAdministration();
        Student s = new Student("123AA4", "r_adu", "Andrei", "A3", 8);
        instance.addStudent(s);  
        assertEquals(instance, instance2); 
    }
    
      //nrmatricol nu a fost introdus in formatul dat
    @Test
    public void addStudentTestNrMatricolNotInTheRequiredForm() {
        StudentAdministration instance = new StudentAdministration();
        Student s = new Student("123aA*", "Radu", "Andrei", "A3", 9); 
        String nrMatricol = s.getNrMatricol();
        if (nrMatricol.length() != 6)
        {
            fail("NrMatriocol does not have the required number of characters");
        }
        if (nrMatricol.charAt(0) < 48 || nrMatricol.charAt(0) > 57)
        {
            fail("First character is not a number");
        }
        if (nrMatricol.charAt(1) < 48 || nrMatricol.charAt(1) > 57)
        {
            fail("Second character is not a number");
        }
        if (nrMatricol.charAt(2) < 48 || nrMatricol.charAt(2) > 57)
        {
            fail("Third character is not a number");
        }
        if (nrMatricol.charAt(6) < 48 || nrMatricol.charAt(6) > 57)
        {
            fail("Sixth character is not a number");
        }
        if (nrMatricol.charAt(4) < 65 || nrMatricol.charAt(4) > 90)
        {
            fail("Fourth character is not a number");
        }
        if (nrMatricol.charAt(5) < 65 || nrMatricol.charAt(5) > 90)
        {
            fail("Fifth character is not a number");
        }
    }
    
    //testul reuseste daca studentul cu nrmatricol introdus in format gresit  nu a fost adaugat 
    @Test
    public void addStudentTestNrMatricolNotInTheRequiredFormAdaugat() {
        StudentAdministration instance = new StudentAdministration();
        StudentAdministration instance2 = new StudentAdministration();
        Student s = new Student("123aA*", "Radu", "Andrei", "A3", 9);
        instance.addStudent(s);  
        assertEquals(instance, instance2); 
    }
}