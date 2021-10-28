package hk.edu.cuhk.ie.iems5722.a2_1155169095;

public class Consts {
    //format with message
    public static String MessageTimeFormat = "HH:mm";

    //format for class Msg time record
    public static String MessageClassTimeFormat = "yyyy/MM/dd  HH:mm:ss";

    //format when the gap time is more than 5min but less than 1day
    public static String MinAndHourGapFormat = "HH:mm";

    //format when the gap time is more than 3days
    public static String YesterdayGapFormat = "'Yesterday' HH:mm";

    //format when the gap time is more than 3days
    public static String DayBeforeYesterdayGapFormat = "'Two days ago' HH:mm";

    //format when the gap time is more than 3days
    public static String DayAndMonthGapFormat = "MM/dd  HH:mm";

    public static String YearGapFormat = "yyyy/MM/dd  HH:mm";

    //The length of the time unit used to convert month, day and year
    // 换算月日年的时间单位长度           秒 ， 分， 时， 日， 月， 年
    public static int[] TimeSlice = {1000, 60, 60, 24, 30, 12};

    //format of message send time on server response
    public static String NetworkMessageTimeFormat = "yyyy-MM-dd HH:mm";

}
