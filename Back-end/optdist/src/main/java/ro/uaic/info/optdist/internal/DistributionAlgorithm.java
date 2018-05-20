package ro.uaic.info.optdist.internal;

import java.util.ArrayList;
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
    @Override
    public Map<Student,List<Optional>> match(StudentAdministration students)    
    {
        Map<Student, List<Optional>> result = new HashMap<>();
        students.orderStudents();
        
        for(Student student : students.studList )
        {
            System.out.println(student.getName());
            List<Optional> optionale = new ArrayList<Optional>();
                for (Map.Entry<Package, List<Optional>> entries : student.getPreference().getPreference().entrySet() ) 
                {
                    for(int i=0; i< entries.getKey().getOptionals().size();i++)
                    {
                        //System.out.println(entries.getValue().get(i).name);
                    if (entries.getValue().get(i).getEnrolledStudents() < entries.getValue().get(i).getCapacity())
                    {
                        entries.getValue().get(i).setEnrolledStudents(entries.getValue().get(i).getEnrolledStudents() + 1);
                        entries.getValue().get(i).setLastGrade(student.getGrade());
                        optionale.add(entries.getValue().get(i));
                        System.out.println(entries.getValue().get(i).name);
                        break;
                    }
                    else
                    {
                        if (entries.getValue().get(i).getEnrolledStudents() == entries.getValue().get(i).getCapacity() && entries.getValue().get(i).getLastGrade() == student.getGrade())
                        {
                            entries.getValue().get(i).setEnrolledStudents(entries.getValue().get(i).getEnrolledStudents() + 1);
                            entries.getValue().get(i).setCapacity(entries.getValue().get(i).getCapacity()+1);
                            optionale.add(entries.getValue().get(i));
                            System.out.println(entries.getValue().get(i).name);
                            break;
                        }
                    }
                    }
                }
            result.put(student, optionale);
        }
        return result;
}}