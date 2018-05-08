/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package optdist;
import java.util.Date;
/**
 *
 * @author gabih
 */
public class Form {
     Date TTL;
     String UnivYear;
     Form()
     {
         
     }
     void SetTTL(Date TTL)
     {
         this.TTL = TTL;
     }
     Date GetTTL()
     {
         return TTL;
     }
     void SetUnivYear(String UnivYear)
     {
         this.UnivYear = UnivYear;
     }
     String GetUnivYear()
     {
         return this.UnivYear;
     }
}
