package ro.uaic.info.optdist.internal;

import ro.uaic.info.optdist.*;

import org.xwiki.component.annotation.Component;

@Component
public class Optional implements OptionalInterface {
    String name;
    int year;
    int semester;
    private int enrolledStudents;
    int capacity;
    String ID;
    float lastGrade;
   
    public Optional(String ID, String name, int year, int semester) {
        this.name = name;
        this.year = year;
        this.semester = semester;
        this.capacity = capacity;
        this.ID = ID;
        this.enrolledStudents = 0;
    }
   
    public Optional(String name, int year, int semester, int capacity, String ID) {
        this.name = name;
        this.year = year;
        this.semester = semester;
        this.capacity = capacity;
        this.ID = ID;
        this.enrolledStudents = 0;
    }
    
    @Override
    public int getCapacity()
    {
        return capacity;
    }
    
    @Override
    public int getYear()
    {
        return year;
    }
    
    @Override
    public int getSemester()
    {
        return semester;
    }
    
    @Override
    public String getName()
    {
        return name;
    }
    
    @Override
    public String getID()
    {
        return ID;
    }

    /**
     * @return the enrolledStudents
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

    public float getLastGrade() {
        return lastGrade;
    }

    public void setLastGrade(float lastGrade) {
        this.lastGrade = lastGrade;
    }

    void setCapacity(int i) { 
        this.capacity = i;
    }
}
