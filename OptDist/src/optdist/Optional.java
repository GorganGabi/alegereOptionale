/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package optdist;

/**
 *
 * @author gabih
 */
public class Optional {
    String name;
    int year;
    int semester;
    int capacity;
    Optional()
    {
        
    }
    int GetCapacity()
    {
        return this.capacity;
    }
    int GetYear()
    {
        return this.year;
    }
    int GetSemester()
    {
        return this.semester;
    }
    String GetName()
    {
        return this.name;
    }
}
