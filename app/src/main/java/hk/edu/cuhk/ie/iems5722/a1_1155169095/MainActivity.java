package hk.edu.cuhk.ie.iems5722.a1_1155169095;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import hk.edu.cuhk.ie.iems5722.a1_1155169095.Activity.DefaultRoomActivity;
import hk.edu.cuhk.ie.iems5722.a1_1155169095.Adapter.ChatroomAdapter;
import hk.edu.cuhk.ie.iems5722.a1_1155169095.Entity.Chatroom;
import hk.edu.cuhk.ie.iems5722.a1_1155169095.Entity.User;
import hk.edu.cuhk.ie.iems5722.a1_1155169095.Network.Client;

public class MainActivity extends AppCompatActivity {
    public static String currRoom = "Default Chat Room";

    public static User currUser = new User("1155169095", "Xuefeng", R.mipmap.user_pic_0);

    private volatile List<Chatroom> chatroomList = new ArrayList<Chatroom>();

    private ListView chatroomListView;
    private ChatroomAdapter chatroomAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button openDefaultRoomBtn = (Button)findViewById(R.id.default_chatroom);

        openDefaultRoomBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DefaultRoomActivity.class);
                intent.putExtra("chatroomID", 2);
                intent.putExtra("chatroomName", "hhh");
                startActivity(intent);
            }
        });

//        mockData();

        Client.getRequest(this);

        chatroomListView = findViewById(R.id.chatroom_list);
        chatroomAdapter = new ChatroomAdapter(MainActivity.this, R.layout.chatroom_item, chatroomList);
        chatroomListView.setAdapter(chatroomAdapter);

        chatroomListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Chatroom chatroom = chatroomList.get(i);
                Intent intent = new Intent(MainActivity.this, DefaultRoomActivity.class);
                intent.putExtra("chatroomID", chatroom.id);
                intent.putExtra("chatroomName", chatroom.name);
                startActivity(intent);
            }
        });
    }

    public void updateChatroomList(List<Chatroom> chatrooms){
        chatroomList.clear();
        chatroomList.addAll(chatrooms);
        chatroomAdapter.notifyDataSetChanged();
    }

    private void mockData(){
        Chatroom c1 = new Chatroom(1, "IEMS 5722");
        Chatroom c2 = new Chatroom(2, "IEMS 5710");

        chatroomList.add(c1);
        chatroomList.add(c2);
    }

}