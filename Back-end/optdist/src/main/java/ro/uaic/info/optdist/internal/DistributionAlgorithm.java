package ro.uaic.info.optdist.internal;

import ro.uaic.info.optdist.*;

import org.xwiki.component.annotation.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class DistributionAlgorithm implements DistributionAlgorithmInterface {
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
