package com.github.thushear.redis;

import com.github.thushear.BaseTest;
import com.github.thushear.redis.utils.RedisUtils;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * Created by kongming on 2017/5/4.
 */
public class RedisUtilsTest extends BaseTest {


    @Resource
    private RedisUtils redisUtils;


    @Test
    public void setAndGetTest(){
        for (int i = 0; i < 1000; i++) {
            redisUtils.set("re" + i, "val" + i);
        }

        for (int i = 0; i < 1000; i++) {
            String value =  redisUtils.getAndWait("re" + i);
            org.junit.Assert.assertEquals("set and get equals",value,"val" +i);
        }
    }

}
