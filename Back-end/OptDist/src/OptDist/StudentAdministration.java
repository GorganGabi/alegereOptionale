
package OptDist;

import java.util.Arrays;
import java.util.List;
import java.util.Comparator;
import java.util.Collections;

public class StudentAdministration  {
    List<Student> studList;
    void orderStudents()
    {
        Collections.sort(studList, new Comparator<Student>() {
            @Override
            public int compare(Student s1, Student s2) {
                return s1.getGrade()<s2.getGrade()? -1 : ((s2.getGrade() > s1.getGrade() ? 1 : 0));
            }
        });
    }
    void addStudent(Student newStudent)
    {
        studList.add(newStudent);
    }
    
    void addStudent(String nrMatricol, String name, String surname, String group, float grade)
    {
        Student newStudent = new Student(nrMatricol, name, surname, group, grade);
        studList.add(newStudent);
    }
    
    void importStudents(ExcelDump data)
    {
        
    }
}
