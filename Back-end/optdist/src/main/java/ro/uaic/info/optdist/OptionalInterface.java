package ro.uaic.info.optdist;

import org.xwiki.component.annotation.Role;

@Role
public interface OptionalInterface {
	int getCapacity();
	int getYear();
	int getSemester();
	String getName();
	String getID();
}
