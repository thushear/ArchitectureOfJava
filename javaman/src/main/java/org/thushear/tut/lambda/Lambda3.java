package org.thushear.tut.lambda;

import java.util.Comparator;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Created by kongming on 2016/3/9.
 */
public class Lambda3 {

    public static void main(String[] args) {

        Predicate<String> predicate = s -> s.length() > 0;
        System.out.println(predicate.test("foo"));

        System.out.println(predicate.negate().test("foo"));

        Predicate<Boolean> nonNull = Objects::nonNull;

        Predicate<Boolean> isNull = Objects::isNull;
        System.out.println(nonNull.test(null));

        Predicate<String> isEmpty = String::isEmpty;

        System.out.println(isEmpty.test(""));

//        //Functions
//        Function接口接收一个参数，并返回单一的结果。默认方法可以将多个函数串在一起（compse, andThen）
        Function<String,Integer> toInteger = Integer::valueOf;
        Function<String,String>  backToString = toInteger.andThen(String::valueOf);
        String s = backToString.apply("123");
        System.out.println("s = " + s);

//        Suppliers
//        Supplier接口产生一个给定类型的结果。与Function不同的是，Supplier没有输入参数。

        Supplier<Person> personSupplier = Person::new;
        Person person =  personSupplier.get();
        System.out.println("person = " + person);

//        Consumers
//        Consumer代表了在一个输入参数上需要进行的操作。

        Consumer<Person>  consumer = (p) -> System.out.println("hello " + p.lastName);
        consumer.accept(new Person("lucas","kong"));

//        Comparators
//        Comparator接口在早期的Java版本中非常著名。Java 8 为这个接口添加了不同的默认方法

        Comparator<Person> comparator = (p1,p2)-> p1.firstName.compareTo(p2.firstName);
        Person person1 = new Person("thushear","kong");
        Person person2 = new Person("kobe","bryant");
        int c1 = comparator.compare(person1,person2);
        System.out.println(c1);
        int c2 = comparator.reversed().compare(person1,person2);
        System.out.println("c2 = " + c2);

//        Optionals
//        Optional不是一个函数式接口，而是一个精巧的工具接口，用来防止NullPointerEception产生。这个概念在下一节会显得很重要，所以我们在这里快速地浏览一下Optional的工作原理。
//
//        Optional是一个简单的值容器，这个值可以是null，也可以是non-null。考虑到一个方法可能会返回一个non-null的值，也可能返回一个空值。为了不直接返回null，我们在Java 8中就返回一个Optional.

    }
}
