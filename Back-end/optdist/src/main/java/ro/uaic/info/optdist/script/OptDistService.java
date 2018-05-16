package ro.uaic.info.optdist.script;

import org.xwiki.component.annotation.Component;
import org.xwiki.script.service.ScriptService;

import ro.uaic.info.optdist.HelloWorld;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import ro.uaic.info.optdist.internal.Student;


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
    
    
    
    public void beginSubmissions () {
        
    }
    
    public void submitForm (String nrMatricol) {
        
    }
}
