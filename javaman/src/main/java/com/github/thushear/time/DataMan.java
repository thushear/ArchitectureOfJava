package com.github.thushear.time;

import org.joda.time.DateTime;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.TemporalUnit;
import java.util.*;

/**
 * Created by kongming on 2016/12/16.
 */
public class DataMan {


    static LocalDate BEGIN_DATE = LocalDate.parse("2017_06_06",DateTimeFormatter.ofPattern("yyyy_MM_dd"));

    public static void main(String[] args) {

        jodaApi();

//        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy_MM_dd");

        Date date = new Date();

        Date firstDate = getFirstDayOfWeek(date);

        System.out.println("firstDate = " + dateFormat.format(firstDate));

        Date endDate = getLastDayOfWeek(date);

        System.out.println("endDate = " + dateFormat.format(endDate));


        Calendar calendar = Calendar.getInstance();

        LocalDate localDate = LocalDate.now();
        System.out.println(localDate.getDayOfWeek());
        System.out.println("==========================");
        localDate = localDate.minus(1, ChronoUnit.WEEKS);
        System.out.println(localDate);
        localDate = localDate.minusWeeks(1);
        System.out.println(localDate);
        localDate = localDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        System.out.println(localDate);
        localDate = localDate.with(TemporalAdjusters.previous(DayOfWeek.MONDAY));
        System.out.println(localDate);
        localDate = localDate.with(TemporalAdjusters.previous(DayOfWeek.SUNDAY));
        System.out.println(localDate);
        System.out.println("==========================");




        localDate = localDate.minusWeeks(1);

        System.out.println(dateTimeFormatter.format(localDate));

        System.out.println(calendar.getTime());
        calendar.add(Calendar.DAY_OF_WEEK,-7);
        System.out.println(calendar.getTime());

        calendar.add(Calendar.DAY_OF_WEEK,-7);
        System.out.println(calendar.getTime());

        System.out.println(getFirstDayOfWeek(calendar.getTime()));
        calendar.add(Calendar.DAY_OF_WEEK,-7);
        System.out.println(getFirstDayOfWeek(calendar.getTime()));
        System.out.println(getLastDayOfWeek(calendar.getTime()));



        LocalDate now = LocalDate.now();

        System.out.println(now.with(TemporalAdjusters.previous(DayOfWeek.MONDAY)));
        System.out.println(now.with(TemporalAdjusters.previous(DayOfWeek.SUNDAY)));

        LocalDate end = now.with(TemporalAdjusters.previous(DayOfWeek.SUNDAY));
        System.out.println("end = " + end);
        LocalDate start = end.with(TemporalAdjusters.previous(DayOfWeek.MONDAY));
        System.out.println("start = " + start);
        System.out.println(dateTimeFormatter.format(start));
        System.out.println(dateTimeFormatter.format(end));

        LocalDate startDate = LocalDate.parse("2017_06_01",dateTimeFormatter);
        System.out.println("startDate = " + startDate);

        System.out.println(end.isBefore(startDate));


        System.out.println(getWeeks());


        System.out.println(LocalDate.now());
        System.out.println(LocalDateTime.now());

        LocalDate after23D = LocalDate.now().plusDays(23);
        System.out.println("after23D = " + after23D);

        Date date1 = Date.from(after23D.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        System.out.println("date1 = " + date1);

         

    }


    /**
     * 取得指定日期所在周的第一天

     */
    public static Date getFirstDayOfWeek(Date date) {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(date);
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek()); // Monday
        return c.getTime ();
    }

    /**
     * 取得指定日期所在周的最后一天
     */
    public static Date getLastDayOfWeek(Date date) {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(date);
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6); // Sunday
        return c.getTime();
    }




    private static void jodaApi() {
        DateTime dateTime = new DateTime(new Date());
        System.out.println(dateTime);
        DateTime threeAfterDate = dateTime.plusDays(3);

        System.out.println(threeAfterDate);

        System.out.println(threeAfterDate.toDate());
    }




    public static List<String> getWeeks(){

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        LinkedList<String> list = new LinkedList<>();
        LocalDate now = LocalDate.now();

        while (now.isAfter(BEGIN_DATE)){
            LocalDate lastSunday = now.with(TemporalAdjusters.previous(DayOfWeek.SUNDAY));

            LocalDate lastMonday = now.with(TemporalAdjusters.previous(DayOfWeek.MONDAY));

            String week = dateTimeFormatter.format(lastMonday) + "_" + dateTimeFormatter.format(lastSunday);

            list.addLast(week);
            now = lastMonday;
        }

        return list;
    }


}
