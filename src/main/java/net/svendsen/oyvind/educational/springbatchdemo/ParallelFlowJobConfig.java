package net.svendsen.oyvind.educational.springbatchdemo;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.job.flow.support.SimpleFlow;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.SyncTaskExecutor;

@Configuration
@EnableBatchProcessing
public class ParallelFlowJobConfig {

    @Autowired
    private JobBuilderFactory  jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;

//    @Bean
    public Job parallelFlow(Flow splitFlow) {
        return jobBuilderFactory.get("parallelFlow")
                .start(splitFlow)
                .next(step(6)).build()
                .build();
    }

    @Bean
    public Flow splitFlow() {
        return new FlowBuilder<SimpleFlow>("splitFlow")
                .split(new SyncTaskExecutor())
                .add(flow1(), flow2()) // contains steps [1,2,3] and [4,5]
                .build();
    }

    private Flow flow2() {
        return new FlowBuilder<SimpleFlow>("flow2")
                    .start(step(4))
                    .next(step(5))
                    .build();
    }

    private Flow flow1() {
        return new FlowBuilder<SimpleFlow>("flow1")
                    .start(step(1))
                    .next(step(2))
                    .next(step(3))
                    .build();
    }

    private Step step(int i) {
        return stepBuilderFactory.get("step" + i)
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("Executing step " + i);
                    return RepeatStatus.FINISHED;
                }).build();
    }

}
