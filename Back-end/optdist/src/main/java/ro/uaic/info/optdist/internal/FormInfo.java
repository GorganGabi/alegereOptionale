package ro.uaic.info.optdist.internal;

import ro.uaic.info.optdist.*;

import org.xwiki.component.annotation.Component;

import java.util.Calendar;
import java.util.List;
import java.util.ArrayList;

/** This class describes a form, its packages and a calendar argument
 * that indicates the date when the form expires.
 * 
 * @see Package
 */
@Component
public class FormInfo implements FormInfoInterface {
    private List<Package> packages;
    private Calendar TTL;
    
    /** Constructor.
     * 
     * @see Package
     * @param newTTL form's time to live
     * @param newPackages form's list of packages
     */
    public FormInfo(Calendar newTTL, List<Package> newPackages)
    {
        this.TTL = newTTL;
        this.packages = newPackages;
    }
    
    /**
     * Constructor without arguments.
     * Only instantiates the list of packages.
     * 
     * @see Package
     */	
    public FormInfo() {
    	packages = new ArrayList<Package>();
    }

	/** The function returns this form's list of packages.
     * 
     * @see Package
     * @return form's list of packages
     */
	public List<Package> getPackages() {
		return packages;
	}
	
	/**Sets the form's list of packages.
     * 
     * @see Package
     * @param packages form's new list of packages
     */
	public void setPackages(List<Package> packages) {
		this.packages = packages;
	}
}
