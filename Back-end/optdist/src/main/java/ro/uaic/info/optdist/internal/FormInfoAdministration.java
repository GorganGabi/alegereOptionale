package ro.uaic.info.optdist.internal;

import ro.uaic.info.optdist.*;

import org.xwiki.component.annotation.Component;

import java.util.*;

@Component
public class FormInfoAdministration implements FormInfoAdministrationInterface {
	private List<FormInfo> forms;
	
        @Override
	public void generateForms(StudentAdministration students, PackageAdministration packages) {
		
		for( Student i : students.studList ) {
			FormInfo form = new FormInfo();
			
			for( Package j : packages.getPackageList() ) {
				if( i.getYear() + 1 == j.getYear() )
					form.getPackages().add(j);
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
