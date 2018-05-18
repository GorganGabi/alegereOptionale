package ro.uaic.info.optdist.internal;

import ro.uaic.info.optdist.*;
import org.xwiki.component.annotation.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;
/**
 * Manages a student list.
 *
 * @see Student
 */
@Component
public class StudentAdministration implements StudentAdministrationInterface {
    ArrayList<Student> studList;
    
    /**
     * Orders the internal student list by grade (ro medie).
     */
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
    
    /**
     * Adds a new student to the internal student list.
     * 
     * @param newStudent student to be added
     * @see Student
     */
    @Override
    public void addStudent(Student newStudent)
    {
        studList.add(newStudent);
    }
    
    /**
     * Sets the internal list to the students found in the excel file (passed as an ExcelDump).
     * <p>
     * This method doesn't change the order in the excel in any way.
     * 
     * @param data the excel data from which the students are imported
     * @see ExcelDump
     */
    @Override
    public void importStudents(ExcelDump data)
    {
        
    }
}
