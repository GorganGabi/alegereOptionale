package ro.uaic.info.optdist.internal;

import ro.uaic.info.optdist.*;

import org.xwiki.component.annotation.Component;

import java.util.List;
import java.util.Map;

@Component
public class Distribution implements DistributionInterface {
    StudentAdministration students;
    DistributionAlgorithm algorithm;
    Map<Student, List<Optional>> result;
    
    @Override
    public void export()
    {
        
    }
    
    @Override
    public void start()
    {
        algorithm.match(students);
    }
    
    @Override
    public void setAlgorithm(DistributionAlgorithm newAlgorithm)
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
