package com.github.thushear.collection;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by kongming on 2017/5/10.
 */
public class CollectionCase {

    public static void main(String[] args) {

        List<String>  list = new ArrayList<>();
        list.add("a");list.add("b");list.add("c");list.add("d");

//        try {
//            for (String s : list) {
//                if ("b".equalsIgnoreCase(s)){
//                    list.remove(s);
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }


        try {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).equalsIgnoreCase("b")){
                    list.remove(i);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        try {
            Iterator iterator = list.listIterator();
            while (iterator.hasNext()){

                if (iterator.next().equals("b")){
                    iterator.remove();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
