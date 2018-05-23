package ro.uaic.info.optdist.internal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
/**
 *
 * @author Madalina
 */
public class PreferenceTest {
    
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
     @Test
     public void Test1() {
        List<Optional> optionale = new ArrayList<>();
        Map<Package, List<Optional>> pref = new HashMap<>();
        Optional o1 = new Optional("1", "CDC", 2, 1);
        Optional o2 = new Optional("2", "PLP", 2, 1);
        Optional o3 = new Optional("3", "IC", 2, 1);
        
        optionale.add(o1); optionale.add(o2); optionale.add(o3);
        
        Package pachet1 = new Package(optionale, 2, 1, "100"); 
        pref.put(pachet1, optionale);
        FormResponse f1 = new FormResponse("123AAA", pref);  
        //Preference expPreference = new Preference();
        //expPreference.setPreference(pref);
        Preference expPreference = new Preference(f1);
        
        assertEquals(expPreference, expPreference.getPreference());
        
     }
}
