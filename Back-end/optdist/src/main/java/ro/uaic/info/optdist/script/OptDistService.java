package ro.uaic.info.optdist.script;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.xwiki.component.annotation.Component;
import org.xwiki.script.service.ScriptService;

import ro.uaic.info.optdist.HelloWorld;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import ro.uaic.info.optdist.internal.Distribution;
import ro.uaic.info.optdist.internal.DistributionAlgorithm;
import ro.uaic.info.optdist.internal.ExcelParser;
import ro.uaic.info.optdist.internal.FormInfoAdministration;
import ro.uaic.info.optdist.internal.FormResponse;
import ro.uaic.info.optdist.internal.Optional;
import ro.uaic.info.optdist.internal.PackageAdministration;
import ro.uaic.info.optdist.internal.Preference;
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
    
    private FormInfoAdministration forms;
    
    private Distribution distribution;
    private DistributionAlgorithm algorithmDistribution;
    
    private String packagesUrl = "https://www.info.uaic.ro/bin/Programs/Undergraduate";
    private String studsExcelPath = "C:\\students.xls";
    private String exportPath = "C:\\exportStudents.xlsx";
    
    private boolean hasInit = false;
    private boolean flagSync = false;
    private boolean flagGetPrefs = false;
    
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
    
    public String test_list_get (List<String> list, int index) {
        return list.get(index);
    }
    
    private final long dayMillis = 86400000;
    private final long weekMillis = 604800000;
    
    /**
     * Initialisation method that has to be called before the OptDist
     * is able to operate.
     * 
     * Specifically, <code>init()</code> initialises all internal objects
     * with empty ones, sets the time to live of the forms to 1 week after 
     * the current time and sets to true an internal flag, memorising that
     * the initialisation has been done.
     * 
     */
    public void init () {
        students = new StudentAdministration();
        packages = new PackageAdministration();
        xcParser = new ExcelParser();
        
        Calendar expiration = Calendar.getInstance();
        // 604800000 ms == 1 week
        expiration.setTime(new Date(System.currentTimeMillis() + weekMillis));
        forms = new FormInfoAdministration(expiration);
        
        algorithmDistribution = new DistributionAlgorithm();
        distribution = new Distribution(students, algorithmDistribution);
        
        buffer = new HashMap<>();
        
        hasInit = true;
        flagSync = false;
        flagGetPrefs = false;
    }
    
    /**
     * Set the time to live to <code>days</code> days from the current time.
     * 
     * @param days the number of days from the current time to set the form to expire
     */
    public void setTTL (int days) {
        this.forms.getTTL().setTime(new Date(System.currentTimeMillis() + days * dayMillis));
    }
    
    /**
     * Imports the students from the the internal student excel 
     * path, <code>this.studsExcelPath</code>.
     * <p>
     * Before importing, it is necessary to initialise the server <code>init()</code>.
     * 
     * @return a string containing the status message
     * @throws java.lang.Exception
     * @see init
     */
    public String importStudents () throws Exception {
        if (!hasInit) {
            return "Esec: serverul nu a fost initializat!";
        }
        
        try {
            students.importStudents(xcParser.parse(studsExcelPath));
        } catch (Exception e) {
            return "Esec: " + e.getMessage();
        }
        
        return "Succes!";
    }
    
    /**
     * Imports the packages from the the internal package url,
     * <code>this.packagesUrl</code>.
     * <p>
     * Before importing, it is necessary to initialise the server <code>init()</code>.
     * 
     * @return a string containing the status message
     * @throws java.lang.Exception
     * @see init
     */
    public String importPackages () throws Exception {
        if (!hasInit) {
            return "Esec: serverul nu a fost initializat!";
        }
        
        try {
            packages.importPackages(packagesUrl);
        } catch (Exception e) {
            return "Esec: " + e.getMessage();
        }
        
        return "Succes!";
    }
    
    /**
     * Validates a form containing the preferences of a student then
     * memorises it for the distribution process.
     * <p>
     * For each element in <code>packageIDs</code>, there must be 
     * a list in the <code>optionalIDs</code> array. In essence, for each package
     * received in <code>packageIDs</code> there is a <code>List</code> of optionals
     * ordered, descending, by the student's submitted preferences.
     * <p>
     * The actual contents of <code>packageIDs</code> are the IDs 
     * (type <code>String</code> of the corresponding package. The same goes for
     * <code>optionalIDs</code> though note that <code>optionalIDs</code> is
     * an array of lists and so each element of <code>optionalIDs</code> is a
     * list of <code>Strings</code> that contain the IDs of the respective 
     * <code>Optional</code>s.
     * <p>
     * This method is called whenever a student submits a form.
     * 
     * 
     * @param nrMatricol the registration number of the student that submitted
     * @param packageIDs the list of package IDs that the student has chosen 
     * optional preferences for
     * @param optionalIDs the array of lists of optional IDs that, by their order,
     * represent the student's preference of optionals in each package for which
     * there is an entry in <code>packageIDs</code>
     * @return status message
     */
    public String submitForm (String nrMatricol, List<String> packageIDs, List<String>... optionalIDs) {
        if (Calendar.getInstance().after(forms.getTTL())) { // TODO verifica data
            return "Esec! Perioada de submit a expirat.";
        }
        
        if (packageIDs.size() != optionalIDs.length) {
            throw new IllegalArgumentException("Esec! packageIDs si optionalIDs nu sunt de aceeasi lungime!");
        }
        
        
        Student submitterStudent = this.students.getStudentByReg(nrMatricol);
        
        FormResponse submitterForm = new FormResponse();
        submitterForm.setNrMatricol(nrMatricol);
        
        Map<ro.uaic.info.optdist.internal.Package, List<Optional>> submitterPrefs = new HashMap<>();
        
        ro.uaic.info.optdist.internal.Package currentPackage = null;
        List<Optional> currentOptionalList = null;
        Optional currentOptional = null;
        
        for (int iterPackage = 0; iterPackage < packageIDs.size(); iterPackage++) {
            currentPackage = packages.getPackageByID(packageIDs.get(iterPackage));
            
            currentOptionalList = new ArrayList<>();
            for (int iterOptionals = 0; iterOptionals < optionalIDs.length; iterOptionals++) {
                currentOptional = currentPackage.getOptionalByID(optionalIDs[iterPackage].get(iterOptionals));
                
                currentOptionalList.add(currentOptional);
            }
            
            submitterPrefs.put(currentPackage, currentOptionalList);
        }
        
        submitterForm.setPrefs(submitterPrefs);
        
        Preference submitterPreference = new Preference(submitterForm);
        
        submitterStudent.setPreference(submitterPreference);
        
        // TODO inserat in BD(?)
        
        return "Succes!";
    }
    
    /**
     * Validates a form containing the preferences of a student then
     * memorises it for the distribution process.
     * <p>
     * For each element in <code>packageIDs</code>, there must be 
     * a list in the <code>optionalIDs</code> array. In essence, for each package
     * received in <code>packageIDs</code> there is a <code>List</code> of optionals
     * ordered, descending, by the student's submitted preferences.
     * <p>
     * The actual contents of <code>packageIDs</code> are the IDs 
     * (type <code>String</code> of the corresponding package. The same goes for
     * <code>optionalIDs</code> though note that <code>optionalIDs</code> is
     * an array of lists and so each element of <code>optionalIDs</code> is a
     * list of <code>Strings</code> that contain the IDs of the respective 
     * <code>Optional</code>s.
     * <p>
     * This method is called whenever a student submits a form.
     * 
     * 
     * @param nrMatricol the registration number of the student that submitted
     * @param packageIDs the list of package IDs that the student has chosen 
     * optional preferences for
     * @param optionalIDs the array of lists of optional IDs that, by their order,
     * represent the student's preference of optionals in each package for which
     * there is an entry in <code>packageIDs</code>
     * @return status message
     */
    public String submitForm (String nrMatricol, List<String> packageIDs, List<List<String>> optionalIDs) {
        if (Calendar.getInstance().after(forms.getTTL())) { // TODO verifica data
            return "Esec! Perioada de submit a expirat.";
        }
        
        if (packageIDs.size() != optionalIDs.size()) {
            throw new IllegalArgumentException("Esec! packageIDs si optionalIDs nu sunt de aceeasi lungime!");
        }
        
        
        Student submitterStudent = this.students.getStudentByReg(nrMatricol);
        
        FormResponse submitterForm = new FormResponse();
        submitterForm.setNrMatricol(nrMatricol);
        
        Map<ro.uaic.info.optdist.internal.Package, List<Optional>> submitterPrefs = new HashMap<>();
        
        ro.uaic.info.optdist.internal.Package currentPackage = null;
        List<Optional> currentOptionalList = null;
        Optional currentOptional = null;
        
        for (int iterPackage = 0; iterPackage < packageIDs.size(); iterPackage++) {
            currentPackage = packages.getPackageByID(packageIDs.get(iterPackage));
            
            currentOptionalList = new ArrayList<>();
            for (int iterOptionals = 0; iterOptionals < optionalIDs.size(); iterOptionals++) {
                currentOptional = currentPackage.getOptionalByID(optionalIDs.get(iterPackage).get(iterOptionals));
                
                currentOptionalList.add(currentOptional);
            }
            
            submitterPrefs.put(currentPackage, currentOptionalList);
        }
        
        submitterForm.setPrefs(submitterPrefs);
        
        Preference submitterPreference = new Preference(submitterForm);
        
        submitterStudent.setPreference(submitterPreference);
        
        // TODO inserat in BD(?)
        
        return "Succes!";
    }
    
    HashMap<String, HashMap<String, ArrayList<String>>> buffer;
    /**
     * Memorizes a single optional (part of the student's whole preference)
     * in an internal buffer.
     * 
     * The given data isn't actually part of the internal data used for the distribution.
     * To actually use the data in the buffer, <code>flushPreferences()</code>
     * must be called.
     * 
     * @param nrMatricol registration number of the student whose preference is passed
     * @param packageID the package id of the passed optional
     * @param optionalID the passed optional's id
     * @param priority the priority of the optional in the student's preference (0 being highest)
     * @return the status message
     */
    public String submitSinglePreference (String nrMatricol, String packageID, String optionalID, int priority) {
        // TODO check if synced optionals
        
        String status = "";
        
        //get student with nrMatricol
        Student currentStudent = students.getStudentByReg(nrMatricol);
        if (currentStudent == null) {
            status += "Esec: studentul nu a fost gasit";
            return status;
        }
        
        ro.uaic.info.optdist.internal.Package currentPackage = packages.getPackageByID(packageID);
        if (currentPackage == null) {
            status += "Esec: pachetul nu a fost gasit";
            return status;
        }
        
        Optional currentOptional = currentPackage.getOptionalByID(optionalID);
        if (currentOptional == null) {
            status += "Esec: optionalul nu a fost gasit";
            return status;
        }
        
        
        if (buffer.get(nrMatricol) == null) {
            buffer.put(nrMatricol, new HashMap<>());
        }
        
        if (buffer.get(nrMatricol).get(packageID) == null) {
            ArrayList<String> emptyOptList = new ArrayList<>();
            
            for (int i = 0; i < currentPackage.getOptionals().size(); i++) {
                emptyOptList.add("");
            }
            
            buffer.get(nrMatricol).put(packageID, emptyOptList);
        }
        
        if (!buffer.get(nrMatricol).get(packageID).get(priority).equals("")) {
            status += "O preferinta a fost suprascrisa!\n";
        }
        
        buffer.get(nrMatricol).get(packageID).set(priority, optionalID);

        return status + "Succes!";
    }
    
    /**
     * Wrapper to the function of the same name that accepts 
     * the priority as a String.
     * 
     * @param nrMatricol registration number of the student whose preference is passed
     * @param packageID the package id of the passed optional
     * @param optionalID the passed optional's id
     * @param priority the priority of the optional in the student's preference (0 being highest)
     * @return the status message
     */
    public String submitSinglePreference (String nrMatricol, String packageID, String optionalID, String priority) {
        return submitSinglePreference (nrMatricol, packageID, optionalID, Integer.parseInt(priority));
    }
    
    /**
     * Flushes the internal preference buffer 
     * 
     * @return status messages
     */
    public String flushPreferences () {
        for (String nrMat : buffer.keySet()) {
            List<String> packList = new ArrayList<>();
            List<List<String>> optList = new ArrayList<>();
            
            for (String packID : buffer.get(nrMat).keySet()) {
                packList.add(packID);
                optList.add(buffer.get(nrMat).get(packID));
            }
            
            submitForm(nrMat, packList, optList);
        }
        
        return "Succes!";
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
    public void exportDistribution () throws IOException {
        Map<Optional, List<Student>> optionalDistribution;
        List<Student> optionalStudents;
        List<Optional> optionalsChosen;
        Workbook workbook;
        FileOutputStream fileOut;
        
        optionalDistribution = new HashMap();
        
        for(ro.uaic.info.optdist.internal.Package pack : packages.getPackageList())
            for(Optional opt : pack.getOptionals())
            {
                optionalStudents = new ArrayList();
                optionalDistribution.put(opt, optionalStudents);
            }
        
        for(Student student : distribution.getResult().keySet())
        {
            optionalsChosen = distribution.getResult().get(student);
            for(Optional optional : optionalsChosen)
                optionalDistribution.get(optional).add(student);
        }
        
        workbook = new XSSFWorkbook();
        
        for(Optional optional : optionalDistribution.keySet())
        {
            Sheet sheet;
            if(optional.getName().contains(":"))
                sheet = workbook.createSheet(optional.getName().split(":")[0]);
            else
                sheet = workbook.createSheet(optional.getName());
            int rowNum = 0;
            for(Student student : optionalDistribution.get(optional))
            {
                Row row = sheet.createRow(rowNum);
                row.createCell(0).setCellValue(student.getName());
                row.createCell(1).setCellValue(student.getSurname());
            }
        }
        
        try 
        {
            fileOut = new FileOutputStream(exportPath);
            workbook.write(fileOut);
            fileOut.close();
        }
        catch(FileNotFoundException e)
        {
            System.out.println("Fisierul xlsx nu a fost gasit!");
        } catch (IOException ex) {
            System.out.println("I/O Exception");
        }
    }


    /**
     * Nullifies all internal data objects except for the student excel path
     * and the packages' URL.
     */
    public void stop () {
        this.students = null;
        this.packages = null;
        this.distribution = null;
        this.algorithmDistribution = null;
        this.xcParser = null;
        hasInit = false;
        flagSync = false;
        flagGetPrefs = false;
    }

    /**
     * Creates <code>Package</code> object and adds it to the internal list.
     * 
     * @param year the new package's year
     * @param semester the new package's semester
     * @param ID the new package's ID
     * @return the empty <code>Package</code> created
     */
    public boolean createPackage (int year, int semester, String ID) {
        return this.packages.getPackageList().add(new ro.uaic.info.optdist.internal.Package(year, semester, ID));
    }
    
    /**
     * Creates empty <code>Optional</code> object and adds it to the package
     * with <code>packageID</code> as ID.
     * 
     * @param packageID the id of the package to 
     * @param ID the new optional's ID
     * @param name the new optional's name
     * @param year the new optional's year
     * @param semester the new optional's semester
     * @return result of operation
     */
    public boolean createOptional (String packageID, String ID, String name, int year, int semester) {
        return this.packages.getPackageByID(packageID).getOptionals().add(new Optional (ID, name, year, semester));
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
    
    public void setExportExcelPath (String newPath) {
        this.exportPath = newPath;
    }
    
    public String getExportExcelPath () {
        return this.exportPath;
    }
    
    
    public PackageAdministration getPackages () {
        return this.packages;
    }
    
    public StudentAdministration getStudents () {
        return this.students;
    }
    
    public Distribution getDistribution () {
        return this.distribution;
    }
}
