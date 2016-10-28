package com.github.thushear;

import com.github.thushear.msf.provider.Provider;
import com.github.thushear.msf.server.Server;
import com.github.thushear.msf.service.HelloService;
import com.github.thushear.msf.service.HelloServiceImpl;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp() throws InterruptedException {
        HelloService helloService = new HelloServiceImpl();
        Provider<HelloService> provider = new Provider<>(helloService,"com.github.thushear.msf.service.HelloService");
        Server server = new Server("0.0.0.0",8080);
        provider.setServer(server);
        provider.export();
        Thread.currentThread().join();
    }
}
