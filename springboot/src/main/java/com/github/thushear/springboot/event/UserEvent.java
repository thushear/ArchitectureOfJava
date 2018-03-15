package com.github.thushear.springboot.event;

import lombok.Data;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

import java.util.EventObject;

/**
 * <pre>
 * Created: 2018年03月09日 下午 18:26
 * Version: 1.0
 * Project Name: architecture
 * Last Edit Time: 2018年03月09日 下午 18:26
 * Update Log:
 * Comment:
 * </pre>
 */
@Data
public class UserEvent extends ApplicationEvent {

     private String name;




    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public UserEvent(Object source) {
        super(source);
    }


    public UserEvent(Object source,String name) {
        super(source);
        this.name = name;
    }


}
