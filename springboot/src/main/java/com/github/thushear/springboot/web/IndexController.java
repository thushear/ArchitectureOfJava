package com.github.thushear.springboot.web;

import com.github.thushear.springboot.event.UserEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by kongming on 2018/3/6.
 */
@RestController
public class IndexController implements ApplicationContextAware{

    private ApplicationContext applicationContext ;

    private static final Logger LOGGER = LoggerFactory.getLogger(IndexController.class);


    @Autowired
    private Map mapSource;


    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @GetMapping("/index")
    public String index(){
        LOGGER.trace("trace");
        LOGGER.debug("debug");
        LOGGER.info("info");
        LOGGER.warn("warn");
        return "hello boot";
    }


    @GetMapping("/map")
    public Map map(){
        return mapSource;
    }


    @GetMapping("/app")
    public String[] app(HttpServletRequest request){
        ApplicationContext parent = applicationContext.getParent();
        System.err.println("parent:" + parent);
        System.err.println("app:" + applicationContext);
        System.err.println("applicationContext.getParentBeanFactory():" + applicationContext.getParentBeanFactory());
        WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
        System.err.println("webapp:" + webApplicationContext);
        System.err.println("webappbean:" + webApplicationContext.getParentBeanFactory());
        ApplicationContext appContext = webApplicationContext.getParent();
        System.err.println("appcontext:" + appContext);
        DispatcherServlet dispatcherServlet = webApplicationContext.getBean(DispatcherServlet.class);
        System.err.println("dis:" + dispatcherServlet);
        Map mapsource = webApplicationContext.getBean("mapSource",Map.class);
        System.err.println("mapsource:" + mapsource);
        List<HandlerMapping> handlerMappings = dispatcherServlet.getHandlerMappings();

        return applicationContext.getBeanDefinitionNames();
    }


    @GetMapping("/event")
    public String  event(){
        applicationEventPublisher.publishEvent(new UserEvent("event","event"));
        return "event";
    }


    @GetMapping("/responseEntity")
    public ResponseEntity testResponseEntity(){

        return new ResponseEntity("responseEntity", HttpStatus.OK);
    }


    @GetMapping("/responseEntityCreated")
    public ResponseEntity testResponseEntityCreated(){

        return new ResponseEntity("responseEntityCreate", HttpStatus.CREATED);
    }


    @GetMapping("/responseEntityBad")
    public ResponseEntity testResponseEntityBad(){

        return new ResponseEntity("responseEntityBad", HttpStatus.BAD_GATEWAY);
    }


    @GetMapping("/responseEntitys")
    public List<ResponseEntity> testResponseEntitys(){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("headerKey","headerValue");
        List<ResponseEntity> responseEntities = new ArrayList<>();
        responseEntities.add(ResponseEntity.ok("ok1"));
        responseEntities.add(new ResponseEntity("body1",httpHeaders,HttpStatus.CREATED));


        return responseEntities;
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
         this.applicationContext = applicationContext;
    }
}
