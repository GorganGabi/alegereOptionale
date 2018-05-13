
package OptDist;

public class Optional {
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
    
    int getID()
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
