package com.github.thushear.springboot.conf;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * <pre>

 * Created: 2018年03月09日 下午 18:43
 * Version: 1.0
 * Project Name: architecture
 * Last Edit Time: 2018年03月09日 下午 18:43
 * Update Log:
 * Comment:
 * </pre>
 */
//@Configuration
//@ConditionalOnMissingBean(TaskExecutor.class)
public class TaskExecutorConfiguration {


    @Bean("applicationEventPublisher")
    public ApplicationEventMulticaster simpleApplicationEventMulticaster(){
        SimpleApplicationEventMulticaster applicationEventMulticaster = new SimpleApplicationEventMulticaster();
        applicationEventMulticaster.setTaskExecutor(taskExecutor());
        return applicationEventMulticaster;
    }

    @Bean(name = "taskExecutor")
    public TaskExecutor taskExecutor(){
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(1);
        taskExecutor.setMaxPoolSize(1);
        taskExecutor.setQueueCapacity(2);
        taskExecutor.afterPropertiesSet();
        return taskExecutor;
    }

}
