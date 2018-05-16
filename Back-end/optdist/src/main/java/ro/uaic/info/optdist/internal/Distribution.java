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
    
    public Distribution(StudentAdministration newStudents)
    {
        this.students = newStudents;
    }
    
    public Distribution(StudentAdministration newStudents, DistributionAlgorithm newAlgorithm)
    {
        this.students = newStudents;
        this.algorithm = newAlgorithm;
    }
    
    @Override
    public Map<Student, List<Optional>> getResult()
    {
        return this.result;
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
}
