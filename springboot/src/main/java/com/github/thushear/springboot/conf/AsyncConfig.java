package com.github.thushear.springboot.conf;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.Nullable;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * <pre>

 * Created: 2018年03月12日 下午 15:24
 * Version: 1.0
 * Project Name: architecture
 * Last Edit Time: 2018年03月12日 下午 15:24
 * Update Log:
 * Comment:
 * </pre>
 */
@Configuration
@EnableAsync
public class AsyncConfig implements AsyncConfigurer {


    @Nullable
    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(7);
          executor.setMaxPoolSize(42);
          executor.setQueueCapacity(11);
          executor.setThreadNamePrefix("MyExecutor-");
          executor.initialize();
          return executor;
    }

    @Nullable
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new SimpleAsyncUncaughtExceptionHandler();
    }
}
