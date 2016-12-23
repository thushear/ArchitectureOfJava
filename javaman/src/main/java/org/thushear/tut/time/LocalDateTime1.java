package org.thushear.tut.time;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.Date;

/**
 * Created by kongming on 2016/3/12.
 */
public class LocalDateTime1 {


    public static void main(String[] args) {

//        LocalDateTime
//        LocalDateTime表示的是日期-时间。它将刚才介绍的日期对象和时间对象结合起来，形成了一个对象实例。LocalDateTime是不可变的，与LocalTime和LocalDate的工作原理相同。我们可以通过调用方法来获取日期时间对象中特定的数据域。
        LocalDateTime localDateTime =LocalDateTime.of(2014, Month.APRIL,11,22,22,33);

        System.out.println(localDateTime.getDayOfWeek());

        System.out.println(localDateTime.getDayOfYear());

        System.out.println(localDateTime.getLong(ChronoField.MINUTE_OF_DAY));

//        如果再加上的时区信息，LocalDateTime能够被转换成Instance实例。Instance能够被转换成以前的java.util.Date对象。

        Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();

        Date date =  Date.from(instant);
        System.out.println(date);

//        格式化日期-时间对象就和格式化日期对象或者时间对象一样。除了使用预定义的格式以外，我们还可以创建自定义的格式化对象，然后匹配我们自定义的格式

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String now = dateTimeFormatter.format(LocalDateTime.now());
        System.out.println("now = " + now);


        LocalDateTime time = new LocalDateTime();

    }
}
