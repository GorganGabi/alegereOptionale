package ro.uaic.info.optdist.internal;

import ro.uaic.info.optdist.*;
import org.xwiki.component.annotation.Component;

/**
 * Stores all data relevant to a student.
 * <p>
 * Specifically, this data is the student's registration number
 * (<i>ro numar matricol</i>), name, surname, group, grade (<i>ro medie</i>),
 * year of study and optional course preferences.
 * 
 * Aside from storing a student's data, this class does nothing.
 * 
 * 
 * @see Student
 */
@Component
public class Student implements StudentInterface {
    private String nrMatricol;
    private String name;
    private String surname;
    private String group;
    private float grade;
    private int year;
    private Preference preference;

    /**
     * Assigns to the created instance, in this order,
     * the registration number, name, surname, group and grade.
     * <p>
     * The optional preferences aren't set in the constructor since the
     * preferences may be unknown at the time of importing students.
     * 
     * @param nrMatricol student's registration number
     * @param name student's name
     * @param surname student's surname
     * @param group student's group
     * @param grade student's grade
     */
    public Student(String nrMatricol, String name, String surname, String group, float grade) {
        this.nrMatricol = nrMatricol;
        this.name = name;
        this.surname = surname;
        this.group = group;
        this.grade = grade;
    }
    
    /**
     * Returns the optional preferences of the student.
     * 
     * @return student's preferences
     * @see Preference
     */
    public Preference getPreference() {
        return preference;
    }

    /**
     * Returns the student's registration number.
     * 
     * @return student's registration number
     */
    @Override
    public String getNrMatricol()
    {
        return nrMatricol;
    }

    /**
     * Returns the student's name.
     * 
     * @return student's name
     */
    @Override
    public String getName()
    {
        return name;
    }

    /**
     * Returns the student's surname.
     * 
     * @return student's surname
     */
    @Override
    public String getSurname()
    {
        return surname;
    }

    /**
     * Returns the student's group.
     * 
     * @return student's group
     */
    @Override
    public String getGroup()
    {
        return group;
    }
    
    /**
     * Returns the student's grade.
     * 
     * @return student's grade
     */
    @Override
    public float getGrade()
    {
        return grade;
    }


    
    /**
     * Returns the student's year.
     * 
     * @return student's year
     */
    @Override
    public int getYear() {
        return year;
    }

    /**
     * Sets the student's registration number.
     * 
     * @param nrMatricol the student's new registration number
     */
    public void setNrMatricol(String nrMatricol) {
        this.nrMatricol = nrMatricol;
    }

    /**
     * Sets the student's name.
     * 
     * @param name the student's new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the student's surname.
     * 
     * @param surname the student's new surname
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Sets the student's group.
     * 
     * @param group the student's new group
     */
    public void setGroup(String group) {
        this.group = group;
    }

    /**
     * Sets the student's grade.
     * 
     * @param grade the student's new grade
     */
    public void setGrade(float grade) {
        this.grade = grade;
    }

    /**
     * Sets the student's year.
     * 
     * @param year the student's new year
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * Sets the student's preferences.
     * 
     * @param newPreference the student's new preferences
     * @see Preference
     */
    @Override
    public void setPreference (Preference newPreference)
    {
        this.preference=newPreference;
    }
}

