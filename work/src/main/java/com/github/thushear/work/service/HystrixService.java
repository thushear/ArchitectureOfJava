package com.github.thushear.work.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.stereotype.Service;

/**
 * <pre>
 * Copyright: www.jd.com
 * Author: kongming@jd.com
 * Created: 2018年05月31日 下午 15:06
 * Version: 1.0
 * Project Name: saas-pbs-framework
 * Last Edit Time: 2018年05月31日 下午 15:06
 * Update Log:
 * Comment:
 * </pre>
 */
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
