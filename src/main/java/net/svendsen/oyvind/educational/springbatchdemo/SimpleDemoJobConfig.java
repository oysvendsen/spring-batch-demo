package net.svendsen.oyvind.educational.springbatchdemo;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableBatchProcessing
public class SimpleDemoJobConfig {

    private JobBuilderFactory  jobBuilderFactory;
    private StepBuilderFactory stepBuilderFactory;
    private List<String> strings = Collections.synchronizedList(new ArrayList<>(Arrays.asList("OEyvind", "George", "Shayan", "Marie", "Johannes", "Elizabeth", "Yurii")));

    public SimpleDemoJobConfig(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
    }

    @Bean
    public Job demoJob(Step step1, Step step2) {
        return jobBuilderFactory.get("simpleDemoJob")
                .start(step1)
                .next(step2)
                    .on("FAILED")
                    .to(step1)
                .from(step2)
                    .on("REPEAT")
                    .to(step2)
                    .end()
                .build();
    }

    @Bean
    public Step step1(Tasklet tasklet) {
        return stepBuilderFactory.get("demoTaskletStep")
                .tasklet(tasklet)
                .build();
    }

    @Bean
    public Step step2(ItemReader<String> reader,
            ItemProcessor<String,String> processor,
            ItemWriter<String> writer) {
        return stepBuilderFactory.get("demoChunkStep")
                .<String,String>chunk(2)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }
}
