
package OptDist;


public abstract class Student {
    private String nrMatricol;
    private String name;
    private String surname;
    private String group;
    private float grade;

    public Student(String nrMatricol, String name, String surname, String group, float grade) {
        this.nrMatricol = nrMatricol;
        this.name = name;
        this.surname = surname;
        this.group = group;
        this.grade = grade;
    }
    
    /**
     * @return the registration number of the student
     */
    abstract public String getNrMatricol();

    /**
     * @return the name of the student
     */
    abstract public String getName();

    /**
     * @return the surname of the student
     */
    abstract public String getSurname();

    /**
     * @return the group of the student
     */
    abstract public String getGroup();

    /**
     * @return the grade of the student
     */
    abstract public float getGrade();
    
}

