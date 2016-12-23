package org.thushear.tut.stream;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by kongming on 2016/3/15.
 */
public class Stream4 {


    static class Person {
        String name;
        int age;

        Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        @Override
        public String toString() {
            return name;
        }
    }



    public static void main(String[] args) {
        List<Person> persons =
                Arrays.asList(
                        new Person("Max", 18),
                        new Person("Peter", 23),
                        new Person("Pamela", 23),
                        new Person("David", 12));

        //collect
        List<Person> collectList = persons.stream()
                .filter(person -> person.name.startsWith("P"))
                .collect(Collectors.toList());

        System.out.println(collectList);

        System.out.println("====================");
        persons
                .stream()
                .collect(Collectors.groupingBy(person -> person.age))
                .forEach((age,person) -> System.out.format("age %s : %s \n",age,person));


    }



}
