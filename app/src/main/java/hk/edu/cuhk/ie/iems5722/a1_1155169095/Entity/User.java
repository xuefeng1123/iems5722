package hk.edu.cuhk.ie.iems5722.a1_1155169095.Entity;

import com.alibaba.fastjson.annotation.JSONField;

public class User {

    @JSONField(name = "user_id")
    public String id;

    @JSONField(name = "name")
    public String name;
    public int pic; //the address of user's pic

    public User(){

    }

    public User(String id, String name, int pic){
        this.id = id;
        this.name = name;
        this.pic = pic;
    }
}
