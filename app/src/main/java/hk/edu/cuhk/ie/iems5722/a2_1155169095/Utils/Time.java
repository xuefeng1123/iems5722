package hk.edu.cuhk.ie.iems5722.a2_1155169095.Utils;

import static hk.edu.cuhk.ie.iems5722.a2_1155169095.Consts.DayAndMonthGapFormat;
import static hk.edu.cuhk.ie.iems5722.a2_1155169095.Consts.DayBeforeYesterdayGapFormat;
import static hk.edu.cuhk.ie.iems5722.a2_1155169095.Consts.MessageClassTimeFormat;
import static hk.edu.cuhk.ie.iems5722.a2_1155169095.Consts.MinAndHourGapFormat;
import static hk.edu.cuhk.ie.iems5722.a2_1155169095.Consts.TimeSlice;
import static hk.edu.cuhk.ie.iems5722.a2_1155169095.Consts.YearGapFormat;
import static hk.edu.cuhk.ie.iems5722.a2_1155169095.Consts.YesterdayGapFormat;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Time {

    public static long fiveMin =    5*60*1000;
    public static long oneHour =   60*60*1000;
    public static long oneDay = 24*60*60*1000;

    //for test
    public static long getTimeMillis(String timeString, String format){
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try{
            Date date = sdf.parse(timeString);
            return date.getTime();
        }catch (Exception e){
            Log.e("Error", "Error test input!!" + e.toString());
        }
        return 0;
    }


    public static String getChatStartTime(long time, String format){
        Date date = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    //Used to get gap time format: default return MinAndHourGapFormat
    //gapTimeMillis = date - lastDate -> decide if show time bar
    //currTimeMillis = currDate - date -> decide what format the time bar should use
    public static String getGapTimeFormat(long lastTime, long time){
        Date lastDate = new Date(lastTime);//the time of last message
        Date date = new Date(time);//the time of the message
        Date currDate = new Date(System.currentTimeMillis());//curr time
        Date currEndDate = getEndTime();
        Date currStartDate = getStartTime();

        long gapTimeMillis = date.getTime() - lastDate.getTime();
        long currTimeMillis = currDate.getTime() - date.getTime();
        long currDateTimeMillis = currEndDate.getTime() - date.getTime();


        long iterationTime = currTimeMillis;
        int timeIndex = 0;

        long days = currDateTimeMillis / (TimeSlice[0] * TimeSlice[1] * TimeSlice[2] * TimeSlice[3]);
        if(days >= 1){
            gapTimeMillis = currEndDate.getTime() - lastDate.getTime();
            timeIndex = 4;
            iterationTime = days;
        }
        while(timeIndex < TimeSlice.length && iterationTime >= TimeSlice[timeIndex]){
            iterationTime /= TimeSlice[timeIndex++];
        }
        //if the gap time is in
        //[ 5min , 1day)
        if(timeIndex <= 3){

            //continue messages should not show time bar
            if(gapTimeMillis <= fiveMin){
                return "";
            }

            return MinAndHourGapFormat;
        }
        //[ 1day , 1month )
        else if(timeIndex == 4){

            //continue messages should not show time bar
            if(gapTimeMillis <= oneHour){
                return "";
            }

            //[ 1day , 2day)
            if(iterationTime >= 1 && iterationTime < 2){
                return YesterdayGapFormat;
            }
            //[ 2day , 3day)
            else if(timeIndex == 4 && iterationTime >= 2 && iterationTime < 3){
                return DayBeforeYesterdayGapFormat;
            }
            //[ 3day , 1month)
            else{
                return DayAndMonthGapFormat;
            }
        }
        //[ 1month, 1year)
        else if(timeIndex == 5){

            //continue messages should not show time bar
            if(gapTimeMillis <= oneDay){
                return "";
            }

            return DayAndMonthGapFormat;

            //[ 1year , +8)
        }else if(timeIndex == 6){

            //continue messages should not show time bar
            if(gapTimeMillis <= oneDay){
                return "";
            }

            return YearGapFormat;
        }


        return "";
    }

    public static String getLastTwoDayTimeString(long time){

        SimpleDateFormat sdf = new SimpleDateFormat(MessageClassTimeFormat);
        Date date = new Date(time - 2*oneDay);
        return sdf.format(date);
    }

    private static Date getStartTime() {
        Calendar todayStart = Calendar.getInstance();
        todayStart.set(Calendar.HOUR_OF_DAY, 0);
        todayStart.set(Calendar.MINUTE, 0);
        todayStart.set(Calendar.SECOND, 0);
        todayStart.set(Calendar.MILLISECOND, 0);
        return todayStart.getTime();
    }

    private static Date getEndTime() {
        Calendar todayEnd = Calendar.getInstance();
        todayEnd.set(Calendar.HOUR_OF_DAY, 23);
        todayEnd.set(Calendar.MINUTE, 59);
        todayEnd.set(Calendar.SECOND, 59);
        todayEnd.set(Calendar.MILLISECOND, 999);
        return todayEnd.getTime();
    }


}
