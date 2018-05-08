
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
                float x1 = ((Student) s1).getGrade();
                float x2 = ((Student) s2).getGrade();
                if(x1<x2) return -1;
                if(x1>x2) return 1;
                if(x1==x2) return 0;
                //return s1.getGrade()<s2.getGrade()?-1:1;
                return 0;
            }
        });
    }
    void addStudent(Student newStudent)
    {
        studList.add(newStudent);
    }
    void importStudents(ExcelDump data)
    {
        
    }
}
