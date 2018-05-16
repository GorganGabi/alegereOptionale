package ro.uaic.info.optdist.internal;

import ro.uaic.info.optdist.*;

import org.xwiki.component.annotation.Component;

@Component
public class ExcelParser implements ExcelParserInterface {
    @Override
    public ExcelDump parse(String Path)
    {
        System.out.println(String.format("[ExcelParser] Path = %s", Path));        
        return null;
    }
}
