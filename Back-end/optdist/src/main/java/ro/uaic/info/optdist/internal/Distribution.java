package ro.uaic.info.optdist.internal;

import ro.uaic.info.optdist.*;

import org.xwiki.component.annotation.Component;

import java.util.List;
import java.util.Map;

/**
 * 
 * Controls the distribution of the students.
 * Makes a call to the DistributionAlgorithm using the start() method and stores the result.
 */


@Component
public class Distribution implements DistributionInterface {
    StudentAdministration students;
    DistributionAlgorithm algorithm;
    
    /** 
     * Stores the result of the matching algorithm. A hash map which maps a Student to a list of Optionals. The Optionals are stored in the desired order of the student; the closer to the start of the list an Optional is, the more the student desires to enroll into the Optional.
     */
    Map<Student, List<Optional>>
            result = null;
    
    /**
     * 
     * @param newStudents Contains the students along with their preferences.
     */
    public Distribution(StudentAdministration newStudents)
    {
        this.students = newStudents;
    }
    
    /**
     * 
     * @param newStudents Contains the students along with their preferences.
     * @param newAlgorithm Specifies the algorithm which will be used for the distribution of the students.
     */
    
    public Distribution(StudentAdministration newStudents, DistributionAlgorithm newAlgorithm)
    {
        this.students = newStudents;
        this.algorithm = newAlgorithm;
    }
    
    /**
     * 
     * @return Returns the result of the algorithm.
     */
    @Override
    public Map<Student, List<Optional>> getResult()
    {       
        return this.result;
    }
    
    /**
     * Starts the student matching algorithm using the selected algorithm.
     */
      
    @Override
    public void start()
    {
        algorithm.match(students);
    }
    
    /**
     * Sets the algorithm to be used while matching the Students to the Optionals. There's only one at this point, more will be added if needed.
     * @param newAlgorithm 
     */
    
    @Override
    public void setAlgorithm(DistributionAlgorithm newAlgorithm)
    {
        this.algorithm = newAlgorithm;
    }
}
