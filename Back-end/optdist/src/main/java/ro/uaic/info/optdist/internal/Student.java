package ro.uaic.info.optdist.internal;

import ro.uaic.info.optdist.*;
import org.xwiki.component.annotation.Component;

@Component
public class Student implements StudentInterface {
    private String nrMatricol;
    private String name;
    private String surname;
    private String group;
    private float grade;
    private int year;
    private Preference preference;

    public Student(String nrMatricol, String name, String surname, String group, float grade) {
        this.nrMatricol = nrMatricol;
        this.name = name;
        this.surname = surname;
        this.group = group;
        this.grade = grade;
    }

    @Override
    public String getNrMatricol()
    {
        return nrMatricol;
    }

    @Override
    public String getName()
    {
        return name;
    }

    @Override
    public String getSurname()
    {
        return surname;
    }

    @Override
    public String getGroup()
    {
        return group;
    }
    
    @Override
    public float getGrade()
    {
        return grade;
    }

    @Override
    public void setPreference (Preference newPreference)
    {
        this.preference=newPreference;
    }

    @Override
    public int getYear() {
        return year;
    }

    /**
     * @param nrMatricol the nrMatricol to set
     */
    public void setNrMatricol(String nrMatricol) {
        this.nrMatricol = nrMatricol;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @param surname the surname to set
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * @param group the group to set
     */
    public void setGroup(String group) {
        this.group = group;
    }

    /**
     * @param grade the grade to set
     */
    public void setGrade(float grade) {
        this.grade = grade;
    }

    /**
     * @param year the year to set
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * @return the preference
     */
    public Preference getPreference() {
        return preference;
    }
}

