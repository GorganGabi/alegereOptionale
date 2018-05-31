package ro.uaic.info.optdist;

import org.xwiki.component.annotation.Role;

import ro.uaic.info.optdist.internal.Student;
import ro.uaic.info.optdist.internal.Optional;
import ro.uaic.info.optdist.internal.StudentAdministration;

import java.util.List;
import java.util.Map;
import ro.uaic.info.optdist.internal.PackageAdministration;

@Role
public interface DistributionAlgorithmInterface {
	Map<Student,List<Optional>> match(StudentAdministration students, PackageAdministration pack);
}
