package ro.uaic.info.optdist.internal;

import ro.uaic.info.optdist.*;
import org.xwiki.component.annotation.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;
import java.util.List;
/**
 * Manages a student list.
 *
 * @see Student
 */
@Component
public class StudentAdministration implements StudentAdministrationInterface {
    ArrayList<Student> studList;
    
    /**
     * Initializes the internal student list with an empty list.
     */
    public StudentAdministration () {
        this.studList = new ArrayList<>();
    }
    
    /**
     * Orders the internal student list by grade (<i>ro medie</i>).
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
     * Sets the internal list to the students found in the excel
     * file (passed as an ExcelDump).
     * <p>
     * This method doesn't change the order in the excel in any way.
     * 
     * @param data the excel data from which the students are imported
     * @see ExcelDump
     */
    @Override
    public void importStudents(ExcelDump data)
    {
        int i, j = 1;
    	String nrMatricol;
        String name;
        String surname;
        String group;
        float grade;
        int year;
        Student student;
        
        for(i = 0; i < data.getNrOfRows(); i++)
        {	
        	nrMatricol = data.get(i,j);
        	name = data.get(i,j+1);
        	surname = data.get(i,j+2);
        	year = Integer.parseInt(data.get(i,j+3));
        	group = data.get(i,j+4);
        	grade = Integer.parseInt(data.get(i,j+5));
        		
        	student = new Student(nrMatricol, name, surname, group, grade);
        	student.setYear(year);
        		
        	addStudent(student);
        }    
    }
    
    /** 
     * Return, from the internal student list, a student
     * identified by the specified registration number.
     * 
     * @param nrMatricol the registration number against which
     * to match the student
     * @return the desired student
     */
    @Override
    public Student getStudentByReg (String nrMatricol) {    
        for(int i = 0; i < studList.size(); i++) {
            if (studList.get(i).getNrMatricol().equals(nrMatricol)){ 
                return studList.get(i);
            }
        }
        
        return null;
    }
        
    /**
     * Creates a list of students in a certain year,
     * based on the existing internal list of students.
     * 
     * @param year the year against which students are matched
     * @return new list of desired students
     */
    @Override
    public List<Student> getStudentsByYear (int year) {
        List<Student> result = new ArrayList<>();
        
        for(int i = 0; i < studList.size(); i++) {
            if (studList.get(i).getYear() == year){
                result.add(studList.get(i));
            }
        }
        
        return result;
    }
}
