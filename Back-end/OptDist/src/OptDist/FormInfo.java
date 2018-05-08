
package OptDist;

import java.util.Calendar;
import java.util.List;

public class FormInfo {
    private List<Package> packages;
    private Calendar TTL;
    
    public FormInfo(Calendar newTTL, List<Package> newPackages)
    {
        this.TTL = newTTL;
        this.packages = newPackages;
    }
}
