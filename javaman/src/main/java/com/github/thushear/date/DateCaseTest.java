package com.github.thushear.date;

import org.apache.commons.lang.time.DateFormatUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;
import org.joda.time.format.FormatUtils;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by kongming on 2017/5/24.
 */
public class DateCaseTest {


    public static void main(String[] args) {

        DateTime endDateTime = new DateTime(2017,5,24,16,0,0);
        DateTime dateTime = DateTime.now();
        dateTime = dateTime.minusHours(1);
        System.out.println(dateTime.isBefore(endDateTime));
        System.out.println(dateTime.toString("yyyyMMddHH"));
        dateTime = dateTime.minusHours(1);
        System.out.println(dateTime.isBefore(endDateTime));
        System.out.println(dateTime.toString("yyyyMMddHH"));
        dateTime = dateTime.minusHours(24);
        System.out.println(dateTime.isBefore(endDateTime));
        System.out.println(dateTime.toString("yyyyMMddHH"));

        String formatDateTime = DateTimeFormat.forPattern("yyyyMMddHHmmss").print(DateTime.now());
        System.out.println("formatDateTime = " + formatDateTime);

        Calendar endCalendar = Calendar.getInstance();
        endCalendar.set(2017,4,22,0,0);
        Calendar calendar = Calendar.getInstance();
//        calendar.set(Calendar.HOUR,-1);
        calendar.add(Calendar.HOUR,-1);
        System.out.println(DateFormatUtils.format(calendar.getTime(),"yyyyMMddHH"));
        calendar.add(Calendar.HOUR,-1);
        System.out.println(DateFormatUtils.format(calendar.getTime(),"yyyyMMddHH"));
        calendar.add(Calendar.HOUR,-1);
        System.out.println(DateFormatUtils.format(calendar.getTime(),"yyyyMMddHH"));

        calendar.set(2017,4,21,0,0);
        System.out.println(DateFormatUtils.format(calendar.getTime(),"yyyyMMddHHmmss"));
        System.out.println(calendar.before(endCalendar));
        calendar.add(Calendar.HOUR,1);
        System.out.println(DateFormatUtils.format(calendar.getTime(),"yyyyMMddHHmmss"));
        calendar.add(Calendar.HOUR,24);
        System.out.println(DateFormatUtils.format(calendar.getTime(),"yyyyMMddHHmmss"));


        System.out.println(calendar.before(endCalendar));

        Date date = new Date();


    }

}
