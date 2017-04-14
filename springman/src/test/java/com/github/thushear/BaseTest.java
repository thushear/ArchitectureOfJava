package com.github.thushear;

import org.junit.Assert;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.StopWatch;

/**
 * Created by kongming on 2017/4/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring-config-test.xml"})
public class BaseTest extends Assert{


    final static protected Logger LOGGER = LoggerFactory.getLogger(BaseTest.class);

    protected StopWatch stopWatch  = new StopWatch();

}
