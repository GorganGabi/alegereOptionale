package ro.uaic.info.optdist.internal;

import ro.uaic.info.optdist.*;

import org.xwiki.component.annotation.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class FormResponse implements FormResponseInterface {
    private String nrMatricol;
    private Map<Package, List<Optional>> prefs;
    
    @Override
    public void importfromDB(String nrMatricol)
    {
        System.out.println("[FormResponse] importFromDB");
    }
    
    @Override
    public void exportToDB()
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

    @Override
    public String getNrMatricol() {
        return nrMatricol;
    }

    @Override
    public Map<Package, List<Optional>> getPrefs() {
        return prefs;
    }
}
