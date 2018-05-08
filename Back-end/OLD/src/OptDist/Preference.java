package OptDist;

import java.util.List;
import java.util.Map;

public abstract class Preference {
    Map<Student, List<Package>> preferencesList;
    
    abstract public void setPreferenceList(); 
}
