package com.example.mymoviememoir.daterelated;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateRelated {
    public static Date getCurrentDate() {
        Date date = new Date(System.currentTimeMillis());
        return date;
    }

    public static int dateCompared(String date1) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
        Date dob;
        try {
            dob = dateFormat.parse(date1);
            return dob.compareTo(getCurrentDate());
        } catch (ParseException e) {
            e.printStackTrace();
            return 1;
        }
    }


    public static String currentDateString() {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date curDate = new Date(System.currentTimeMillis());
        String str = formatter.format(curDate);
        return  str;
    }


    public static Date convertToDate(String dateStr){
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }


    }

    public static String dateString(Date date){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(date);

    }

    public static String makeUp( String dateStr){
        try {
            SimpleDateFormat formatter  = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.CHINA);
            Date date = formatter.parse(dateStr);
            return formatter.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }


    }




}
