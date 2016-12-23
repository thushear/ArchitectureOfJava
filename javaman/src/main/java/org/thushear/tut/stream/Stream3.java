package org.thushear.tut.stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by kongming on 2016/3/15.
 */
public class Stream3 {

    public static void main(String[] args) {

        Arrays.asList("a1","a2","a3")
                .stream().findFirst().ifPresent(System.out::println);

        Stream.of("a1","a2","a3")
                .findFirst().ifPresent(System.out::println);


        IntStream.range(1,4).forEach(System.out::println);

        Arrays.stream(new int[]{1,2,3})
                .map(n -> 2 * n + 1)
                .average().ifPresent(System.out::println);


        Stream.of("a1","a2","a3")
                .map(s -> s.substring(1))
                .mapToInt(Integer::parseInt)
                .max()
                .ifPresent(System.out::println);

        IntStream.range(1,5)
                .mapToObj(i -> "a" + i)
                .forEach(System.out::println);


        Stream.of(1.0,2.0,3.0)
                .mapToInt(Double::intValue)
                .mapToObj(i -> "a" + i)
                .forEach(System.out::println);

        Stream.of("d2", "a2", "b1", "b3", "c")
                .filter(s -> {
                    System.out.println("filter: " + s);
                    return true;
                });

        Stream.of("d2","a2","b1","b3","c")
                .filter(s -> {
                    System.out.println("filter :" + s);
                    return true;
                })
                .forEach(s -> System.out.println("foreach : " + s));

        Stream.of("d2","a2","b1","b3","c")
                .map(s -> {
                    System.out.println("map:" + s);
                    return s.toUpperCase();
                })
                .anyMatch(s1 -> {
                    System.out.println("Anymatch :" + s1);
                    return s1.startsWith("A");
                });

        System.out.println("=======================");

        Stream.of("d2", "a2", "b1", "b3", "c")
                .map(s -> {
                    System.out.println("map: " + s);
                    return s.toUpperCase();
                })
                .filter(s -> {
                    System.out.println("filter: " + s);
                    return s.startsWith("A");
                })
                .forEach(s -> System.out.println("forEach: " + s));
        System.out.println("=======================");

        Stream.of("d2", "a2", "b1", "b3", "c")
                .filter(s -> {
                    System.out.println("filter: " + s);
                    return s.startsWith("a");
                })
                .map(s -> {
                    System.out.println("map: " + s);
                    return s.toUpperCase();
                })
                .forEach(s -> System.out.println("forEach: " + s));

        System.out.println("=======================");

        Stream.of("d2", "a2", "b1", "b3", "c")
                .sorted((s1, s2) -> {
                    System.out.printf("sort: %s; %s\n", s1, s2);
                    return s1.compareTo(s2);
                })
                .filter(s -> {
                    System.out.println("filter: " + s);
                    return s.startsWith("a");
                })
                .map(s -> {
                    System.out.println("map: " + s);
                    return s.toUpperCase();
                })
                .forEach(s -> System.out.println("forEach: " + s));

//        First, the sort operation is executed on the entire input collection. In other words sorted is executed horizontally. So in this case sorted is called eight times for multiple combinations on every element in the input collection.
//
//                Once again we can optimize the performance by reordering the chain:
        //sorted 执行是水平的
        System.out.println("=======================");

        Stream.of("d2", "a2", "b1", "b3", "c")
                .filter(s -> {
                    System.out.println("filter: " + s);
                    return s.startsWith("a");
                })
                .sorted((s1, s2) -> {
                    System.out.printf("sort: %s; %s\n", s1, s2);
                    return s1.compareTo(s2);
                })
                .map(s -> {
                    System.out.println("map: " + s);
                    return s.toUpperCase();
                })
                .forEach(s -> System.out.println("forEach: " + s));


    }
}
