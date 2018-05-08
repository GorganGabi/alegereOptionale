package OptDist;

import java.util.*;

public class FormInfoAdministration {
	List<FormInfo> forms;
	
	void generateForms(StudentAdministration students, PackageAdministration packages) {
		
		for( Student i : students.studList ) {
			FormInfo form = new FormInfo();
			
			for( Package j : packages.packageList ) {
				if( i.getYear() + 1 == j.getYear() )
					form.packages.add(j);
			}
			
			forms.add(form);
		}
			
	}

	public List<FormInfo> getForms() {
		return forms;
	}

	public void setForms(List<FormInfo> forms) {
		this.forms = forms;
	}
}
