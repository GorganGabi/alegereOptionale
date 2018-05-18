package ro.uaic.info.optdist.script;

import java.io.IOException;
import java.net.ProtocolException;
import org.xwiki.component.annotation.Component;
import org.xwiki.script.service.ScriptService;

import ro.uaic.info.optdist.HelloWorld;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import ro.uaic.info.optdist.internal.Distribution;
import ro.uaic.info.optdist.internal.DistributionAlgorithm;
import ro.uaic.info.optdist.internal.ExcelParser;
import ro.uaic.info.optdist.internal.Optional;
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
        this.test_student = new Student("192SL00777", "Vasile", "Vasilescu", "V3", 1.9f);
        return test_student.getNrMatricol();
    }
    
    private Optional test_optional;
    
    public String test_optional_creaiton () {
        this.test_optional = new Optional("CS1010101", "Programare Orientata Orizontal", 3, 2);
        return this.test_optional.getName();
    }
    
    
    private StudentAdministration students;
    private PackageAdministration packages;
    private ExcelParser xcParser;
    
    private Distribution distribution;
    private DistributionAlgorithm algorithmDistribution;
    
    public String beginSubmissions () throws Exception {
        String studsExcelPath = "C:\\students.xls";
        String packagesUrl = "";
        students.importStudents(xcParser.parse(studsExcelPath));
        packages.importPackages(packagesUrl);
        return "Succes!";
    }
    
    public String submitForm (String nrMatricol, String... preferences) {
        if (!true) { // TODO verifica data
            return "Proces esuat. Perioada de submit a expirat.";
        }
        
        // TODO convert datele in preferinte
        
        // TODO inserat in BD
        
        return "Formular depus.";
    }
    
    public void distribute () {
        algorithmDistribution = new DistributionAlgorithm();
        distribution = new Distribution(students, algorithmDistribution);
        distribution.start();
    }
    
    public void exportDistribution () {
        // TODO salveaza in BD rezultatul
    }
    
    public void reset () {
        // TODO verifica adminitate
        
        this.students = null;
        this.packages = null;
        this.distribution = null;
    }
}
