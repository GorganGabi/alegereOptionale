package ro.uaic.info.optdist.script;

import java.util.List;
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

/**
 * The class that implements the project's logic and exposes an API
 * to the xWiki scripting engine.
 * 
 * Functionalities exposed are: starting the submission period, accepting a
 * submitted form, starting the student/optional distribution algorithm,
 * exporting the distribution results and resetting all data so that
 * the process can be restarted.
 */
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
    
    private final String packagesUrl = "https://www.info.uaic.ro/bin/Programs/Undergraduate";
    private final String studsExcelPath = "C:\\students.xls";
    
    public String test_optional_parsing () throws Exception {
        packages = new PackageAdministration();
        packages.importPackages(packagesUrl);
        return packages.getPackageList().get(1).getID();
    }
    
    public String test_args (String arg1) {
        return arg1;
    }
    
    /**
     * Starts accepting preference forms from students.
     * <p>
     * First, this method initialises <code>this.students</code> and <code>this.packages</code>.
     * Second, it imports into the students from the path in <code>this.studsExcelPath</code>,
     * and imports the packages into <code>this.packages</code> from the URL at <code>this.packagesUrl</code>.
     * 
     * At this point, the server is ready to accept forms from the student.
     * 
     * @return "Succes!" on success or "Esec!" on failure
     * @throws Exception 
     */
    public String beginSubmissions () throws Exception {
        students = new StudentAdministration();
        packages = new PackageAdministration();
        
        students.importStudents(xcParser.parse(studsExcelPath));
        // TODO add try catch here:
        packages.importPackages(packagesUrl);
        return "Succes!";
    }
    
    public List<ro.uaic.info.optdist.internal.Package> getPackageList () {
        return this.packages.getPackageList();
    }
    
    /**
     * Validates a form containing the preferences of a student then
     * exports it to the database for the distribution process.
     * 
     * @param nrMatricol registration number of the student that submitted
     * the form
     * @param preferences an array of optional IDs ordered by the student's
     * preference
     * @return status message
     */
    public String submitForm (String nrMatricol, String... preferences) {
        if (!true) { // TODO verifica data
            return "Proces esuat. Perioada de submit a expirat.";
        }
        
        // TODO convert datele in preferinte
        
        // TODO inserat in BD
        
        return "Formular depus.";
    }
    
    /**
     * Starts the distribution algorithm with the data in <code>this.students</code>
     * and <code>this.packages</code>.
     */
    public void distribute () {
        algorithmDistribution = new DistributionAlgorithm();
        distribution = new Distribution(students, algorithmDistribution);
        distribution.start();
    }
    
    /**
     * Exports the distribution results in the predetermined format.
     */
    public void exportDistribution () {
        // TODO salveaza in BD rezultatul
    }
    
    /**
     * Resets the data in <code>this.students</code> and <code>this.packages</code>
     * so that the submitting process can be restarted.
     */
    public void reset () {
        // TODO verifica adminitate
        
        this.students = null;
        this.packages = null;
        this.distribution = null;
    }
}
