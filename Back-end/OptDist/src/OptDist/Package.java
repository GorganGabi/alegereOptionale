
package OptDist;

import java.util.List;

public class Package {
    private List<Optional> optionals;
    private int year;
    private int semester;
    private String name;
    private int ID;
    private static int packageCount = 0;
    
    public Package(List<Optional> optionals, int year, int semester, String name) {
        this.optionals = optionals;
        this.year = year;
        this.semester = semester;
        this.name = name;
        packageCount++;
        this.ID = packageCount;
    }
 /*   
    public Package()
    {
        packageCount++;
        this.ID = packageCount;
    }
*/
    
    public List<Optional> getOptionals()
    {
        return optionals;
    }

    public int getYear()
    {
        return year;
    }

    public int getSemester()
    {
        return semester;
    }

    public String getName()
    {
        return name;
    }
    
    public int getID()
    {
        return ID;
    }

    /**
     * @param optionals the optionals to set
     */
    public void setOptionals(List<Optional> optionals) {
        this.optionals = optionals;
    }

    /**
     * @param year the year to set
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * @param semester the semester to set
     */
    public void setSemester(int semester) {
        this.semester = semester;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @param ID the ID to set
     */
    public void setID(int ID) {
        this.ID = ID;
    }
}
