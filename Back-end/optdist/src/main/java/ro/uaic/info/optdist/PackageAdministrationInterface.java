package ro.uaic.info.optdist;

import ro.uaic.info.optdist.internal.ExcelDump;
import ro.uaic.info.optdist.internal.Package;

import java.util.List;

import org.xwiki.component.annotation.Role;

@Role
public interface PackageAdministrationInterface {
	public void importPackages(ExcelDump data);
	public void addPackage (Package newPackage);
	public List<Package> getPackageList();
}
