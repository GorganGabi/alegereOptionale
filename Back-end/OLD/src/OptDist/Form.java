package OptDist;
import java.util.Date;

public abstract class Form {
     Date TTL;
     String UnivYear;
    
     abstract void setTTL(Date TTL);
     abstract Date getTTL();
     abstract void setUnivYear(String UnivYear);
     abstract String getUnivYear();
}
