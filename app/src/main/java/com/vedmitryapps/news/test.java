package com.vedmitryapps.news;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class test {
    public static void main(String[] args) throws ParseException {
        String dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";
        SimpleDateFormat format = new SimpleDateFormat(dateFormat);

        String date = "2017-10-11T04:14:02.302Z";
        Date date1 = format.parse(date);

        long difference = System.currentTimeMillis() - date1.getTime();
        double hour = difference/(double)1000/(double)60/(double)60;
        System.out.println(hour);
    }
}