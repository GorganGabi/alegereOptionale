package OptDist;

public abstract class Student {
    private String nrMatricol;
    private String name;
    private String surname;
    private String group;
    private int year;
    private float grade;
    private Preference preference;

    public Student(String nrMatricol, String name, String surname, String group, int an, float grade) {
        this.nrMatricol = nrMatricol;
        this.name = name;
        this.surname = surname;
        this.group = group;
        this.year = year;
        this.grade = grade;
    }

    public Student(String nrMatricol, String name, String surname, String group, int an) {
        this.nrMatricol = nrMatricol;
        this.name = name;
        this.surname = surname;
        this.group = group;
        this.year = year;
    }
    
    /**
     * @return the registration number of the student
     */
    public String getNrMatricol(){
        return nrMatricol;
    }

    /**
     * @return the name of the student
     */
    public String getName(){
        return name;
    }

    /**
     * @return the surname of the student
     */
    public String getSurname(){
        return surname;
    }

    /**
     * @return the group of the student
     */
    public String getGroup(){
        return group;
    }

    /**
     * @return the grade of the student
     */
    public float getGrade(){
        return grade;
    }

    public void setPreference(Preference preference) {
        this.preference = preference;
    }

    public int getYear() {
        return year;
    }
}
