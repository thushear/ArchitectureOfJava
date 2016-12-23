package org.thushear.tut.map;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kongming on 2016/3/9.
 */
public class Map1 {


    public static void main(String[] args) {

//        Map
//
//        正如前面已经提到的那样，map是不支持流操作的。而更新后的map现在则支持多种实用的新方法，来完成常规的任务。
        Map<Integer,String>  map = new HashMap<>();
        for (int i = 0; i < 10; i++) {
            map.putIfAbsent(i,"val" + i);
        }

        map.forEach((id,val) -> System.out.println(val));

//        上面的代码风格是完全自解释的：putIfAbsent避免我们将null写入；forEach接受一个消费者对象，从而将操作实施到每一个map中的值上。

        System.out.println("==================");
        map.computeIfPresent(3, (num , val)-> val + num);

        System.out.println(map.get(3));

        map.computeIfPresent(9,(num,val) -> null);
        System.out.println(map.containsKey(9));

        System.out.println("==================");
        map.computeIfAbsent(23,num -> "val" + num);
        System.out.println(map.get(23));
        map.computeIfAbsent(23,num -> "bam");

        System.out.println(map.get(23));

        System.out.println("==================");
        System.out.println(map.getOrDefault(23,"notFound"));
        System.out.println(map.getOrDefault(25,"notFound"));
        System.out.println("==================");
        System.out.println(map.get(23));
        map.remove(23,"val23");
        System.out.println(map.get(23));

        map.merge(3,"val3",(value,newValue)->value.concat(newValue));
        System.out.println(map.get(3));
        map.merge(3,"concat",(value,newvalue) -> value.concat(newvalue));
        System.out.println(map.get(3));
    }
}
