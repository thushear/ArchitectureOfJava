package com.github.thushear.springboot.spring;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.Ordered;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * <pre>
 * Created: 2018年03月09日 下午 14:33
 * Version: 1.0
 * Project Name: architecture
 * Last Edit Time: 2018年03月09日 下午 14:33
 * Update Log:
 * Comment:
 * </pre>
 */
//@Component
public class SpringApplicationListenr implements ApplicationListener<ApplicationEvent>,Ordered {



    @Async
    @Override
    public void onApplicationEvent(ApplicationEvent event) {

        System.err.println("event:" + event.getTimestamp() + event.getSource() + ":"  + event);

    }


    @Override
    public int getOrder() {
        return 1;
    }
}
