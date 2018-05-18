package ro.uaic.info.optdist.internal;

import ro.uaic.info.optdist.*;

import org.xwiki.component.annotation.Component;

import java.util.*;

/**
 * Manages a list of forms (FormInfo).
 *
 * @see FormInfo
 */
@Component
public class FormInfoAdministration implements FormInfoAdministrationInterface {
	private List<FormInfo> forms;
	
	/**The function parses the list of students that is offered through the first parameter, 
	 * it creates a new form for each student then parses the package list we receive through
	 * the second parameter. If the year in which the student is about to pass into is equal to 
	 * the year of a package then that package is added to the form of that student.
	 * 
	 * At the end, the form created specifically for the Student is added to the list of forms
	 * this class contains.
	 * 
	 * @see StudenttAdministration
	 * @see PackageAdministration
	 * @see Student
	 * @see FormInfo
	 * @see Package
	 * @param students - the students that we have to generate forms for
	 * @param packages - the packages that we have to distribute to the students
	 */
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

    	/** The function returns a list of all forms.
     	* 
     	* @see FormInfo
     	* @return A list of forms
     	*/
	public List<FormInfo> getForms() {
		return forms;
	}

	/**Sets the list of forms from this class in the one we receive as parameter.
	 * 
	 * @see FormInfo
	 * @param forms - A list of forms.
	 */
	public void setForms(List<FormInfo> forms) {
		this.forms = forms;
	}
}
