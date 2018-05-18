package ro.uaic.info.optdist.internal;

import ro.uaic.info.optdist.*;

import org.xwiki.component.annotation.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * Contains the algorithm which matches Students to Optionals.
 * 
 * This is be used by an object of type Distribution, which will select the algorithm and call the match() function.
 * Currently there's only one matching algorithm, the class will be modified in case the need for a new algorithm arises.
 * 
 * @see Distribution
 * @see StudentAdministration
 */

@Component
public class DistributionAlgorithm implements DistributionAlgorithmInterface {
    
/**
 * @see StudentAdministration
 * @see Student
 * @see Optional
 * @param students Object that should be populated with the students and their preferences.
 * 
 * @return A hash map which maps a Student to a list of Optionals. The Optionals are stored in the desired order of the student; the closer to the start of the list an Optional is, the more the student desires to enroll into the Optional.
 */
    
    @Override
    public Map<Student,List<Optional>> match(StudentAdministration students)    
    {
        Map<Student, List<Optional>> result = new HashMap<>();
        students.orderStudents();
        
        for(Student student : students.studList )
        {
            List<Optional> optionale = null;
            for (Map.Entry<Package, List<Optional>> entry : student.getPreference().getPreference().entrySet() ){
                for (Optional opt : entry.getValue() ) {
                    if (opt.getEnrolledStudents() < opt.getCapacity())
                    {
                        opt.setEnrolledStudents(opt.getEnrolledStudents() + 1);
                        opt.setLastGrade(student.getGrade());
                        optionale.add(opt);
                        break;
                    }
                    else
                    {
                        if (opt.getEnrolledStudents() == opt.getCapacity() && opt.getLastGrade() == student.getGrade())
                        {
                            opt.setEnrolledStudents(opt.getEnrolledStudents() + 1);
                            opt.setCapacity(opt.getCapacity()+1);
                            optionale.add(opt);
                            break;
                        }
                    }
                }
                result.put(student, optionale);
            }
        }
        return result;
    }
}
