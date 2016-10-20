package com.github.thushear.utils.xss;

import org.apache.commons.lang.StringEscapeUtils;
import org.owasp.validator.html.*;

/**
 * Created by kongming on 2016/10/20.
 */
public class AntiSamyLearn {



     static AntiSamy init(){

         try {
             Policy policy = Policy.getInstance(AntiSamyLearn.class.getResourceAsStream("/antisamy-group-1.0.xml"));
             AntiSamy antiSamy = new AntiSamy(policy);
             return antiSamy;
         } catch (PolicyException e) {
             e.printStackTrace();
         }
         return null;
     }

    public static AntiSamy antiSamy  = init() ;






    public static String xssClean(String html) throws PolicyException, ScanException {
        CleanResults cleanResults = antiSamy.scan(html);
        return cleanResults.getCleanHTML();
    }


    public static void main(String[] args) throws ScanException, PolicyException {
        String html = "<script>alert(1)</script>  方式发生发生";
        System.out.println(StringEscapeUtils.escapeHtml(html));
        System.out.println(xssClean(html));
    }
}
