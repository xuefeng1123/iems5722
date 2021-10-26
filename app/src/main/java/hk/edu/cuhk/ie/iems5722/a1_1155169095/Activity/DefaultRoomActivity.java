package hk.edu.cuhk.ie.iems5722.a1_1155169095.Activity;

import static hk.edu.cuhk.ie.iems5722.a1_1155169095.Consts.MessageClassTimeFormat;
import static hk.edu.cuhk.ie.iems5722.a1_1155169095.Utils.Time.getTimeMillis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import hk.edu.cuhk.ie.iems5722.a1_1155169095.Adapter.MsgAdapter;
import hk.edu.cuhk.ie.iems5722.a1_1155169095.Entity.Chatroom;
import hk.edu.cuhk.ie.iems5722.a1_1155169095.Entity.Msg;
import hk.edu.cuhk.ie.iems5722.a1_1155169095.Entity.User;
import hk.edu.cuhk.ie.iems5722.a1_1155169095.MainActivity;
import hk.edu.cuhk.ie.iems5722.a1_1155169095.Network.Client;
import hk.edu.cuhk.ie.iems5722.a1_1155169095.R;
import hk.edu.cuhk.ie.iems5722.a1_1155169095.Utils.ExceptionHandler;
import hk.edu.cuhk.ie.iems5722.a1_1155169095.Utils.ExceptionUtil;

public class DefaultRoomActivity extends AppCompatActivity {

    private volatile List<User> users = new ArrayList<User>();

    private volatile List<Msg> msgList = new ArrayList<Msg>();

    private EditText input;
    private Button send;
    private ListView msgListView;
    private MsgAdapter adapter;

    public Chatroom chatroom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default_room);
        Chatroom chatroom = new Chatroom();
        Intent intent = getIntent();
        try{
            int chatroomID = intent.getIntExtra("chatroomID", -1);
            if(chatroomID > 0){
                chatroom.id = chatroomID;
                chatroom.name = intent.getStringExtra("chatroomName");
                chatroom.page = 1;

                //mockData();


                input = (EditText) findViewById(R.id.input);
                send = (Button) findViewById(R.id.send);
                msgListView = (ListView) findViewById(R.id.msg_list);

                adapter = new MsgAdapter(DefaultRoomActivity.this, R.layout.msg_item, msgList);
                msgListView.setAdapter(adapter);



                send.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String inputText = input.getText().toString();
                        if(!"".equals(inputText)){
                            Msg msg = new Msg(MainActivity.currUser, MainActivity.currRoom, inputText, System.currentTimeMillis());
                            msgList.add(msg);
                            adapter.notifyDataSetChanged();
                            //scroll to bottom automatically when new msg comes in
                            msgListView.setSelection(msgList.size());
                            input.setText("");
                        }
                    }
                });


                //network request: get messages
                Client.getMessages(this, chatroom.id, chatroom.page);

                //Client.getMessages(this,2,1);
            }else{
                throw new ExceptionUtil.IllegalChatroomIDException();
            }

        }catch (ExceptionUtil.IllegalChatroomIDException e){
            ExceptionHandler.handleIllegalChatroomIDException(this);
            finish();
        }

    }

    public void updateMessagesPage(List<Msg> msgs){
        Log.i("get msg!", "get msg!!");
        //msgList = msgs;
        msgList.clear();
        msgList.addAll(msgs);
        adapter.notifyDataSetChanged();
    }


    private String testString = "qwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnm";
    private String testString2 = "qwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnm";

    public void mockData(){
        User user1 = new User("1155169011TuNina", "Tu", R.mipmap.user_pic_1);
        User user2 = new User("1155169022LuoShaojie", "Luo", R.mipmap.user_pic_2);

        users.add(user1);
        users.add(user2);

        Msg msg_0_0 = new Msg(MainActivity.currUser, MainActivity.currRoom, testString, getTimeMillis("2018/01/01  01:01:01", MessageClassTimeFormat));
        Msg msg_0_1 = new Msg(MainActivity.currUser, MainActivity.currRoom, testString2, getTimeMillis("2021/10/02  02:02:02", MessageClassTimeFormat));
        Msg msg_1_0 = new Msg(user1, MainActivity.currRoom, testString, getTimeMillis("2021/10/03  03:03:03", MessageClassTimeFormat));
        Msg msg_2_0 = new Msg(user2, MainActivity.currRoom, testString2, getTimeMillis("2021/10/03  13:30:04", MessageClassTimeFormat));

        msgList.add(msg_0_0);
        msgList.add(msg_0_1);
        msgList.add(msg_1_0);
        msgList.add(msg_2_0);

    }


}