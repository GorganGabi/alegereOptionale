
package OptDist;


public abstract class Student {
    private String nrMatricol;
    private String name;
    private String surname;
    private String group;
    private float grade;
    private Preference preference;

    public Student(String nrMatricol, String name, String surname, String group, float grade) {
        this.nrMatricol = nrMatricol;
        this.name = name;
        this.surname = surname;
        this.group = group;
        this.grade = grade;
    }
    

    abstract public String getNrMatricol();

    abstract public String getName();

    abstract public String getSurname();

    abstract public String getGroup();

    abstract public float getGrade();
    
    abstract public void setPreference (Preference newPreference);
    
}

