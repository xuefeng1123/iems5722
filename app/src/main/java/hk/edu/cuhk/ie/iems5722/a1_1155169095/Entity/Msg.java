package hk.edu.cuhk.ie.iems5722.a1_1155169095.Entity;

public class Msg {
    public User from;
    public String to;
    public String context;

    public long timeMillis;

    public Msg(User from, String to, String context, long timeMillis){
        this.from = from;
        this.to = to;
        this.context = context;

        this.timeMillis = timeMillis;
    }

}
