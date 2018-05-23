package ro.uaic.info.optdist.internal;

import ro.uaic.info.optdist.*;

import org.xwiki.component.annotation.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * This class holds and provides access to the data extracted from the form completed by the student.
 * <p>
 * The class contains registration number (<i>ro numar matricol</i>) of the student
 * and a map with his preferences like this: for every package available, 
 * the student order a list of optionals (for more details, take a look at
 * the form page).
 * 
 * @see Package
 * @see Optional
 * @see HashMap
 * @author ggorgan
 */
@Component
public class FormResponse implements FormResponseInterface {

    private String nrMatricol;
    private Map<Package, List<Optional>> prefs;
    
    /**
     * This method imports the preferences of a student (identified by registration number) to the database
     * @param nrMatricol the registration number of the form to load
     */
    @Override
    public void importFromDB(String nrMatricol)
    {
        System.out.println("[FormResponse] importFromDB");
    }
    /**
     * This method exports the preferences of a student (identified by registration number) to the database
     */
    @Override
    public void exportToDB()
    {
        System.out.println("[FormResponse] exportToDB");
    }
    /**
     * This is a simple constructor for the FormResponse class
     * @param newNrMatricol new instance's registration number
     * @param newPrefs new instance's preferences
     */
    public FormResponse(String newNrMatricol, Map<Package, List<Optional>> newPrefs)
    {
        this.nrMatricol = newNrMatricol;
        this.prefs = newPrefs;
    }
    /**
     * This is a simple constructor for the FormResponse class
     * The registration number is undefined
     * Preferences are initialized as HashMap 
     */
    public FormResponse()
    {
        this.nrMatricol = "undefined";
        this.prefs = new HashMap<>();
    }
    /**
     * 
     * @return the registration number of the student
     */
    @Override
    public String getNrMatricol() {
        return nrMatricol;
    }
    /**
     * Sets a new registration number for the current instance.
     * 
     * @param newNrMatricol the new registration number to be set
     */
    @Override
    public void setNrMatricol(String newNrMatricol) {
        this.nrMatricol = newNrMatricol;
    }

    /**
     * Sets a new preferences mapping for the current instance.
     * 
     * @param newPrefs the new preferences mapping to be set
     */
    @Override
    public void setPrefs(Map<ro.uaic.info.optdist.internal.Package, List<Optional>> newPrefs) {
        this.prefs = newPrefs;
    }
    /**
     * 
     * @return the preferences of a student
     */
    @Override
    public Map<Package, List<Optional>> getPrefs() {
        return prefs;
    }
}
