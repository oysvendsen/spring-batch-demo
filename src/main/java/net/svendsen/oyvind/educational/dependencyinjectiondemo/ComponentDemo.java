package net.svendsen.oyvind.educational.dependencyinjectiondemo;

import org.springframework.stereotype.Component;
import javax.inject.Inject;

@Component
public class ComponentDemo {

    @Inject
    private Object field;

    @Inject
    public ComponentDemo(Object instance) {
        this.field = instance;
    }

    @Inject
    public void setField(Object instance) {
        this.field = instance;
    }
}
