package ro.uaic.info.optdist.internal;

import ro.uaic.info.optdist.*;

import org.xwiki.component.annotation.Component;

import java.util.*;

/**
 * Manages a list of forms (FormInfo).
 *
 * @see FormInfo
 */
@Component
public class FormInfoAdministration implements FormInfoAdministrationInterface {

    private List<FormInfo> forms;
    private Calendar TTL;

    /**
     * Initialises the internal list with an empty list and the internal
     * time to live with the new, given TTL.
     *
     * @param newTTL the new time to live
     */
    public FormInfoAdministration(Calendar newTTL) {
        if (newTTL.after(Calendar.getInstance())) {
            this.TTL = newTTL;
        } else {
            throw new IllegalArgumentException("Given date is not later than the current time!");
        }
        
        this.forms = new ArrayList<>();
    }

    /**
     * The function parses the list of students that is offered through the
     * first parameter, it creates a new form for each student then parses the
     * package list we receive through the second parameter. If the year in
     * which the student is about to pass into is equal to the year of a package
     * then that package is added to the form of that student.
     *
     * At the end, the form created specifically for the Student is added to the
     * list of forms this class contains.
     *
     * @see StudentAdministration
     * @see PackageAdministration
     * @see Student
     * @see FormInfo
     * @see Package
     * @param students the students that we have to generate forms for
     * @param packages the packages that we have to distribute to the students
     */
    @Override
    public void generateForms(StudentAdministration students, PackageAdministration packages) {
        for (Student i : students.studList) {
            FormInfo form = new FormInfo();
            
            form.setStudent(i);
            
            for (Package j : packages.getPackageList()) {
                if (i.getYear() + 1 == j.getYear()) {
                    form.getPackages().add(j);
                }
            }
            
            forms.add(form);
        }
    }
    
    /**
     * Looks in the internal form list for the provided student's 
     * form data and returns it.
     * 
     * @param forWhom the student whose form data is requested
     * @return the form of the provided student
     */
    public FormInfo getForm (Student forWhom) {
        for (int i = 0; i < forms.size(); i++) {
            if (this.forms.get(i).getStudent().getNrMatricol().equals(forWhom.getNrMatricol())) {
                return this.forms.get(i);
            }
        }
        
        return null;
    }
    
    /**
     * Looks in the internal form list for the provided student's
     * (by registration number) form data and returns it.
     * 
     * @param forWhomNrMat the registration number of 
     * the student whose form data is requested
     * @return the form of the provided student
     */
    public FormInfo getForm (String forWhomNrMat) {
        for (int i = 0; i < forms.size(); i++) {
            if (this.forms.get(i).getStudent().getNrMatricol().equals(forWhomNrMat)) {
                return this.forms.get(i);
            }
        }
        
        return null;
    }
    
    /**
     * The function returns the time to live of the forms.
     * 
     * @return the time to live of the managed forms
     */
    public Calendar getTTL () {
        return this.TTL;
    }
    
    
    /**
     * The function sets the time to live of the forms.
     * 
     * @param newTTL the new time to live to set
     */
    public void setTTL (Calendar newTTL) {
        this.TTL = newTTL;
    }

    /**
     * The function returns a list of all forms.
     *
     * @see FormInfo
     * @return A list of forms
     */
    @Override
    public List<FormInfo> getForms() {
        return forms;
    }

    /**
     * Sets the list of forms from this class in the one we receive as
     * parameter.
     *
     * @see FormInfo
     * @param forms A list of forms.
     */
    @Override
    public void setForms(List<FormInfo> forms) {
        this.forms = forms;
    }
}
