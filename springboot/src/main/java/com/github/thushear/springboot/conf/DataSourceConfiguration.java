package com.github.thushear.springboot.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kongming on 2018/3/7.
 */
@Configuration
public class DataSourceConfiguration {

//    private final MapConfProp mapConfProp;
//
//    public DataSourceConfiguration(MapConfProp mapConfProp){
//        this.mapConfProp = mapConfProp;
//    }


    @Bean("mapSource")
    public  Map  getDataSource(@Autowired MapConfProp mapConfProp){
        Map source = new HashMap();
        source.put(mapConfProp.getKey(),mapConfProp.getValue());
        return source;
    }



}
