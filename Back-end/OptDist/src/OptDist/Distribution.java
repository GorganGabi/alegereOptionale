
package OptDist;

import java.util.List;
import java.util.Map;

public class Distribution {
    StudentAdministration students;
    DistributionAlgorithm algorithm;
    Map<Student, List<Optional>> result;
    
    void export()
    {
        
    }
    
    void start()
    {
        algorithm.match(students);
    }
    
    void setAlgorithm(DistributionAlgorithm newAlgorithm)
    {
        this.algorithm = newAlgorithm;
    }
    
    Distribution(StudentAdministration newStudents)
    {
        this.students = newStudents;
    }
    
    Distribution(StudentAdministration newStudents, DistributionAlgorithm newAlgorithm)
    {
        this.students = newStudents;
        this.algorithm = newAlgorithm;
        
    }
}
