
package OptDist;

import java.util.List;

public abstract class Package {
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
    
    abstract public List<Optional> getOptionals();

    abstract public int getYear();

    abstract public int getSemester();

    abstract public String getName();
    
    abstract public String getID();
}
