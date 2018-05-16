package ro.uaic.info.optdist.internal;

import ro.uaic.info.optdist.*;

import org.xwiki.component.annotation.Component;

import java.util.Calendar;
import java.util.List;
import java.util.ArrayList;

@Component
public class FormInfo implements FormInfoInterface {
    private List<Package> packages;
    private Calendar TTL;
    
    public FormInfo(Calendar newTTL, List<Package> newPackages)
    {
        this.TTL = newTTL;
        this.packages = newPackages;
    }
    
    public FormInfo() {
    	packages = new ArrayList<Package>();
    }

	public List<Package> getPackages() {
		return packages;
	}

	public void setPackages(List<Package> packages) {
		this.packages = packages;
	}
}
