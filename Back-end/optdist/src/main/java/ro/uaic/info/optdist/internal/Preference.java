package ro.uaic.info.optdist.internal;

import java.util.HashMap;
import ro.uaic.info.optdist.*;
import org.xwiki.component.annotation.Component;

//import com.sun.xml.internal.bind.v2.runtime.reflect.Lister;

import java.util.List;
import java.util.Map;

/**
 * This class is used to represent the preference of one student,
 * as a map between each package and an ordered list of optionals.
 * The map is the variable <code>preference</code>.
 */
@Component
public class Preference implements PreferenceInterface {
    private  Map<Package, List<Optional>> preference;

    /**
     * Constructor.
     * @param studentInput the input from a student
     */
    public Preference(FormResponse studentInput)
    {
        this.preference = new HashMap<>();
        
        for (Map.Entry<Package, List<Optional>> entry : studentInput.getPrefs().entrySet()){
                preference.put(entry.getKey(),entry.getValue());
        }
    }

    /**
     * @return the preference map
     */
    public Map<Package, List<Optional>> getPreference() {
        return preference;
    }

    /**
     * @param preference the preference map to set
     */
    public void setPreference(Map<Package, List<Optional>> preference) {
        this.preference = preference;
    }
}
