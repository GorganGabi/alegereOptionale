package ro.uaic.info.optdist;

import ro.uaic.info.optdist.internal.ExcelDump;
import ro.uaic.info.optdist.internal.Package;
import ro.uaic.info.optdist.internal.StudentAdministration;

import java.util.List;
import org.xwiki.component.annotation.Role;

@Role
public interface PackageAdministrationInterface {
	public void importPackages(String url) throws Exception;
	public void addPackage (Package newPackage);
	public List<Package> getPackageList();
        public Package getPackageByID (String ID);
        public List<Package> getPackagesByRank (int year, int semester);
        public void setImplicitCapacity(StudentAdministration studs);
}
