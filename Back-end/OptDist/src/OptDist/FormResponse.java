
package OptDist;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FormResponse {
    private String nrMatricol;
    private Map<Package, List<Optional>> prefs;
    
    void importfromDB(String nrMatricol)
    {
        System.out.println("[FormResponse] importFromDB");
    }
    
    void exportToDB()
    {
        System.out.println("[FormResponse] exportToDB");
    }
    
    public FormResponse(String newNrMatricol, Map<Package, List<Optional>> newPrefs)
    {
        this.nrMatricol = newNrMatricol;
        this.prefs = newPrefs;
    }
    
    public FormResponse()
    {
        this.nrMatricol = "undefined";
        this.prefs = new HashMap<>();
    }

    public String getNrMatricol() {
        return nrMatricol;
    }

    public Map<Package, List<Optional>> getPrefs() {
        return prefs;
    }
}
