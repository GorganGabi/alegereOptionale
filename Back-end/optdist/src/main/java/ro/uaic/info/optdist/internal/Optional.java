package ro.uaic.info.optdist.internal;

import ro.uaic.info.optdist.*;

import org.xwiki.component.annotation.Component;

/**
 * Stores all data relevant to an optional.
 * <p>
 * Specifically, this data is the optional's name, year, semester, current
 * enrolled students, capacity, ID and admission grade.
 * 
 */
@Component
public class Optional implements OptionalInterface {
    String name;
    int year;
    int semester;
    private int enrolledStudents;
    int capacity;
    String ID;
    float lastGrade;
   
    /**
     * Constructor.
     * <p>
     * 
     * The values for the fields <code>capacity</code> and <code>enrolledStudents</code> are set to 0.
     * 
     * @param ID (required) id of the optional
     * @param name (required) name of the optional
     * @param year (required) year of the optional
     * @param semester (required) semester of the optional
     */
    public Optional(String ID, String name, int year, int semester) {
        this.name = name;
        this.year = year;
        this.semester = semester;
        this.ID = ID;
        this.enrolledStudents = 0;
        this.capacity = 0;
    }
    
    /**
     * @return the capacity
     */
    @Override
    public int getCapacity()
    {
        return capacity;
    }
    
    /**
     * @return the year
     */
    @Override
    public int getYear()
    {
        return year;
    }
    
    /**
     * @return the semester
     */
    @Override
    public int getSemester()
    {
        return semester;
    }
    
    /**
     * @return the name
     */
    @Override
    public String getName()
    {
        return name;
    }
    
    /**
     * @return the ID
     */
    @Override
    public String getID()
    {
        return ID;
    }

    /**
     * @return the number of enrolled students
     */
    public int getEnrolledStudents() {
        return enrolledStudents;
    }

    /**
     * @param enrolledStudents the enrolledStudents to set
     */
    public void setEnrolledStudents(int enrolledStudents) {
        this.enrolledStudents = enrolledStudents;
    }

    /**
     * @return the last grade
     */
    public float getLastGrade() {
        return lastGrade;
    }
    
    /**
     * @param lastGrade the value of the last grade to set
     */
    public void setLastGrade(float lastGrade) {
        this.lastGrade = lastGrade;
    }
    
    /**
     * @param capacity the capacity to set
     */
    void setCapacity(int i) { 
        this.capacity = i;
    }
}
