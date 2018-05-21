
package ro.uaic.info.optdist.internal;

import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import ro.uaic.info.optdist.*;
/**
 *
 * @author Iulian
 */
public class FormInfoAdministrationTest {
    
 @Test
 public void generateFormsTest(StudentAdministration students, PackageAdministration packages)
 {  // Verificam daca s-a generat form si pt studentii restantieri
    //  Deoarece nici FormInfo, nici FormInfoAdministration nu stocheaza informatii
    //    exacte despre care formular ii va fi atribuit unui anumit student 
    //    vom verifica daca diferenta dimensiunilor listelor(inainte de a adauga studentul, si cea de dupa adaugare)=1
     FormInfoAdministration f = new FormInfoAdministration();
     List<FormInfo> l1=f.getForms();
     int n1 = l1.size(); // n1 salveaza nr de forms generate inainte de adaugarea studentului din grupa X     
     Student stud_t;
     stud_t=new Student("123abc","Ababei","Bogdan","X",5); // Un student din grupa X
     students.addStudent(stud_t); // Il adaugam in lista
          f.generateForms(students,packages); // Generam forms pentru toti studentii
     List<FormInfo> l2=f.getForms();
     int n2 = l2.size(); // n1 salveaza nr de forms generate dupa adaugarea studentului din grupa X 
     int result=n2-n1;
     int expectedResult=1;
     assertEquals(expectedResult,result);
 }
    
}
