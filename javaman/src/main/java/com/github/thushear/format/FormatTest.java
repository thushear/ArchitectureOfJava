package com.github.thushear.format;

import com.google.common.base.Strings;
import org.apache.commons.lang.StringUtils;

import java.text.MessageFormat;

/**
 * Format related Test Case
 * Created by kongming on 2017/5/9.
 */
public class FormatTest {

    public static void main(String[] args) {

        String formatStr = MessageFormat.format("{0} is not {1}","a","b");
        System.out.println("formatStr = " + formatStr);

        formatStr = String.format("%s is not %s","c","d");
        System.out.println("formatStr = " + formatStr);
    }


}
