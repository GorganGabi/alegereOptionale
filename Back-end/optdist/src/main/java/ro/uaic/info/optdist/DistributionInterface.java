package ro.uaic.info.optdist;

import org.xwiki.component.annotation.Role;

import ro.uaic.info.optdist.internal.DistributionAlgorithm;

@Role
public interface DistributionInterface {
	void export();
	void start();
	void setAlgorithm(DistributionAlgorithm newAlgorithm);
}
