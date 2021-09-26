package hk.edu.cuhk.ie.iems5722.helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import hk.edu.cuhk.ie.iems5722.helloworld.Entity.Msg;
import hk.edu.cuhk.ie.iems5722.helloworld.Entity.User;

public class MainActivity extends AppCompatActivity {
    public static String currRoom = "Default Chat Room";

    public static User currUser = new User("1155169095YangXinyi", "Yang", R.mipmap.user_pic_0);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button openDefaultRoomBtn = (Button)findViewById(R.id.default_chatroom);

        openDefaultRoomBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DefaultRoomActivity.class);
                startActivity(intent);
            }
        });
    }

}