
package OptDist;

import java.util.List;

public class Package {
    private List<Optional> optionals;
    private int year;
    private int semester;
    private String name;
    private int ID;
    
    public Package(List<Optional> optionals, int year, int semester, String name) {
        this.optionals = optionals;
        this.year = year;
        this.semester = semester;
        this.name = name;
    }
    
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
}
