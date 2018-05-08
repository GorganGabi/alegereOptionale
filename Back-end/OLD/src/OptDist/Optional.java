package OptDist;

public abstract class Optional {
    String name;
    int year;
    int semester;
    int capacity;
    
    abstract int GetCapacity();
    abstract int GetYear();
    abstract int GetSemester();
    abstract String GetName();
}
