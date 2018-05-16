package ro.uaic.info.optdist.script;

import org.xwiki.component.annotation.Component;
import org.xwiki.script.service.ScriptService;

import ro.uaic.info.optdist.HelloWorld;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import ro.uaic.info.optdist.internal.Distribution;
import ro.uaic.info.optdist.internal.DistributionAlgorithm;
import ro.uaic.info.optdist.internal.ExcelParser;
import ro.uaic.info.optdist.internal.PackageAdministration;
import ro.uaic.info.optdist.internal.Student;
import ro.uaic.info.optdist.internal.StudentAdministration;


@Component
@Named("OptDist")
@Singleton
public class OptDistService implements ScriptService {
    @Inject
    private HelloWorld helloWorld;

    public String test_greet() {
        return this.helloWorld.sayHello();
    }
    
    private Student test_student;
    
    public String test_student_creation () {
        this.test_student = new Student("192SL00777", "fernado", "armandoghedon", "a3", 1.9f);
        return test_student.getNrMatricol();
    }
    
    private StudentAdministration students;
    private PackageAdministration packages;
    private ExcelParser xcParser;
    
    private Distribution distribution;
    private DistributionAlgorithm algorithmDistribution;
    
    private boolean acceptSubmissions = false;
    
    public String beginSubmissions () {
        String studsExcelPath = "C:\\students.xls";
        String packagesUrl = "";
        students.importStudents(xcParser.parse(studsExcelPath));
        packages.importPackages(packagesUrl);
        acceptSubmissions = true;
        return "Succes!";
    }
    
    public String submitForm (String nrMatricol, String... preferences) {
        if (!acceptSubmissions) {
            return "Proces esuat. Perioada de submit a expirat.";
        }
        
        // TODO convert datele in preferinte
        
        // TODO inserat in BD
        
        return "Formular depus.";
    }
    
    public void distribute () {
        algorithmDistribution = new DistributionAlgorithm();
        distribution = new Distribution(students);
        
        
    }
}
