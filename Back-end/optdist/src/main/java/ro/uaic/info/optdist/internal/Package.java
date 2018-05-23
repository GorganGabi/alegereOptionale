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
    
    /**
     * Copies all of <code>newPackage</code>'s members into the
     * current instance (i.e.&nbsp;<code>this</code>).
     * 
     * @param newPackage the package that is to be copied into <code>this</code> instance
     */
    @Override
    public void replace (Package newPackage) {
        this.optionals = newPackage.optionals;
        this.year = newPackage.year;
        this.semester = newPackage.semester;
        this.ID = newPackage.ID;
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
    @Override
    public List<Optional> getOptionals()
    {
        return optionals;
    }
    
    /**
     * Returns, if there is any, an optional with the given ID 
     * in the current package.
     * 
     * @param ID
     * @return 
     */
    public Optional getOptionalByID (String ID) {
        for(int i = 0; i < optionals.size(); i++) {
            if (optionals.get(i).getID().equals(ID)){ 
                return optionals.get(i);
            }
        }
        
        return null;
    }

     /** The function returns this package's year.
     * 
     * @return package's year
     */
    @Override
    public int getYear()
    {
        return year;
    }

    /** The function returns this package's semester.
    * 
    * @return package's semester
    */
    @Override
    public int getSemester()
    {
        return semester;
    }
	
    /** The function returns this package's name.
     * 
     * @return package's name
     */
    @Override
    public String getName()
    {
        return name;
    }
    
    /** The function returns this package's ID.
     * 
     * @return package's ID
     */
    @Override
    public String getID()
    {
        return ID;
    }
	
    /**Sets the package's optional list.
     * 
     * @param optionals package's new optional list
     * @see Optional
     */
    @Override
    public void setOptionals(List<Optional> optionals) {
        this.optionals = optionals;
    }

    /**Sets the package's year.
     * 
     * @param year package's new oyear
     */
    @Override
    public void setYear(int year) {
        this.year = year;
    }

    /**Sets the package's semester.
     * 
     * @param semester package's new semester
     */
    @Override
    public void setSemester(int semester) {
        this.semester = semester;
    }

    /**Sets the package's name.
     * 
     * @param name package's new name
     */
    @Override
    public void setName(String name) {
        this.name = name;
    }

    /**Sets the package's ID.
     * 
     * @param ID package's new ID
     */
    @Override
    public void setID(String ID) {
        this.ID = ID;
    }
}
