
package OptDist;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FormResponse {
    private String nrMatricol;
    private Map<Integer, List<Integer>> prefs;
    
    void importfromDB(String nrMatricol)
    {
        System.out.println("[FormResponse] importFromDB");
    }
    
    void exportToDB()
    {
        System.out.println("[FormResponse] exportToDB");
    }
    
    public FormResponse(String newNrMatricol, Map<Integer, List<Integer>> newPrefs)
    {
        this.nrMatricol = newNrMatricol;
        this.prefs = newPrefs;
    }
    
    public FormResponse()
    {
        this.nrMatricol = "undefined";
        this.prefs = new HashMap<Integer, List<Integer>>();
    }

    public String getNrMatricol() {
        return nrMatricol;
    }

    public Map<Integer, List<Integer>> getPrefs() {
        return prefs;
    }
}
