package com.github.thushear.work.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.stereotype.Service;


@Service
public class HystrixService {


    @HystrixCommand(groupKey = "hystrix",fallbackMethod = "defaultHystrix")
    public String getHystrix(){
        String a = null;
        a.length();
        return "sss";
    }

    private String defaultHystrix(){
        return "default";
    }

}
