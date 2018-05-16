package ro.uaic.info.optdist;

import org.xwiki.component.annotation.Role;

import ro.uaic.info.optdist.internal.Package;

import java.util.List;

@Role
public interface FormInfoInterface {
	public List<Package> getPackages();
	public void setPackages(List<Package> packages);
}
