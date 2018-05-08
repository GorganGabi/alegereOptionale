
package OptDist;

import java.util.Map;

public class ExcelDump {
    
    private Map<String,Map<String,String>> data;
    
    public String get(String x,String y)
    {
        if (data.containsKey(x)) {
            Map map = data.get(x);
            
            if (map.containsKey(y)) {
                return (String) map.get(y);
            }
        }
        
        System.out.println(String.format("[ExcelDump] Could not find value for pair <%s, %s>!", x, y));
        return null;
    }
    
    public void ExcelDump(Map<String,Map<String,String>> newData)
    {
        this.data = newData;
    }
}
