
package OptDist;

import com.sun.xml.internal.bind.v2.runtime.reflect.Lister;

import java.util.List;
import java.util.Map;

public class Preference {
    private  Map<Package, List<Optional>> preference;

    public Preference(FormResponse studentInput)
    {
        for (Map.Entry<Integer, List<Integer>> entry : studentInput.getPrefs().entrySet()){
                Package packagee = new Package();
                preference.put(entry.getKey(),entry.getValue());
        }
    }
}
