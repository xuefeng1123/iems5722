package hk.edu.cuhk.ie.iems5722.a1_1155169095.Entity;

import com.alibaba.fastjson.annotation.JSONField;

import hk.edu.cuhk.ie.iems5722.a1_1155169095.R;

public class Chatroom {

    @JSONField(name = "id")
    public int id;

    @JSONField(name = "name")
    public String name;

    public int page = 0;

    public int pic = R.mipmap.chatroom_pic_0;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public Chatroom(){

    }

    public Chatroom(int id, String name){
        this.id = id;
        this.name = name;
    }
}

