package hk.edu.cuhk.ie.iems5722.helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import hk.edu.cuhk.ie.iems5722.helloworld.Adapter.MsgAdapter;
import hk.edu.cuhk.ie.iems5722.helloworld.Entity.Msg;
import hk.edu.cuhk.ie.iems5722.helloworld.Entity.User;

public class DefaultRoomActivity extends AppCompatActivity {

    private List<User> users = new ArrayList<User>();

    private List<Msg> msgList = new ArrayList<Msg>();

    private EditText input;
    private Button send;
    private ListView msgListView;
    private MsgAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default_room);
        Intent intent = getIntent();

        mockData();


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
                    Msg msg = new Msg(MainActivity.currUser, MainActivity.currRoom, inputText);
                    msgList.add(msg);
                    adapter.notifyDataSetChanged();
                    //scroll to bottom automatically when new msg comes in
                    msgListView.setSelection(msgList.size());
                    input.setText("");
                }
            }
        });
    }


    public void mockData(){
        User user1 = new User("1155169011TuNina", "Tu", R.mipmap.user_pic_1);
        User user2 = new User("1155169022LuoShaojie", "Luo", R.mipmap.user_pic_2);

        users.add(user1);
        users.add(user2);

        Msg msg_0_0 = new Msg(MainActivity.currUser, MainActivity.currRoom, "Hello everyone!");
        Msg msg_0_1 = new Msg(MainActivity.currUser, MainActivity.currRoom, "Welcome to my chat room!");
        Msg msg_1_0 = new Msg(user1, MainActivity.currRoom, "Hello Xinyi. I am Nina!");
        Msg msg_2_0 = new Msg(user2, MainActivity.currRoom, "Hi Xinyi. Here is Shaojie!");

        msgList.add(msg_0_0);
        msgList.add(msg_0_1);
        msgList.add(msg_1_0);
        msgList.add(msg_2_0);

    }
}