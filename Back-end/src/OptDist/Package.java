package OptDist;

import java.util.List;

public abstract class Package {
    private List<Optional> optionals;
    private int year;
    private int semester;
    private String name;
    
    public Package(List<Optional> optionals, int year, int semester, String name) {
        this.optionals = optionals;
        this.year = year;
        this.semester = semester;
        this.name = name;
    }
    
    /**
     * @return the optional list (as List<Optionals>)
     */
    abstract public List<Optional> getOptionals();

    /**
     * @return the year of the package (as int)
     */
    abstract public int getYear();

    /**
     * @return the semester of the package (as int)
     */
    abstract public int getSemester();

    /**
     * @return the name of the package (as String)
     */
    abstract public String getName();
}
