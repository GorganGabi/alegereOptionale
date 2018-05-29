package ro.uaic.info.optdist.internal;

import ro.uaic.info.optdist.*;

import org.xwiki.component.annotation.Component;


import java.util.List;
import java.util.ArrayList;

/** This class describes a form, its packages and a calendar argument
 * that indicates the date when the form expires.
 * 
 * @see Package
 */
@Component
public class FormInfo implements FormInfoInterface {
    private Student student;
    private List<Package> packages;
    
    /** Creates a <code>FormInfo</code> with the given list of packages.
     * 
     * @param newStudent
     * @param newPackages form's list of packages
     * 
     * @see Package
     */
    public FormInfo(Student newStudent, List<Package> newPackages) {        
        this.student = null;
        this.packages = newPackages;
    }
    
    /**
     * Constructor without arguments.
     * Only instantiates the list of packages.
     * 
     * @see Package
     */	
    public FormInfo() {
        student = null;
    	packages = new ArrayList<>();
    }

    /** The function returns this form's list of packages.
     * 
     * @see Package
     * @return form's list of packages
     */
    @Override
    public List<Package> getPackages() {
        return packages;
    }
	
    /**Sets the form's list of packages.
     * 
     * @see Package
     * @param packages form's new list of packages
     */
    @Override
    public void setPackages(List<Package> packages) {
        this.packages = packages;
    }
    
    /** The function returns this form's student.
     * 
     * @see Package
     * @return form's student
     */
    //@Override
    public Student getStudent() {
        return student;
    }
	
    /**Sets the form's student.
     * 
     * @param newStudent
     * @see Package
     */
    //@Override
    public void setStudent(Student newStudent) {
        this.student = newStudent;
    }
}
