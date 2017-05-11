package com.github.thushear.guava.collections;

import com.google.common.collect.*;

import java.util.List;
import java.util.Map;

/**
 * Created by kongming on 2017/5/9.
 */
public class GuavaCollectionsCase {


    public static void main(String[] args) {
        immutableCollections();
    }



    static void immutableCollections(){
        ImmutableSet<String> constantSet = ImmutableSet.of("a","b","c");
        System.out.println("constantSet = " + constantSet);
        ImmutableSet<String> copyConstantSet = ImmutableSet.copyOf(constantSet);
        System.out.println("copyConstantSet = " + copyConstantSet);
        constantSet.forEach(ele ->{
            System.out.println("ele = " + ele);
            System.out.println(ele.hashCode());
        });

        copyConstantSet.forEach(ele ->{
            System.out.println("ele = " + ele);
            System.out.println(ele.hashCode());
        });


        ImmutableList immutableList = ImmutableList.of("a","b","f","c");
        immutableList.forEach(ele -> {
            System.out.println(ele);
        });

        System.out.println("==================================");
        List testList = Lists.newArrayList("f","e","d","c");
        List copyImmutableList = ImmutableList.copyOf(testList);
        copyImmutableList.forEach(ele ->{
            System.out.println("ele = " + ele);
            System.out.println(ele.hashCode());
        });
        System.out.println(copyImmutableList.get(0));
//        copyImmutableList.add("g");

        Map map  =  Maps.newHashMap();
        map.put("a","a");map.put("b","b");map.put("c","c");map.put("d","d");
        map.forEach((k,v) -> {
            System.out.println("k = " + k);
            System.out.println("v = " + v);
        });
        Map sortedMap = ImmutableSortedMap.naturalOrder().putAll(map).build();
        sortedMap.forEach( (k,v) -> {
            System.out.println("k = " + k);
            System.out.println("v = " + v);
        });

    }
}
