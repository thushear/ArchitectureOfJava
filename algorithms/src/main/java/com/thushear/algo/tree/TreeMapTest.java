package com.thushear.algo.tree;

import java.util.TreeMap;

/**
 * Created by kongming on 2017/5/3.
 */
public class TreeMapTest {


    public static void main(String[] args) {

        TreeMap<String,String> treeMap = new TreeMap<>();
        treeMap.put("1","1");
        treeMap.put("2","2");
        treeMap.put("3","3");
        treeMap.put("4","4");
        treeMap.put("5","5");
        treeMap.put("6","6");
        treeMap.put("7","7");

        System.out.println(treeMap);

        System.out.println(treeMap.ceilingKey("10"));
        System.out.println(treeMap.ceilingKey("4"));
        System.out.println(treeMap.ceilingKey("-1"));



    }



}
