 package org.thushear.tut.time;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Locale;
import java.util.Set;

 /**
 * Created by kongming on 2016/3/11.
 */
public class LocalTime1 {

    public static void main(String[] args) {

//        Clock
//        Clock提供了对当前时间和日期的访问功能。Clock是对当前时区敏感的，并可用于替代System.currentTimeMillis()方法来获取当前的毫秒时间。当前时间线上的时刻可以用Instance类来表示。Instance也能够用于创建原先的java.util.Date对象

        Clock clock = Clock.systemDefaultZone();
        System.out.println(clock.millis());

        Instant instant = clock.instant();
        Date date = Date.from(instant);
        System.out.println("date = " + date);

        System.out.println("===================");

//        Timezones
//        时区类可以用一个ZoneId来表示。时区类的对象可以通过静态工厂方法方便地获取。时区类还定义了一个偏移量，用来在当前时刻或某时间与目标时区时间之间进行转换。

        Set<String> zones = ZoneId.getAvailableZoneIds();
        System.out.println("zones = " + zones);

        ZoneId zoneId1 = ZoneId.of("Africa/Nairobi");
        ZoneId zoneId2 = ZoneId.of("Europe/Bratislava");

        System.out.println(zoneId1.getRules());
        System.out.println(zoneId2.getRules());

        LocalTime localTime1 = LocalTime.now(zoneId1);
        LocalTime localTime2 = LocalTime.now(zoneId2);
        System.out.println(localTime1);
        System.out.println(localTime2);

        System.out.println(localTime1.isBefore(localTime2));

        long gap1 = ChronoUnit.HOURS.between(localTime1,localTime2);

        long gap2 = ChronoUnit.MINUTES.between(localTime1,localTime2);
        System.out.println(gap1);
        System.out.println(gap2);


//        LocalTime是由多个工厂方法组成，其目的是为了简化对时间对象实例的创建和操作，包括对时间字符串进行解析的操作。
        LocalTime localTime = LocalTime.now();
        System.out.println(localTime);

        LocalTime localTime3 = LocalTime.of(23,59,59);
        System.out.println(localTime3);

        DateTimeFormatter germanFormatter =
                DateTimeFormatter
                        .ofLocalizedTime(FormatStyle.SHORT)
                        .withLocale(Locale.GERMAN);

        LocalTime leetTime = LocalTime.parse("13:37", germanFormatter);
        System.out.println(leetTime);


        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT).withLocale(Locale.GERMAN);

        LocalTime localTime4 = LocalTime.parse("13:37",dateTimeFormatter);
        System.out.println(localTime4);


    }
}
