package com.github.thushear.springboot.spring;

import com.alibaba.fastjson.JSON;
import org.apache.logging.log4j.core.util.JsonUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

/**
 * Created by kongming on 2018/3/7.
 */
@Component
public class SpringBeanPostProcessor implements BeanPostProcessor {


    @Nullable
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.err.println("SpringBeanPostProcessor: postProcessBeforeInitialization" + beanName    +  ":" +  ( bean));
        return null;
    }

    @Nullable
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.err.println("SpringBeanPostProcessor:postProcessAfterInitialization"  + beanName    +  ":" +  ( bean));
        return null;
    }
}
