package hk.edu.cuhk.ie.iems5722.a2_1155169095.Network;

import com.alibaba.fastjson.annotation.JSONField;

public class PostMsg {
    @JSONField(name = "chatroom_id")
    public int chatroomID;

    @JSONField(name = "user_id")
    public String user_id;

    @JSONField(name = "name")
    public String name;

    @JSONField(name = "message")
    public String message;
}
