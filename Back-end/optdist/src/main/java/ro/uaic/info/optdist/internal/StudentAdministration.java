package ro.uaic.info.optdist.internal;

import ro.uaic.info.optdist.*;
import org.xwiki.component.annotation.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;

@Component
public class StudentAdministration implements StudentAdministrationInterface {
    ArrayList<Student> studList;
    
    @Override
    public void orderStudents()
    {
        Collections.sort(studList, new Comparator<Student>() {
            @Override
            public int compare(Student s1, Student s2) {
                return s1.getGrade()<s2.getGrade()? -1 : ((s2.getGrade() > s1.getGrade() ? 1 : 0));
            }
        });
    }
    
    @Override
    public void addStudent(Student newStudent)
    {
        studList.add(newStudent);
    }
    
    @Override
    public void addStudent(String nrMatricol, String name, String surname, String group, float grade)
    {
        studList.add(new Student(nrMatricol, name, surname, group, grade));
    }
    
    @Override
    public void importStudents(ExcelDump data)
    {
        
    }
}
