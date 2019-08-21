package net.svendsen.oyvind.educational.springbatchdemo;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

@Configuration
@EnableBatchProcessing
public class MultiThreadStepJobConfig {

    @Autowired
    private JobBuilderFactory  jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;

//    @Bean
    public Job multiThreadJob(Step multiThreadChunkStep) {
        return jobBuilderFactory.get("parallelFlow")
                .start(multiThreadChunkStep)
                .build();
    }

    @Bean
    public Step multiThreadChunkStep(ItemReader<String> reader, ItemWriter<String> writer) {
        return stepBuilderFactory.get("demoChunkStep")
                .<String,String>chunk(2)
                .reader(reader)
                .writer(writer)
                .taskExecutor(new SimpleAsyncTaskExecutor())
                .build();
    }
}
