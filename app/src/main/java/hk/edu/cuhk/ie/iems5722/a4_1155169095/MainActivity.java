package hk.edu.cuhk.ie.iems5722.a4_1155169095;

import static hk.edu.cuhk.ie.iems5722.a4_1155169095.APIString.SEND_TOKEN;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.List;

import hk.edu.cuhk.ie.iems5722.a4_1155169095.Activity.DefaultRoomActivity;
import hk.edu.cuhk.ie.iems5722.a4_1155169095.Adapter.ChatroomAdapter;
import hk.edu.cuhk.ie.iems5722.a4_1155169095.Entity.Chatroom;
import hk.edu.cuhk.ie.iems5722.a4_1155169095.Entity.User;
import hk.edu.cuhk.ie.iems5722.a4_1155169095.Network.Client;

public class MainActivity extends AppCompatActivity {
    public static String currRoom = "Default Chat Room";

//    public static User currUser = new User("1155169095", "Xuefeng", R.mipmap.user_pic_0);
//    public static User currUser = new User("111", "MyHuaweiM6", R.mipmap.user_pic_0);
    public static User currUser = new User("112", "Pixel22", R.mipmap.user_pic_0);

    private volatile List<Chatroom> chatroomList = new ArrayList<Chatroom>();

    private ListView chatroomListView;
    private ChatroomAdapter chatroomAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar =  getSupportActionBar();
        actionBar.setTitle(R.string.chat_list_page_title);

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

        getFCMToken();
    }
    private static final String TAG = "MainActivity";
    public void getFCMToken(){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create channel to show notifications.
            String channelId  = getString(R.string.default_notification_channel_id);
            String channelName = getString(R.string.default_notification_channel_name);
            NotificationManager notificationManager =
                    getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(new NotificationChannel(channelId,
                    channelName, NotificationManager.IMPORTANCE_LOW));
        }


        // [START log_reg_token]
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();
                        Client.postToken(MainActivity.this, SEND_TOKEN, Integer.parseInt(MainActivity.currUser.id), token);
                        // Log and toast
                        String msg = getString(R.string.msg_token_fmt, token);
                        Log.d(TAG, msg);
                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
        // [END log_reg_token]
    }



    public void updateChatroomList(List<Chatroom> chatrooms){
        chatroomList.clear();
        chatroomList.addAll(chatrooms);
        chatroomAdapter.notifyDataSetChanged();
        subscirbeTopics(chatrooms);
    }

    public void subscirbeTopics(List<Chatroom> chatrooms){
        for (Chatroom chatroom:
             chatrooms) {
            Log.d(TAG, "Subscribing to chatroom: " + chatroom.name);
            FirebaseMessaging.getInstance().subscribeToTopic(chatroom.id + "")
                    .addOnCompleteListener(task -> {
                        String msg = getString(R.string.msg_subscribed);
                        if (!task.isSuccessful()) {
                            msg = getString(R.string.msg_subscribe_failed);
                        }
                        Log.d(TAG, msg);
                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    });
        }

    }

    private void mockData(){
        Chatroom c1 = new Chatroom(1, "IEMS 5722");
        Chatroom c2 = new Chatroom(2, "IEMS 5710");

        chatroomList.add(c1);
        chatroomList.add(c2);
    }

}