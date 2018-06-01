package com.github.thushear.system;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class SystemUtil {


    public static void main(String[] args) {

        System.err.println(System.getenv());

        System.err.println(JSON.toJSONString(System.getenv(), SerializerFeature.PrettyFormat));

    }

}
