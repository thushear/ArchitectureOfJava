package com.github.thushear.time;

import org.joda.time.DateTime;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * Created by kongming on 2016/12/16.
 */
public class DataMan {

    public static void main(String[] args) {

        jodaApi();
    }


    private static void jodaApi() {
        DateTime dateTime = new DateTime(new Date());
        System.out.println(dateTime);
        DateTime threeAfterDate = dateTime.plusDays(3);

        System.out.println(threeAfterDate);

        System.out.println(threeAfterDate.toDate());
    }


}
