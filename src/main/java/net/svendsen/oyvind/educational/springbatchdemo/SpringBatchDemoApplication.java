package net.svendsen.oyvind.educational.springbatchdemo;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.util.StopWatch;

import java.util.Map;

@SpringBootApplication
public class SpringBatchDemoApplication {

    public static void main(String[] args) throws Exception{
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        ConfigurableApplicationContext context = SpringApplication.run(SpringBatchDemoApplication.class, args);
        JobLauncher jobLauncher = context.getBean(JobLauncher.class);
        Map<String, Job> jobs = context.getBeansOfType(Job.class);
        System.out.println("JobBeans: " + jobs);

        jobLauncher.run(jobs.get("demoJob"), new JobParametersBuilder().toJobParameters());

        stopWatch.stop();
        System.out.println(stopWatch);
    }

}
