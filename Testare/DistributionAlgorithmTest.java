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
 * @author Teodora
 */
public class DistributionAlgorithmTest {

    StudentAdministration students = new StudentAdministration();
    List<Optional> optionale = new ArrayList<>();
    List<Optional> lista1 = new ArrayList<>();// lista ce contine optionalele in ordinea preferintelor primului student   
    List<Optional> lista2 = new ArrayList<>();
    List<Optional> lista3 = new ArrayList<>();
    List<Optional> lista4 = new ArrayList<>();
    
    
    Student s1 = new Student("123AAA", "Grosu", "Teodora", "A3", 10);
    Student s2 = new Student("123BBB", "Ticu", "Madalina","A3", 9);
    Student s3 = new Student("123CCC", "Popescu", "Marian","A9", 10);
    Student s4 = new Student("123DDD", "Ionescu", "Andra","A10", 6);
        
    Optional o1 = new Optional("1", "ACTN", 3, 2);
    Optional o2 = new Optional("2", "R PETRI", 3, 2);
    Optional o3 = new Optional("3", "MLMOS", 3, 2);
        

    /**
     * Test of match method, of class DistributionAlgorithm.
     */
    @Test
    public void testMatch() {
        System.out.println("match");
        
        o1.setCapacity(2); //la ACTN pot intra maxim 2 studenti
        o2.setCapacity(3);
        o3.setCapacity(2);
        
        o1.setLastGrade(7);  //ultima medie de intrare la ACTN este 7
        o2.setLastGrade(5);
        o3.setLastGrade(5);
        
        lista1.add(o1); lista1.add(o2); lista1.add(o3);  // Adaug in lista fiecarui student, optionalele in ordinea preferintei
        lista2.add(o2); lista2.add(o1); lista2.add(o3);
        lista3.add(o1); lista3.add(o3); lista3.add(o2);
        lista4.add(o3); lista4.add(o2); lista4.add(o1);
        
        optionale.add(o1); optionale.add(o2); optionale.add(o3);   // lista cu optionalele
        Package pachet1 = new Package(optionale, 3, 2, "100");     // pachetul 1 contine ACTN, MCM, MLMOS
        
        Map<Package, List<Optional>> pref1 = new HashMap<Package, List<Optional>>();     
        pref1.put(pachet1, lista1);                                //mapul cu studentul 1 si lista preferintelor
        FormResponse f1 = new FormResponse("123AAA", pref1);       //form response
        Preference p1 = new Preference(f1);                        // preferinta studentului
        s1.setPreference(p1);
        
        Map<Package, List<Optional>> pref2 = new HashMap<Package, List<Optional>>();
        pref2.put(pachet1, lista2);
        FormResponse f2 = new FormResponse("123BBB", pref2);
        Preference p2 = new Preference(f2);
        s2.setPreference(p2);
        
        Map<Package, List<Optional>> pref3 = new HashMap<Package, List<Optional>>();
        pref3.put(pachet1, lista3);
        FormResponse f3 = new FormResponse("123CCC", pref3);
        Preference p3 = new Preference(f3);
        s3.setPreference(p3);
        
        Map<Package, List<Optional>> pref4 = new HashMap<Package, List<Optional>>();
        pref4.put(pachet1, lista4);
        FormResponse f4 = new FormResponse("123DDD", pref4);
        Preference p4 = new Preference(f4);
        s4.setPreference(p4);
        
        students.addStudent(s1);
        students.addStudent(s2);
        students.addStudent(s3);
        students.addStudent(s4);
        
        
        DistributionAlgorithm instance = new DistributionAlgorithm();
        Map<Student, List<Optional>> expResult = new HashMap<Student, List<Optional>>();
        
        List<Optional> expLista1 = new ArrayList<Optional>();  //ce lista de optionale ma astept sa obtin dupa distributie
        List<Optional> expLista2 = new ArrayList<Optional>();
        List<Optional> expLista3 = new ArrayList<Optional>();
        List<Optional> expLista4 = new ArrayList<Optional>();
        
        expLista1.add(o1); //expLista1.add(o2); expLista1.add(o3);
        expLista2.add(o2); //expLista2.add(o2); expLista2.add(o3);
        expLista3.add(o1); //expLista3.add(o2); expLista3.add(o3);
        expLista4.add(o3); //expLista4.add(o2); expLista4.add(o3);

        expResult.put(s1, expLista1);
        expResult.put(s2, expLista2);
        expResult.put(s3, expLista3);
        expResult.put(s4, expLista4);

        Map<Student, List<Optional>> result = instance.match(students);
        
        assertEquals(expResult, result);
        //fail("The test case failed");
    }
    
     /**
     * 
     */
    
    @Test
    public void SameChoiceTest() {
        System.out.println("Test cand toti studentii au aceeasi prima optiune, dar nu toti se incadreaza");
        
        o1.setCapacity(2); //la ACTN pot intra maxim 2 studenti
        o2.setCapacity(3);
        o3.setCapacity(2);
        
        o1.setLastGrade(7);  //ultima medie de intrare la ACTN este 7
        o2.setLastGrade(5);
        o3.setLastGrade(5);
        
        lista1.add(o1); lista1.add(o2); lista1.add(o3);  // Adaug in lista fiecarui student, optionalele in ordinea preferintei
        lista2.add(o1); lista2.add(o3); lista2.add(o2);
        lista3.add(o1); lista3.add(o3); lista3.add(o2);
        lista4.add(o1); lista4.add(o2); lista4.add(o3);
        
        optionale.add(o1); optionale.add(o2); optionale.add(o3);   // lista cu optionalele
        Package pachet1 = new Package(optionale, 3, 2, "100");     // pachetul 1 contine ACTN, MCM, MLMOS
        
        Map<Package, List<Optional>> pref1 = new HashMap<Package, List<Optional>>();     
        pref1.put(pachet1, lista1);                                //mapul cu studentul 1 si lista preferintelor
        FormResponse f1 = new FormResponse("123AAA", pref1);       //form response
        Preference p1 = new Preference(f1);// preferinta studentului
        s1.setPreference(p1);
        
        Map<Package, List<Optional>> pref2 = new HashMap<Package, List<Optional>>();
        pref2.put(pachet1, lista2);
        FormResponse f2 = new FormResponse("123BBB", pref2);
        Preference p2 = new Preference(f2);
        s2.setPreference(p2);
        
        Map<Package, List<Optional>> pref3 = new HashMap<Package, List<Optional>>();
        pref3.put(pachet1, lista3);
        FormResponse f3 = new FormResponse("123CCC", pref3);
        Preference p3 = new Preference(f3);
        s3.setPreference(p3);
        
        Map<Package, List<Optional>> pref4 = new HashMap<Package, List<Optional>>();
        pref4.put(pachet1, lista4);
        FormResponse f4 = new FormResponse("123DDD", pref4);
        Preference p4 = new Preference(f4);
        s4.setPreference(p4);
        
        students.addStudent(s1);
        students.addStudent(s2);
        students.addStudent(s3);
        students.addStudent(s4);
        
        
        DistributionAlgorithm instance = new DistributionAlgorithm();
        Map<Student, List<Optional>> expResult = new HashMap<Student, List<Optional>>();
        
        List<Optional> expLista1 = new ArrayList<Optional>();  //ce lista de optionale ma astept sa obtin dupa distributie
        List<Optional> expLista2 = new ArrayList<Optional>();
        List<Optional> expLista3 = new ArrayList<Optional>();
        List<Optional> expLista4 = new ArrayList<Optional>();
        
        expLista1.add(o1); //expLista1.add(o2); expLista1.add(o3);
        expLista2.add(o3); //expLista2.add(o2); expLista2.add(o3);
        expLista3.add(o1); //expLista3.add(o2); expLista3.add(o3);
        expLista4.add(o2); //expLista4.add(o2); expLista4.add(o3);

        expResult.put(s1, expLista1);
        expResult.put(s2, expLista2);
        expResult.put(s3, expLista3);
        expResult.put(s4, expLista4);

        Map<Student, List<Optional>> result = instance.match(students);
        
        assertEquals(expResult, result);
    }
 
    /*
    *   Un student care nu si-a exprimat optiunile ar trebui distribuit automat 
    *   la optionalul la care mai sunt locuri
    */
    @Test
    public void FormNotSubmittedTest(){
        Student studUituc = new Student("123KKK","uituc","uituc","A1",0);
        List<Optional> listaUituc = null;
        
        o1.setCapacity(2); 
        o2.setCapacity(1);
        
        o1.setLastGrade(7); 
        o2.setLastGrade(5);
        
        lista1.add(o1); lista1.add(o2);   // Adaug in lista fiecarui student, optionalele in ordinea preferintei
        lista2.add(o2); lista2.add(o1); 

        optionale.add(o1); optionale.add(o2);    // lista cu optionalele
        Package pachet1 = new Package(optionale, 3, 2, "100");     // pachetul 1 contine ACTN, MCM, MLMOS
        
        Map<Package, List<Optional>> pref1 = new HashMap<Package, List<Optional>>();     
        pref1.put(pachet1, lista1);                                //mapul cu studentul 1 si lista preferintelor
        FormResponse f1 = new FormResponse("123AAA", pref1);       //form response
        Preference p1 = new Preference(f1);// preferinta studentului
        s1.setPreference(p1);
        
        Map<Package, List<Optional>> pref2 = new HashMap<Package, List<Optional>>();
        pref2.put(pachet1, lista2);
        FormResponse f2 = new FormResponse("123BBB", pref2);
        Preference p2 = new Preference(f2);
        s2.setPreference(p2);

        Map<Package, List<Optional>> prefk = new HashMap<Package, List<Optional>>();
        prefk.put(pachet1, listaUituc);
        FormResponse fk = new FormResponse("123KKK", prefk);
        Preference pk = new Preference(fk);
        s2.setPreference(pk);
        
        students.addStudent(s1);
        students.addStudent(s2);
        students.addStudent(studUituc);
        
        DistributionAlgorithm instance = new DistributionAlgorithm();
        Map<Student, List<Optional>> expResult = new HashMap<Student, List<Optional>>();
        
        List<Optional> expLista1 = new ArrayList<Optional>();  
        List<Optional> expLista2 = new ArrayList<Optional>();
        List<Optional> expListaUituc = new ArrayList<Optional>();

        
        expLista1.add(o1); 
        expLista2.add(o2); 
        expListaUituc.add(o1); 
        /*numai la primul optional mai sunt locuri disponibile,
        deci acolo va fi studentul uituc distribuit
        */

        expResult.put(s1, expLista1);
        expResult.put(s2, expLista2);
        expResult.put(s3, expListaUituc);

        Map<Student, List<Optional>> result = instance.match(students);
        
        assertEquals(expResult, result);
        
    }
    
    /*
        Cand doi studenti cu aceeasi medie au ales acelasi optional, dar nu mai este decat un loc disponibil,
        se suplimenteaza numarul de locuri cu 1
    */
    @Test
    public void OneMoreTest(){
        o1.setCapacity(2); //la ACTN pot intra maxim 2 studenti
        o2.setCapacity(3);
        o3.setCapacity(2);
        
        o1.setLastGrade(7);  //ultima medie de intrare la ACTN este 7
        o2.setLastGrade(5);
        o3.setLastGrade(5);
        
        lista1.add(o1); lista1.add(o2); lista1.add(o3);  // Adaug in lista fiecarui student, optionalele in ordinea preferintei
        lista2.add(o1); lista2.add(o1); lista2.add(o3);
        lista3.add(o1); lista3.add(o3); lista3.add(o2);
        lista4.add(o3); lista4.add(o2); lista4.add(o1);
        
        s2.setGrade(10);
        
        optionale.add(o1); optionale.add(o2); optionale.add(o3);   // lista cu optionalele
        Package pachet1 = new Package(optionale, 3, 2, "100");     // pachetul 1 contine ACTN, MCM, MLMOS
        
        Map<Package, List<Optional>> pref1 = new HashMap<Package, List<Optional>>();     
        pref1.put(pachet1, lista1);                                //mapul cu studentul 1 si lista preferintelor
        FormResponse f1 = new FormResponse("123AAA", pref1);       //form response
        Preference p1 = new Preference(f1);                        // preferinta studentului
        s1.setPreference(p1);
        
        Map<Package, List<Optional>> pref2 = new HashMap<Package, List<Optional>>();
        pref2.put(pachet1, lista2);
        FormResponse f2 = new FormResponse("123BBB", pref2);
        Preference p2 = new Preference(f2);
        s2.setPreference(p2);
        
        Map<Package, List<Optional>> pref3 = new HashMap<Package, List<Optional>>();
        pref3.put(pachet1, lista3);
        FormResponse f3 = new FormResponse("123CCC", pref3);
        Preference p3 = new Preference(f3);
        s3.setPreference(p3);
        
        Map<Package, List<Optional>> pref4 = new HashMap<Package, List<Optional>>();
        pref4.put(pachet1, lista4);
        FormResponse f4 = new FormResponse("123DDD", pref4);
        Preference p4 = new Preference(f4);
        s4.setPreference(p4);
        
        students.addStudent(s1);
        students.addStudent(s2);
        students.addStudent(s3);
        students.addStudent(s4);
        
        
        DistributionAlgorithm instance = new DistributionAlgorithm();
        Map<Student, List<Optional>> expResult = new HashMap<Student, List<Optional>>();
        
        List<Optional> expLista1 = new ArrayList<Optional>();  //ce lista de optionale ma astept sa obtin dupa distributie
        List<Optional> expLista2 = new ArrayList<Optional>();
        List<Optional> expLista3 = new ArrayList<Optional>();
        List<Optional> expLista4 = new ArrayList<Optional>();
        
        expLista1.add(o1); 
        expLista2.add(o1); 
        expLista3.add(o1); 
        expLista4.add(o3); 

        expResult.put(s1, expLista1);
        expResult.put(s2, expLista2);
        expResult.put(s3, expLista3);
        expResult.put(s4, expLista4);

        Map<Student, List<Optional>> result = instance.match(students);
        
        assertEquals(expResult, result);
        //fail("The test case failed");
    }
    
        
    }
  
