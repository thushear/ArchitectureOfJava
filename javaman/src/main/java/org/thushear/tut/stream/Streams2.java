package org.thushear.tut.stream;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Created by kongming on 2016/3/9.
 */
public class Streams2 {

    public static final int MAX = 100000;

    public static void sortSeq(){
        List<String> sortedList = new ArrayList<>();
        for (int i = 0; i < MAX; i++) {
            sortedList.add(UUID.randomUUID().toString());

        }

        long t1 = System.nanoTime();

        long count =  sortedList.stream().sorted().count();
        System.out.println("count = " + count);

        long t2 = System.nanoTime();
        long millis = TimeUnit.NANOSECONDS.toMillis(t2 - t1);
        System.out.println(String.format("sort seq cost time " + millis + "  ms") );

    }


    public static void sortParrel(){
        List<String> sortedList = new ArrayList<>();
        for (int i = 0; i < MAX; i++) {
            sortedList.add(UUID.randomUUID().toString());

        }

        long t1 = System.nanoTime();

        long count = sortedList.parallelStream().sorted().count();
        System.out.println("count = " + count);

        long t2 = System.nanoTime();
        long millis = TimeUnit.NANOSECONDS.toMillis(t2 - t1);
        System.out.println(String.format("sort seq cost time " + millis + "  ms") );
    }

//    如你所见，所有的代码段几乎都相同，唯一的不同就是把stream()改成了parallelStream(), 结果并行排序快了50%。
    public static void main(String[] args) {

        sortSeq();
        sortParrel();
    }


}
