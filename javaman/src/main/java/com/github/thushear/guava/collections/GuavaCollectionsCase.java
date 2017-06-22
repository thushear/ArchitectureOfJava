package com.github.thushear.guava.collections;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.*;
import com.google.common.primitives.Bytes;
import com.google.common.primitives.Ints;
import com.google.common.primitives.Longs;

import java.lang.reflect.Type;
import java.util.*;

/**
 * Created by kongming on 2017/5/9.
 */
public class GuavaCollectionsCase {


    public static void main(String[] args) {

        //immutableCollections();

//        newMultiSetCase();

//        collectionUtilsCase();

//        iterablesCase();

        listsCase();
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


    /**
     * Guava 新集合类型
     */
    static void newMultiSetCase(){

        HashMultiset<String>  multiSet =   HashMultiset.create();
        multiSet.add("a");multiSet.add("a");multiSet.add("a");multiSet.add("b");multiSet.add("c");
        System.out.println("count = " + multiSet.count("a"));
        System.out.println("count = " + multiSet.count("c"));
        for (Multiset.Entry<String> entry : multiSet.entrySet()) {
            System.out.println("k:" + entry.getElement() + " v:" + entry.getCount());
        }

        System.out.println("test remove");
        multiSet.remove("a");
        for (Multiset.Entry<String> entry : multiSet.entrySet()) {
            System.out.println("k:" + entry.getElement() + " v:" + entry.getCount());
        }


        SortedMultiset<String> stringSortedMultiset = TreeMultiset.create();

        stringSortedMultiset.add("a");stringSortedMultiset.add("b");stringSortedMultiset.add("b");
        stringSortedMultiset.add("b");stringSortedMultiset.add("c");stringSortedMultiset.add("c");

        for (Multiset.Entry<String> stringEntry : stringSortedMultiset.entrySet()) {

            System.out.println("k:" + stringEntry.getElement() + "  v:" + stringEntry.getCount());

        }
        System.out.println("sublist Test");
        SortedMultiset<String> subSortedMultiSet = stringSortedMultiset.subMultiset("a",BoundType.CLOSED,"b",BoundType.OPEN);
        for (Multiset.Entry<String> stringEntry : subSortedMultiSet.entrySet()) {
            System.out.println("k:" + stringEntry.getElement() + " v" + stringEntry.getCount());
        }

        System.out.println("queue===");
        Queue queue = Queues.newLinkedBlockingDeque(Lists.newArrayList("f","e","d","c","b","a"));
        queue.forEach(ele -> {
            System.out.println("ele = " + ele);
        });



    }


    /**
     * 测试guava集合的各种工具类和方法
     */
    static void  collectionUtilsCase(){

        List<String> stringList = Lists.newArrayListWithExpectedSize(20);
        stringList.add("1");stringList.add("2");
        stringList.add("3");stringList.add("4");
        stringList.add("a");stringList.add("b");
        stringList.add("c");stringList.add("d");
        stringList.forEach(ele -> {
            System.out.println("ele = " + ele);
        });

        List<List<String>> partitionList = Lists.partition(stringList,2);
        partitionList.forEach(ele -> {
            System.out.println("======");
            for (String s : ele) {
                System.out.println(s);
            }
        });

        List<Character> characters = Lists.charactersOf("abcdefg");
        characters.forEach(character -> {
            System.out.println("character = " + character);
        });

        Set<String> concurrentSet = Sets.newConcurrentHashSet();
        concurrentSet.add("b");concurrentSet.add("c");concurrentSet.add("a");
        System.out.println("concurrentSet = " + concurrentSet);

        TreeSet<String> treeSet = Sets.newTreeSet();
        treeSet.add("z");
        treeSet.add("h");treeSet.add("a");
        System.out.println("treeSet = " + treeSet);

        Map<String,String> concurrentMap = Maps.newConcurrentMap();
        concurrentMap.put("a","a");concurrentMap.put("b","b");
        concurrentMap.put("c","c");concurrentMap.put("d","d");
        System.out.println("concurrentMap = " + concurrentMap);


        TreeMap<String,String> sortedTreeMap =   Maps.newTreeMap();
        sortedTreeMap.put("a","a");sortedTreeMap.put("b","b");sortedTreeMap.put("c","c");
        sortedTreeMap.put("d","d");sortedTreeMap.put("e","e");sortedTreeMap.put("f","f");
        System.out.println(sortedTreeMap);




    }



    static  void  listsCase(){
        List<Integer> fromList = Lists.newArrayList(1,2,3,4);
        List<Integer> toList = Lists.transform(fromList, new Function<Integer, Integer>() {
            @Override
            public Integer apply(Integer input) {
                return input * 2;
            }
        });

        System.out.println("toList = " + toList);
        System.out.println(toList.getClass().getName());
        System.out.println(toList.size());
    }


    static void iterablesCase(){

        Iterable<Integer> intList = Iterables.concat(Ints.asList(1,2,4), Ints.asList(6,8,9));

        System.out.println(Iterables.getLast(intList));
        System.out.println(Iterables.find(intList, new Predicate<Integer>() {
            @Override
            public boolean apply(Integer input) {
                if (6 == input){
                    return true;
                }
                return false;
            }
        }));

    }




}
