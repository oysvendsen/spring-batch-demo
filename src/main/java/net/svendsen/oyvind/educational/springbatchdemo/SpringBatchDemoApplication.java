package net.svendsen.oyvind.educational.springbatchdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.util.StopWatch;

@SpringBootApplication
public class SpringBatchDemoApplication {

    public static void main(String[] args) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        ConfigurableApplicationContext context = SpringApplication.run(SpringBatchDemoApplication.class, args);
        stopWatch.stop();
        System.out.println(stopWatch);
    }

}
