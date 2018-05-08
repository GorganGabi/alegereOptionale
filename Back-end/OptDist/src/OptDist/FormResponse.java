
package OptDist;

import java.util.List;
import java.util.Map;

public class FormResponse {
    private String nrMatricol;
    private Map<Integer, List<Integer>> prefs;
    
    void importfromDB(String nrMatricol)
    {
        
    }
    
    void exportToDB()
    {
        
    }
    
    public FormResponse(String newNrMatricol, Map<Integer, List<Integer>> newPrefs)
    {
        this.nrMatricol = newNrMatricol;
        this.prefs = newPrefs;
    }
    
    public FormResponse()
    {
        
    }
}
