package hk.edu.cuhk.ie.iems5722.a1_1155169095.Network;

import android.os.Build;
import android.util.Log;


import androidx.annotation.RequiresApi;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.util.List;

import hk.edu.cuhk.ie.iems5722.a1_1155169095.APIString;
import hk.edu.cuhk.ie.iems5722.a1_1155169095.Entity.Chatroom;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Client {
    final OkHttpClient client = new OkHttpClient();

    public void getRequest(){
        final Request request = new Request.Builder()
                .get()
                .url(APIString.GET_CHAT_ROOMS)
                .build();
        new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void run() {
                Response response = null;
                try{
                    response = client.newCall(request).execute();
                    if(response.isSuccessful()){
                        Log.i("response Succeed!", response.body().toString());
                        String res = response.body().string();
                        JSONObject responseJson = JSON.parseObject(res);
                        if(responseJson.get("status").equals("OK")){
                            String dataString = responseJson.getString("data");
                            List<Chatroom> rooms = JSON.parseArray(dataString, Chatroom.class);
                            Log.i("ddd", rooms.stream().count() + " ");
                        }else{
                            throw new IOException("Network GET CHAT ROOM ERROR");
                        }

                    }else{
                        throw new IOException("failed to receive response!" + response);
                    }
                }catch(IOException e){
                    Log.e("get Request Error", e.toString());
                }
            }
        }).start();
    }
}
