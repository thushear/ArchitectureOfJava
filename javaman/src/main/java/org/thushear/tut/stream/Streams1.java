package org.thushear.tut.stream;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by kongming on 2016/3/9.
 */
public class Streams1 {

    public static void main(String[] args) {

//        java.util.Stream表示了某一种元素的序列，在这些元素上可以进行各种操作。Stream操作可以是中间操作，
// 也可以是完结操作。完结操作会返回一个某种类型的值，而中间操作会返回流对象本身，并且你可以通过多次调用同一个流操作方法来将操作结果串起来（就像StringBuffer的append方法一样————译者注）。Stream是在一个源的基础上创建出来的，例如java.util.Collection中的list或者set（map不能作为Stream的源）。Stream操作往往可以通过顺序或者并行两种方式来执行。
        List<String> stringCollections = new ArrayList<>();
        stringCollections.add("ddd");
        stringCollections.add("ccc");
        stringCollections.add("bbb");
        stringCollections.add("aaa");
        stringCollections.add("abv");
        stringCollections.add("a3");
        stringCollections.add("a2");
        stringCollections.add("a1");
//        Filter
//        Filter接受一个predicate接口类型的变量，并将所有流对象中的元素进行过滤。该操作是一个中间操作，因此它允许我们在返回结果的基础上再进行其他的流操作（forEach）。ForEach接受一个function接口类型的变量，用来执行对每一个元素的操作。ForEach是一个中止操作。它不返回流，所以我们不能再调用其他的流操作。
        stringCollections.stream().filter(s -> s.startsWith("a")).forEach(System.out::println);

        System.out.println("====sorted==============");

//        Sorted
//        Sorted是一个中间操作，能够返回一个排过序的流对象的视图。流对象中的元素会默认按照自然顺序进行排序，除非你自己指定一个Comparator接口来改变排序规则。
        stringCollections.stream().sorted().filter(s -> s.startsWith("a")).forEach(System.out::println);
//        一定要记住，sorted只是创建一个流对象排序的视图，而不会改变原来集合中元素的顺序。原来string集合中的元素顺序是没有改变的。
        System.out.println(stringCollections);


//        Map
//        map是一个对于流对象的中间操作，通过给定的方法，它能够把流对象中的每一个元素对应到另外一个对象上。下面的例子就演示了如何把每个string都转换成大写的string. 不但如此，你还可以把每一种对象映射成为其他类型。对于带泛型结果的流对象，具体的类型还要由传递给map的泛型方法来决定。
        System.out.println("====Map==============");
        stringCollections.stream().sorted().map(String::toUpperCase).forEach(System.out::println);

//        Match
//        匹配操作有多种不同的类型，都是用来判断某一种规则是否与流对象相互吻合的。所有的匹配操作都是终结操作，只返回一个boolean类型的结果。
        System.out.println("====Match==============");

        boolean b1 = stringCollections.stream().anyMatch(s -> s.startsWith("a"));
        System.out.println("b1 = " + b1);

        boolean b2 = stringCollections.stream().allMatch(s -> s.startsWith("a"));
        System.out.println("b2 = " + b2);

        boolean b3 = stringCollections.stream().noneMatch(s -> s.startsWith("a"));
        System.out.println("b3 = " + b3);


//        Count
//        Count是一个终结操作，它的作用是返回一个数值，用来标识当前流对象中包含的元素数量。
        System.out.println("====count==============");
        long count = stringCollections.stream().filter(s -> s.startsWith("a")).count();
        System.out.println("count = " + count);

//        Reduce
//        该操作是一个终结操作，它能够通过某一个方法，对元素进行削减操作。该操作的结果会放在一个Optional变量里返回。
        System.out.println("====Reduce==============");
        Optional<String> optional = stringCollections.stream().sorted().reduce((s1, s2) -> s1 + "#" + s2);
        optional.ifPresent(System.out::println);

    }
}

