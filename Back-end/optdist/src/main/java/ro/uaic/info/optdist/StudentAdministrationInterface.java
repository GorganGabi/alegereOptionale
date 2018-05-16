package ro.uaic.info.optdist;

import org.xwiki.component.annotation.Role;

import ro.uaic.info.optdist.internal.Student;
import ro.uaic.info.optdist.internal.ExcelDump;


@Role
public interface StudentAdministrationInterface {
    void orderStudents();
    void addStudent(Student newStudent);
    void addStudent(String nrMatricol, String name, String surname, String group, float grade);
    void importStudents(ExcelDump data);
}
