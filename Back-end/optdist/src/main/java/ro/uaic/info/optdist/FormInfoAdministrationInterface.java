package ro.uaic.info.optdist;

import org.xwiki.component.annotation.Role;

import ro.uaic.info.optdist.internal.StudentAdministration;
import ro.uaic.info.optdist.internal.PackageAdministration;
import ro.uaic.info.optdist.internal.FormInfo;

import java.util.List;

@Role
public interface FormInfoAdministrationInterface {
	void generateForms(StudentAdministration students, PackageAdministration packages);
	public List<FormInfo> getForms();
	public void setForms(List<FormInfo> forms);
}
