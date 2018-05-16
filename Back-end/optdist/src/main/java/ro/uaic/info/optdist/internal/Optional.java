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
    int ID;
    float lastGrade;
   
    public Optional(String name, int year, int semester, int capacity, int ID) {
        this.name = name;
        this.year = year;
        this.semester = semester;
        this.capacity = capacity;
        this.ID = ID;
        this.enrolledStudents = 0;
    }
    
    int getCapacity()
    {
        return capacity;
    }
    
    int getYear()
    {
        return year;
    }
    
    int getSemester()
    {
        return semester;
    }
    
    String getName()
    {
        return name;
    }
    
    public int getID()
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

    @Override
    public int GetCapacity() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int GetYear() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int GetSemester() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String GetName() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
