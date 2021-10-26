package hk.edu.cuhk.ie.iems5722.a1_1155169095.Entity;

import com.alibaba.fastjson.annotation.JSONField;

public class Chatroom {

    @JSONField(name = "id")
    public int id;

    @JSONField(name = "name")
    public String name;

    public int page = -1;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}

