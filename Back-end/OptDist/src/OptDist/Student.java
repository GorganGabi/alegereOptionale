
package OptDist;


public class Student  {
    private String nrMatricol;
    private String name;
    private String surname;
    private String group;
    public float grade;
    private Preference preference;

    public Student(String nrMatricol, String name, String surname, String group, float grade) {
        this.nrMatricol = nrMatricol;
        this.name = name;
        this.surname = surname;
        this.group = group;
        this.grade = grade;
    }
    

    public String getNrMatricol()
    {
        return nrMatricol;
    }

    public String getName()
    {
        return name;
    }

    public String getSurname()
    {
        return surname;
    }

    public String getGroup()
    {
        return group;
    }
    
    public float getGrade()
    {
        return grade;
    }

    public void setPreference (Preference newPreference)
    {

    }
}

