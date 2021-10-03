package hk.edu.cuhk.ie.iems5722.helloworld.Entity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Time {

    public static String MessageTimeFormat = "HH:mm:ss";

    public static String ChatTimeFormat = "yyyy/MM/dd  HH:mm:ss";

    public static String getMessageTime(){
        SimpleDateFormat sdf = new SimpleDateFormat(MessageTimeFormat);
        Date curDate = new Date(System.currentTimeMillis());
        return sdf.format(curDate);
    }

    public static String getChatStartTime(){
        SimpleDateFormat sdf = new SimpleDateFormat(ChatTimeFormat);
        Date curDate = new Date(System.currentTimeMillis());
        return sdf.format(curDate);
    }
}
