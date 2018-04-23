package com.github.thushear.work.web;

import com.github.thushear.work.util.BigThing;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * <pre>
 * Copyright: www.jd.com
 * Author: kongming@jd.com
 * Created: 2018年04月19日 下午 17:49
 * Version: 1.0
 * Project Name: architecture
 * Last Edit Time: 2018年04月19日 下午 17:49
 * Update Log:
 * Comment:
 * </pre>
 */
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