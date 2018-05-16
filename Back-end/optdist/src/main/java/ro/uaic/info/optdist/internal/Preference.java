package ro.uaic.info.optdist.internal;

import ro.uaic.info.optdist.*;
import org.xwiki.component.annotation.Component;

//import com.sun.xml.internal.bind.v2.runtime.reflect.Lister;

import java.util.List;
import java.util.Map;

@Component
public class Preference implements PreferenceInterface {
    private  Map<Package, List<Optional>> preference;

    public Preference(FormResponse studentInput)
    {
        for (Map.Entry<Package, List<Optional>> entry : studentInput.getPrefs().entrySet()){
                preference.put(entry.getKey(),entry.getValue());
        }
    }

    /**
     * @return the preference
     */
    public Map<Package, List<Optional>> getPreference() {
        return preference;
    }

    /**
     * @param preference the preference to set
     */
    public void setPreference(Map<Package, List<Optional>> preference) {
        this.preference = preference;
    }
}
