package org.thushear.tut.lambda;

/**
 * Created by kongming on 2016/3/8.
 */
public class Lambda2 {


    int outerNum;

    static int outerStaticNum;



    public void testLambdaScopes(){
        Converter<Integer,String> converter = from -> {
            outerNum = 33;
            return String.valueOf(from * outerNum);
        };
        System.out.println(converter.convert(33));
        System.out.println("outerNum = " + outerNum);

        Converter<Integer,String> converter1 = from -> {
            outerStaticNum = 55;
            return String.valueOf(from * outerStaticNum);
        };
        System.out.println(converter1.convert(33));

    }



    @FunctionalInterface
    interface Converter<F,T>{
        T convert(F from);
    }


    static class Something{

        String startsWith(String s ){
            return String.valueOf(s.charAt(0));
        }
    }

    interface PersonFactory<P extends Person> {
        P create(String firstName, String lastName);
    }


    public static void main(String[] args) {
        Converter<String,Integer> converter = (from ) -> Integer.valueOf(from);
        Integer i = converter.convert("123");
        System.out.println("i = " + i);

        //Java 8 允许你通过::关键字获取方法或者构造函数的的引用。上面的例子就演示了如何引用一个静态方法。
        Converter<String,Integer> converter1 = Integer::valueOf;
        Integer j = converter1.convert("234");
        System.out.println("j = " + j);

        Something something = new Something();
        Converter<String,String> converter2 = something::startsWith;
        System.out.println(converter2.convert("java8"));

        PersonFactory<Person> factory = Person::new;

        Person person = factory.create("lucas","kong");
        System.out.println(person);

        /*Lambda的范围

        对于lambdab表达式外部的变量，其访问权限的粒度与匿名对象的方式非常类似。你能够访问局部对应的外部区域的局部final变量，以及成员变量和静态变量。

        访问局部变量
        我们可以访问lambda表达式外部的final局部变量：*/

        final int num = 3;
        Converter<Integer,String> converter3 = from -> String.valueOf(from * num);
        String result = converter3.convert(5);
        System.out.println("result = " + result);

        /*访问成员变量和静态变量
        与局部变量不同，我们在lambda表达式的内部能获取到对成员变量或静态变量的读写权。这种访问行为在匿名对象里是非常典型的。*/

        new Lambda2().testLambdaScopes();
    }

}

class Person {

    String firstName;

    String lastName;

    public Person() {
    }

    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "Person{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}