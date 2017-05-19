package com.github.thushear.guava.base;

import com.google.common.base.*;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;

import java.util.List;

/**
 * Created by kongming on 2017/5/8.
 */
public class GuavaBaseCase {


    public static void main(String[] args) {
//        Optional optional = optionalCase();
//        System.out.println(optional.isPresent() + "|" + optional.orNull() + "|" + optional.toString() + "|" + optional.or(new Supplier() {
//            @Override
//            public Object get() {
//                return "supplier";
//            }
//        }));


//        preConditionsCase();
//        ordering();
//
//        objects();
//        throwable();

//        joinerCase();
        splliterCase();
        charMatcherCase();
        caseFormatCase();
    }


    static Optional optionalCase(){
        return Optional.absent();
    }



    static void preConditionsCase(){
        String a = null;
        String notNull = Preconditions.checkNotNull("aa");
//        Preconditions.checkArgument(a != null, "a must not be null");
//        Preconditions.checkNotNull(a,"a must not be null %s",a);

    }


    static void ordering(){
        Ordering natural = Ordering.natural().nullsFirst();
        List sortedList = natural.sortedCopy(Lists.newArrayList("22","11","01",null,"55"));
        System.out.println("sortedList = " + sortedList);

        List stringList = Ordering.usingToString().nullsFirst().sortedCopy(Lists.newArrayList("09","01","00","2","10"));
        System.out.println("stringList = " + stringList);
        int index = Ordering.usingToString().binarySearch(stringList,"2");
        System.out.println("index = " + index);
    }


    static void objects(){

        System.out.println(Objects.equal(null,null));
        System.out.println(Objects.equal("a","a"));
        System.out.println(Objects.equal("a",null));

        System.out.println(Objects.hashCode(new Person("ttt",11), new String("123"), 11L));
        System.out.println(Objects.hashCode("1","2","3"));

        System.out.println(Objects.toStringHelper(Person.class).toString());

        System.out.println(MoreObjects.firstNonNull("a",null));
        System.out.println(MoreObjects.firstNonNull(null,"a"));
//        System.out.println(MoreObjects.firstNonNull(null,null));
//        MoreObjects.firstNonNull(null,null);
    }


    static void throwable(){

        try {
            Throwables.propagateIfInstanceOf(new IllegalArgumentException("error params"), RuntimeException.class);
        } catch (RuntimeException e) {
            e.printStackTrace();
            String cause = Throwables.getStackTraceAsString(e);
            System.out.println("cause = " + cause);
            throw Throwables.propagate(e);

        }


    }



    static void joinerCase(){
        Joiner joiner = Joiner.on(":");
        System.out.println(joiner.join("a","b","c","d"));

        Joiner nullSkipJoiner = Joiner.on(" | ").useForNull("--");
        String joinStr = nullSkipJoiner.join(Lists.newArrayList("hello","how low ","high"));
        System.out.println("joinStr = " + joinStr);
    }


    static void splliterCase(){
        String str = "abc,,   ff,'',tel sla,\"\",    ,";
        Splitter splitter = Splitter.on(",").omitEmptyStrings().trimResults();
        splitter.split(str).forEach(s -> {
            System.out.println("s = " + s);
        });

    }


    static void charMatcherCase(){
        String str = "abc,,   ff,'',tel sla,\"\",    ,123";
        System.out.println(CharMatcher.DIGIT.retainFrom(str));
        System.out.println(CharMatcher.DIGIT.removeFrom(str));
        System.out.println(CharMatcher.DIGIT.countIn(str));

        System.out.println(CharMatcher.JAVA_ISO_CONTROL.removeFrom(str));
        System.out.println(CharMatcher.WHITESPACE.removeFrom(str));

        str.getBytes(Charsets.UTF_8);

    }


    static void caseFormatCase(){

        String caseFormat = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE,"helloWorld");
        System.out.println("caseFormat = " + caseFormat);
        System.out.println(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_HYPHEN,"helloWorld"));



    }


}
class Person {

    String name;

    int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}