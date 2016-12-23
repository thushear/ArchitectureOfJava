package org.thushear.tut.lambda;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by kongming on 2016/3/8.
 */
public class Lambda1 {

    public static void main(String[] args) {
        List<String> names = Arrays.asList("peter","john","ann","micheal");
        Collections.sort(names, new Comparator<String>() {
            @Override
            public int compare(String a, String b) {
                return a.compareTo(b);
            }
        });

        System.out.println(names);
        List<String> names1 = Arrays.asList("peter","john","ann","micheal");

        Collections.sort(names1,(String a , String b) ->{
            return a.compareTo(b);
        });
        System.out.println(names1);

        List<String> names2 = Arrays.asList("peter","john","ann","micheal");

        Collections.sort(names2,(String a , String b) -> a.compareTo(b));
        System.out.println(names2);

        List<String> names3 = Arrays.asList("peter", null, "anna", "mike", "xenia");
        names3.sort(Comparator.nullsLast(String::compareTo));
        System.out.println(names3);

    }
}
