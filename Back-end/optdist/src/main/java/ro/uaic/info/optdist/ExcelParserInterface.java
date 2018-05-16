package ro.uaic.info.optdist;

import ro.uaic.info.optdist.internal.ExcelDump;
import org.xwiki.component.annotation.Role;

@Role
public interface ExcelParserInterface {
	ExcelDump parse(String Path);
}
