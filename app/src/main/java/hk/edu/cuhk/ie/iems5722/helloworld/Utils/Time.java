package hk.edu.cuhk.ie.iems5722.helloworld.Utils;

import static hk.edu.cuhk.ie.iems5722.helloworld.Consts.DayAndMonthGapFormat;
import static hk.edu.cuhk.ie.iems5722.helloworld.Consts.DayBeforeYesterdayGapFormat;
import static hk.edu.cuhk.ie.iems5722.helloworld.Consts.MessageClassTimeFormat;
import static hk.edu.cuhk.ie.iems5722.helloworld.Consts.MinAndHourGapFormat;
import static hk.edu.cuhk.ie.iems5722.helloworld.Consts.TimeSlice;
import static hk.edu.cuhk.ie.iems5722.helloworld.Consts.YearGapFormat;
import static hk.edu.cuhk.ie.iems5722.helloworld.Consts.YesterdayGapFormat;

import android.util.Log;

import java.text.SimpleDateFormat;
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

        long gapTimeMillis = date.getTime() - lastDate.getTime();
        long currTimeMillis = currDate.getTime() - date.getTime();


        long iterationTime = currTimeMillis;
        int timeIndex = 0;
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

}
