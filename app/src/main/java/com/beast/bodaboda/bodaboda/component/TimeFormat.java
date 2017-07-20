package com.beast.bodaboda.bodaboda.component;

import android.text.format.DateUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by simba on 3/3/17.
 */

public class TimeFormat {
    public String convertDateNormal(String timeStamp){
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");


            Date netDate = format.parse(timeStamp);

            long timet=netDate.getTime();

            String date1=    dateFormat.format(netDate);

            return date1;
        } catch (Exception ex) {
            return "xxx";
        }

    }
    public String convertDateToUs(String timeStamp){
        try {
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");



            Date netDate = format.parse(timeStamp);

            long timet=netDate.getTime();

            String date1=    dateFormat.format(netDate);

            return date1;
        } catch (Exception ex) {
            return "xxx";
        }

    }
    public String getRelativeDate(String timeStamp) {
        try {
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            sdf2.setTimeZone(TimeZone.getTimeZone("GMT"));
            Date netDate = sdf2.parse(timeStamp);


            long timet=netDate.getTime();

            Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
            long now= cal.getTimeInMillis();

            return  DateUtils.getRelativeTimeSpanString( timet, now, DateUtils.SECOND_IN_MILLIS).toString();
            //return  DateUtils.getRelativeDateTimeString(context, timet, DateUtils.SECOND_IN_MILLIS, DateUtils.WEEK_IN_MILLIS, DateUtils.FORMAT_ABBREV_ALL).toString();
        } catch (Exception ex) {
            return "xxx";
        }

    }
    public String getPhoneNumber(String myPhone){
        String sPhone="";
        long convertPhone=Long.parseLong(myPhone);
        if(convertPhone>700000000 && convertPhone< 800000000){
            sPhone = "254"+convertPhone;

        }else if(convertPhone> 800000000){
            sPhone =convertPhone+"";
        }
return sPhone;
    }

    public String getIsoTime(){
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'"); // Quoted "Z" to indicate UTC, no timezone offset
        df.setTimeZone(tz);
        String nowAsISO = df.format(new Date());
        return nowAsISO;

    }
}
