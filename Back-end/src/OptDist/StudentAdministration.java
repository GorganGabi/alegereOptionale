package OptDist;

import java.util.List;

public abstract class StudentAdministration {
    private List<Student> studList;
    
    public StudentAdministration(List<Student> studList) {
        this.studList = studList;
    }
    
    abstract public void parseExcelFile();

    abstract public List<Student> orderStudents();
    
    abstract public List<Student> checkEligibility(List<Student> studList);
}
