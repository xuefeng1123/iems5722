package hk.edu.cuhk.ie.iems5722.helloworld.Entity;

public class User {
    public String id;
    public String name;
    public int pic; //the address of user's pic

    public User(String id, String name, int pic){
        this.id = id;
        this.name = name;
        this.pic = pic;
    }
}