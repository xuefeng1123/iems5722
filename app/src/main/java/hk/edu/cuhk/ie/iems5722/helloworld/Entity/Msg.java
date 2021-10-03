package hk.edu.cuhk.ie.iems5722.helloworld.Entity;

public class Msg {
    public User from;
    public String to;
    public String context;
    public String time;

    public Msg(User from, String to, String context, String time){
        this.from = from;
        this.to = to;
        this.context = context;
        this.time = time;
    }

}
