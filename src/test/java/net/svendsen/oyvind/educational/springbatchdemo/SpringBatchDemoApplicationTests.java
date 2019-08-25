package net.svendsen.oyvind.educational.springbatchdemo;

//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;

import io.cucumber.java8.En;
import org.springframework.boot.SpringApplication;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class SpringBatchDemoApplicationTests {

//    @Test
    public void contextLoads() {
    }
    public class StepDefs implements En {

        public StepDefs() {

            When("job is started", () -> {
                SpringApplication.run(SpringBatchDemoApplication.class);
            });

        }
    }
}
