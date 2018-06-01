package com.github.thushear.work.web;

import com.github.thushear.work.util.BigThing;
import org.apache.catalina.startup.Catalina;
import org.apache.catalina.startup.Tomcat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@RestController
public class IndexController {



    @GetMapping("/echo")
    public void testEcho(HttpServletRequest request, HttpServletResponse response) throws IOException, InterruptedException {
        System.err.println("=========================================================");
        //Thread.sleep(6000);
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < 1113072; i++) {
            stringBuffer.append(i);
        }
        PrintWriter out = response.getWriter();
        out.println(stringBuffer.toString());
        out.flush();
        out.close();

    }

}
