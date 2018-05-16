package ro.uaic.info.optdist;

import org.xwiki.component.annotation.Role;

@Role
public interface OptionalInterface {
	int GetCapacity();
	int GetYear();
	int GetSemester();
	String GetName();
	int getID();
}
