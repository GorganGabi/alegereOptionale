package ro.uaic.info.optdist.script;

import org.xwiki.component.annotation.Component;
import org.xwiki.script.service.ScriptService;

import ro.uaic.info.optdist.HelloWorld;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;


@Component
@Named("OptDist")
@Singleton
public class OptDistService implements ScriptService
{
    @Inject
    private HelloWorld helloWorld;

    public String test_greet() {
        return this.helloWorld.sayHello();
    }
}
