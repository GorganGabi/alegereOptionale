package ro.uaic.info.optdist;

import ro.uaic.info.optdist.internal.Optional;
import org.xwiki.component.annotation.Role;


import java.util.List;

@Role
public interface PackageInterface {
	public List<Optional> getOptionals();
	public int getYear();
	public int getSemester();
	public String getName();
	public int getID();
}
