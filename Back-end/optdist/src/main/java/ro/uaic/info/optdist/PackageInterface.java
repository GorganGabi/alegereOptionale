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
	public String getID();
        public void replace (ro.uaic.info.optdist.internal.Package newPackage);
        public void setOptionals(List<Optional> optionals);
        public void setYear(int year);
        public void setSemester(int semester);
        public void setName(String name);
        public void setID(String ID);
}
