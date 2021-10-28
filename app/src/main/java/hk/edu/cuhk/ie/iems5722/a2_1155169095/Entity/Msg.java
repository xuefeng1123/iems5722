package hk.edu.cuhk.ie.iems5722.a2_1155169095.Entity;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

public class Msg {
    @JSONField(name = "from")
    public User from;

    @JSONField(name = "to")
    public String to;

    @JSONField(name = "message")
    public String context;

    @JSONField(name = "message_time", format = "YYYY-MM-dd HH:mm")
    public Date date;

    public long timeMillis;


    public Msg(){

    }

    public void setTimeMillis(long timeMillis) {
        this.timeMillis = timeMillis;
    }


    public Msg(User from, String to, String context, long timeMillis){
        this.from = from;
        this.to = to;
        this.context = context;

        this.timeMillis = timeMillis;
    }

}
