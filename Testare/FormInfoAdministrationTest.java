
package ro.uaic.info.optdist.internal;

import java.util.Calendar;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
/**
 *
 * @author Iulian
 * @update: Teodora
 */
public class FormInfoAdministrationTest {
    
    /* Verificam daca s-a generat form si pt studentii restantieri
      Deoarece nici FormInfo, nici FormInfoAdministration nu stocheaza informatii
      exacte despre care formular ii va fi atribuit unui anumit student 
      vom verifica daca diferenta dimensiunilor listelor(inainte de a adauga studentul, si cea de dupa adaugare)=1
    */
    
    
 @Test
 public void generateFormsTest()
 {  
     Calendar rightnow = Calendar.getInstance();
     rightnow.set(2018,Calendar.JUNE,20);
     StudentAdministration students = new StudentAdministration();
     PackageAdministration packages = new PackageAdministration(); 
     FormInfoAdministration f = new FormInfoAdministration(rightnow);
     List<FormInfo> l1 = f.getForms();
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
     System.out.println(rightnow);
     
 }
    
}
