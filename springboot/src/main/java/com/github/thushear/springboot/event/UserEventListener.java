package com.github.thushear.springboot.event;

import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * <pre>

 * Created: 2018年03月09日 下午 18:29
 * Version: 1.0
 * Project Name: architecture
 * Last Edit Time: 2018年03月09日 下午 18:29
 * Update Log:
 * Comment:
 * </pre>
 */
@Component
public class UserEventListener implements ApplicationListener<UserEvent>{

    @Async
    @Override
    public void onApplicationEvent(UserEvent event) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.err.println(event.getName());
    }
}
