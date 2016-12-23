package org.thushear.tut.time;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;

/**
 * Created by kongming on 2016/3/11.
 */
public class LocalDate1 {


    public static void main(String[] args) {

//        LocalDate
//        本地时间表示了一个独一无二的时间，例如：2014-03-11。这个时间是不可变的，与LocalTime是同源的。下面的例子演示了如何通过加减日，月，年等指标来计算新的日期。记住，每一次操作都会返回一个新的时间对象。

        LocalDate today = LocalDate.now();
        System.out.println(today);
        LocalDate tommorow =  today.plus(1, ChronoUnit.DAYS);
        System.out.println(tommorow);

        LocalDate yesterday = tommorow.minusDays(2);
        System.out.println(yesterday);

        LocalDate oneDay = LocalDate.of(2015, Month.DECEMBER,22);
        System.out.println(oneDay);
        DayOfWeek dayOfWeek = oneDay.getDayOfWeek();
        System.out.println(dayOfWeek);



    }
}
