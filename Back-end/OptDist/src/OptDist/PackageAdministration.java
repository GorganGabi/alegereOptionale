
package OptDist;

import java.util.ArrayList;
import java.util.List;

public class PackageAdministration {
    private List<Package> packageList;

    public PackageAdministration(){
        packageList = new ArrayList<>();
    }
    private void importPackages(ExcelDump data){

    }

    private void addPackage (Package newPackage){
        packageList.add(newPackage);
    }

    private List<Package> getPackageList(){
        return packageList;
    }
}
