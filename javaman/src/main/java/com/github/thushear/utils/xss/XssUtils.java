package com.github.thushear.utils.xss;

import org.owasp.validator.html.*;

/**
 * Created by kongming on 2016/10/20.
 */
public class XssUtils {





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

    private static AntiSamy antiSamy  = init() ;



    public static String xssClean(String html) throws PolicyException, ScanException {
        CleanResults cleanResults = antiSamy.scan(html);
        return cleanResults.getCleanHTML();
    }






}
