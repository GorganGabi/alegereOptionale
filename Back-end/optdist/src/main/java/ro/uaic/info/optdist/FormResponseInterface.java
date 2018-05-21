package ro.uaic.info.optdist;

import ro.uaic.info.optdist.internal.Optional;
import java.util.List;
import org.xwiki.component.annotation.Role;

import java.util.Map;

@Role
public interface FormResponseInterface {
	void importFromDB(String nrMatricol);
	void exportToDB();
	public String getNrMatricol();
	public Map<ro.uaic.info.optdist.internal.Package, List<Optional>> getPrefs();
}
