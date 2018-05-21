package ro.uaic.info.optdist.script;

import java.util.ArrayList;
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
    private Optional test_optional;
    
    private StudentAdministration students;
    private PackageAdministration packages;
    private ExcelParser xcParser;
    
    private Distribution distribution;
    private DistributionAlgorithm algorithmDistribution;
    
    private String packagesUrl = "https://www.info.uaic.ro/bin/Programs/Undergraduate";
    private String studsExcelPath = "C:\\students.xls";
    
    private boolean hasInit = false;
    
    public String test_student_creation () {
        this.test_student = new Student("192SL00777", "Vasile", "Vasilescu", "V3", 1.9f);
        return test_student.getNrMatricol();
    }
    
    public String test_student_administration () {
        Student tmpStudent = new Student("192SL00777", "Vasile", "Vasilescu", "V3", 1.9f);
        tmpStudent.setYear(2);
        this.students.addStudent(tmpStudent);
        
        Student tmpStudent1 = new Student("192SL00778", "Ion", "Ionescu", "V3", 1.9f);
        tmpStudent1.setYear(2);
        this.students.addStudent(tmpStudent1);
        
        return "Succes";
    }
    
    public String test_optional_creaiton () {
        this.test_optional = new Optional("CS1010101", "Programare Orientata Orizontal", 3, 2);
        return this.test_optional.getName();
    }
    
    public String test_optional_parsing () throws Exception {
        packages = new PackageAdministration();
        packages.importPackages(packagesUrl);
        return packages.getPackageList().get(1).getID();
    }
    
    public String test_args (String arg1) {
        return arg1;
    }

    /*
    private StudentAdministration students;
    private PackageAdministration packages;
    private ExcelParser xcParser;
    
    private Distribution distribution;
    private DistributionAlgorithm algorithmDistribution;
    
    private String packagesUrl = "https://www.info.uaic.ro/bin/Programs/Undergraduate";
    private String studsExcelPath = "C:\\students.xls";
    */

    
    /**
     * Initializes internal objects with empty ones.
     * 
     */
    public void init () {
        students = new StudentAdministration();
        packages = new PackageAdministration();
        xcParser = new ExcelParser();
        
        algorithmDistribution = new DistributionAlgorithm();
        distribution = new Distribution(students, algorithmDistribution);
        
        hasInit = true;
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
     * @return "Succes!" on success or "Esec:" and then the exception message on failure
     * @throws Exception 
     */
    public String beginSubmissions () throws Exception {
        students.importStudents(xcParser.parse(studsExcelPath));
        
        try {
            packages.importPackages(packagesUrl);
        } catch (Exception e) {
            return "Esec: " + e.getMessage();
        }
        
        return "Succes!";
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
        distribution.start();
    }
    
    /**
     * Exports the distribution results in the predetermined format.
     */
    public void exportDistribution () {
        // TODO salveaza in BD rezultatul(?)
    }


    /**
     * Nullifies all internal data objects except for the student excel path
     * and the packages' URL.
     */
    public void stop () {
        // TODO verifica adminitate
        
        this.students = null;
        this.packages = null;
        this.distribution = null;
        this.algorithmDistribution = null;
        this.xcParser = null;
        hasInit = false;
    }
    
    
    /**
     * Creates empty <code>List&lt;Student&gt;</code> object.
     * 
     * @return the empty <code>List&lt;Student&gt;</code> created
     */
    public List<Student> createStudentList () {
        return new ArrayList<>();
    }
    
    /**
     * Creates empty <code>List&lt;Optional&gt;</code> object.
     * 
     * @return the empty <code>List&lt;Optional&gt;</code> created
     */
    public List<Optional> createOptionalList () {
        return new ArrayList<>();
    }
    
    /**
     * Creates empty <code>List&lt;Package&gt;</code> object.
     * 
     * @return the empty <code>List&lt;Package&gt;</code> created
     */
    public List<Package> createPackageList () {
        return new ArrayList<>();
    }

    /**
     * Creates empty <code>Package</code> object.
     * 
     * @return the empty <code>Package</code> created
     */
    public ro.uaic.info.optdist.internal.Package createPackage (List<Optional> optionals, int year, int semester, String ID) {
        return new ro.uaic.info.optdist.internal.Package(optionals, year, semester, ID);
    }
    
    /**
     * Creates empty <code>Optional</code> object.
     * 
     * @return the empty <code>Optional</code> created
     */
    public Optional createOptional (String ID, String name, int year, int semester) {
        return new Optional (ID, name, year, semester);
    }
    
    
    
    public void setPackagesUrl (String newUrl) {
        this.packagesUrl = newUrl;
    }
    
    public String getPackagesUrl () {
        return this.packagesUrl;
    }
    
    public void setStudentExcelPath (String newPath) {
        this.studsExcelPath = newPath;
    }
    
    public String getStudentExcelPath () {
        return this.studsExcelPath;
    }
    
    public PackageAdministration getPackages () {
        return this.packages;
    }
    
    public StudentAdministration getStudents () {
        return this.students;
    }
}
