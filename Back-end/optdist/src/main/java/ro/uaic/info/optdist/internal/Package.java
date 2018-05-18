package ro.uaic.info.optdist.internal;

import ro.uaic.info.optdist.*;

import org.xwiki.component.annotation.Component;

import java.util.List;

/**
 * Contains a static attribute, packageCount, that counts the packages as we add them
 * and all the attributes that describe a package: a list of optionals, 
 * a year, a semester, a name and an ID.
 *
 */
@Component
public class Package implements PackageInterface{
    private List<Optional> optionals;
    private int year;
    private int semester;
    private String name;
    private String ID;
    private static int packageCount = 0;
    
	/** Constructor that besides assigning also raises the packageCount.
     * 
     * @param optionals this package's list of optionals
     * @param year the package's year
     * @param semester the package's semester 
     * @param ID the package's ID
     */
    public Package(List<Optional> optionals, int year, int semester, String ID) {
        this.optionals = optionals;
        this.year = year;
        this.semester = semester;
        this.ID = ID;
	packageCount++;
    }
 /*   
    public Package()
    {
        packageCount++;
        this.ID = packageCount;
    }
*/
    /** The function returns this package's list of optionals.
     * 
     * @see Optional
     * @return A list of Optionals
     */
    public List<Optional> getOptionals()
    {
        return optionals;
    }

     /** The function returns this package's year.
     * 
     * @return package's year
     */
    public int getYear()
    {
        return year;
    }

    /** The function returns this package's semester.
    * 
    * @return package's semester
    */
    public int getSemester()
    {
        return semester;
    }
	
    /** The function returns this package's name.
     * 
     * @return package's name
     */
    public String getName()
    {
        return name;
    }
    
    /** The function returns this package's ID.
     * 
     * @return package's ID
     */
    public String getID()
    {
        return ID;
    }
	
    /**Sets the package's optional list.
     * 
     * @param optionals package's new optional list
     * @see Optional
     */
    public void setOptionals(List<Optional> optionals) {
        this.optionals = optionals;
    }

    /**Sets the package's year.
     * 
     * @param year package's new oyear
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**Sets the package's semester.
     * 
     * @param semester package's new semester
     */
    public void setSemester(int semester) {
        this.semester = semester;
    }

    /**Sets the package's name.
     * 
     * @param name package's new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**Sets the package's ID.
     * 
     * @param ID package's new ID
     */
    public void setID(String ID) {
        this.ID = ID;
    }
}
