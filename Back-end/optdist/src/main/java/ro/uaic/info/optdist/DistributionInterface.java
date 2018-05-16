package ro.uaic.info.optdist;

import java.util.List;
import java.util.Map;
import org.xwiki.component.annotation.Role;

import ro.uaic.info.optdist.internal.DistributionAlgorithm;
import ro.uaic.info.optdist.internal.Optional;
import ro.uaic.info.optdist.internal.Student;

@Role
public interface DistributionInterface {
	public Map<Student, List<Optional>> getResult();
	void start();
	void setAlgorithm(DistributionAlgorithm newAlgorithm);
}
