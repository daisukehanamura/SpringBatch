package com.javacodingskills.spring.batch.demo0.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@EnableBatchProcessing
public class BaseConfig {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private Tasklet HelloTasklet;

    @Bean
    public Step taskletStep1(){
        return stepBuilderFactory.get("HelloTaskletStep1")
                .tasklet(HelloTasklet).build();
    }

    @Bean
    public Job taskletJob() throws Exception{
        return jobBuilderFactory.get("HelloTaskletJob")
                .incrementer(new RunIdIncrementer()).start(taskletStep1())
                .build();
    }

}
