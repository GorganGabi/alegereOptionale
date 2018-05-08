package OptDist;

import java.util.List;

public abstract class Package {
    private List<Optional> optionals;
    private int year;
    private int semester;
    private String name;
    private int ID;
    
    public Package(List<Optional> optionals, int year, int semester, String name, int id) {
        this.optionals = optionals;
        this.year = year;
        this.semester = semester;
        this.name = name;
        this.ID = id;
    }
    
    /**
     * @return the optional list (as List<Optionals>)
     */
    public List<Optional> getOptionals(){
        return optionals;
    }

    /**
     * @return the year of the package (as int)
     */
    public int getYear(){
        return year;
    }

    /**
     * @return the semester of the package (as int)
     */
    public int getSemester(){
        return semester;
    }

    /**
     * @return the name of the package (as String)
     */
    public String getName(){
        return name;
    }
}
