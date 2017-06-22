package com.github.thushear.micro.restful;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by kongming on 2017/6/22.
 */
@RestController
public class RestApiController {


    @RequestMapping(value = "/echo",method = RequestMethod.GET)
    public String echo(){
        return "echo";
    }



}
