package com.github.thushear.springboot.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Created by kongming on 2018/3/7.
 */
@Component
public class SpringBeanFactoryPostProcessor implements BeanFactoryPostProcessor {


    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.err.println("SpringBeanFactoryPostProcessor:getBeanDefinitionNames  " + Arrays.toString(beanFactory.getBeanDefinitionNames()) );
        System.err.println("SpringBeanFactoryPostProcessor:getSingletonNames  " + Arrays.toString( beanFactory.getSingletonNames()));

    }
}
