package com.github.thushear.springboot.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created by kongming on 2018/3/6.
 */
@RestController
public class IndexController {


    private static final Logger LOGGER = LoggerFactory.getLogger(IndexController.class);


    @Autowired
    private Map mapSource;


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

}
