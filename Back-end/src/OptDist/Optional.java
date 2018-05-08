package OptDist;

public abstract class Optional {
    private String name;
    private int year;
    private int semester;
    private int capacity;
    private int ID;
    
    public int GetCapacity(){
        return capacity;
    }
    public int GetYear(){
        return year;
    }
    public int GetSemester(){
        return semester;
    }
    public String GetName(){
        return name;
    }
    public int getID() {
        return ID;
    }
}
