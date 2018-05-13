
package OptDist;

import java.util.ArrayList;
import java.util.List;

public class PackageAdministration {
    private List<Package> packageList;
    
    public PackageAdministration(){
        packageList = new ArrayList<>();
    }
    
    public void importPackages(ExcelDump data){
        System.out.println("[PackageAdministration] Excel imported!");
    }

    public void addPackage (Package newPackage){
        packageList.add(newPackage);
    }

    public List<Package> getPackageList(){
        return packageList;
    }
}
