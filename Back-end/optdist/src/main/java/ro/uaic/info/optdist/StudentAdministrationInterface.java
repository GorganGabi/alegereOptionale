package ro.uaic.info.optdist;

import java.util.List;
import org.xwiki.component.annotation.Role;

import ro.uaic.info.optdist.internal.Student;
import ro.uaic.info.optdist.internal.ExcelDump;


@Role
public interface StudentAdministrationInterface {
    void orderStudents();
    void addStudent(Student newStudent);
    void importStudents(ExcelDump data);
    Student getStudentByReg (String nrMatricol);
    List<Student> getStudentsByYear (int year);
}
