package com.github.thushear.utils.xss;

import org.apache.commons.lang.StringEscapeUtils;
import org.owasp.esapi.ESAPI;

/**
 * Created by kongming on 2016/10/20.
 */
public class ESApiLearn {



    public static void main(String[] args) {
//        String html = "<script>alert(1)</script>";
        String html = "alert(1) 中国";
        String xss = ESAPI.encoder().canonicalize(html);
        System.out.println("xss = " + xss);
        String xssFilter = ESAPI.encoder().encodeForHTML(xss);
        System.out.println("xssFilter = " + xssFilter);

        System.out.println(StringEscapeUtils.escapeHtml(html));

        System.out.println(StringEscapeUtils.escapeHtml("中华人民共和国"));
        System.out.println(StringEscapeUtils.unescapeHtml("&#20013;&#22269;&#20154;&#27665;&#31449;&#36215;&#26469;&#20102;"));
        System.out.println(StringEscapeUtils.unescapeHtml("中华人民共和国"));


    }
}
