package ro.uaic.info.optdist;

import org.xwiki.component.annotation.Role;

@Role
public interface ExcelDumpInterface {
	public String get(int x, int y);
}
