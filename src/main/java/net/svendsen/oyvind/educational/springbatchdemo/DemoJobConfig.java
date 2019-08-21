package net.svendsen.oyvind.educational.springbatchdemo;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableBatchProcessing
public class DemoJobConfig {

    @Autowired
    private JobBuilderFactory  jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    private List<String> strings = Collections.synchronizedList(new ArrayList<>(Arrays.asList("OEyvind", "George", "Shayan", "Marie", "Johannes", "Elizabeth", "Yurii")));

    //    @Bean
    public Job demoJob(Step demoTaskletStep, Step demoChunkStep, Step cleanupStep, JobExecutionDecider doRepeatDecider) {
        return jobBuilderFactory.get("demoJob")
                .incrementer(new RunIdIncrementer())
                .start(demoTaskletStep)
                .next(demoChunkStep).on("FAILED").to(cleanupStep)
                .from(demoChunkStep).on("*").to(doRepeatDecider)
                    .on("FAILED").fail()
                    .on("COMPLETED").end()
                    .on("STOP AND CHECK").stop().build()
                .build();
    }

    @Bean
    public Step demoTaskletStep() {
        return stepBuilderFactory.get("demoTaskletStep")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("executing tasklet");
                    Thread.sleep(1000);
                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    public Step demoChunkStep(ItemReader<String> reader, ItemWriter<String> writer) {
        return stepBuilderFactory.get("demoChunkStep")
                .<String,String>chunk(2)
                .reader(reader)
                .writer(writer)
                .build();
    }

    @Bean
    public Step cleanupStep() {
        return stepBuilderFactory.get("cleanupStep")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("Cleaning up after failure");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    public JobExecutionDecider doRepeatDecider() {
        return (jobExecution, stepExecution) -> {
            System.out.println("DECIDING");
            return new FlowExecutionStatus("STOP AND CHECK");
        };
    }

    @Bean
    public ItemReader<String> reader() {
        return () -> {
            if (strings.isEmpty()) {
                return null;
            } else {
                String remove = strings.remove(0);
                System.out.println("reading " + remove);
                return remove;
            }
        };
    }

    @Bean
    public ItemWriter<String> writer() {
        return items -> System.out.println("Writing '" + items + "' to commandline");
    }

}
