package hk.edu.cuhk.ie.iems5722.a1_1155169095.Network;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.view.View;


import androidx.annotation.RequiresApi;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.util.List;

import hk.edu.cuhk.ie.iems5722.a1_1155169095.APIString;
import hk.edu.cuhk.ie.iems5722.a1_1155169095.Activity.DefaultRoomActivity;
import hk.edu.cuhk.ie.iems5722.a1_1155169095.Entity.Chatroom;
import hk.edu.cuhk.ie.iems5722.a1_1155169095.Entity.Msg;
import hk.edu.cuhk.ie.iems5722.a1_1155169095.MainActivity;
import hk.edu.cuhk.ie.iems5722.a1_1155169095.Utils.ExceptionHandler;
import hk.edu.cuhk.ie.iems5722.a1_1155169095.Utils.ExceptionUtil;
import hk.edu.cuhk.ie.iems5722.a1_1155169095.Utils.Time;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Client  {

    final static OkHttpClient client = new OkHttpClient();

    static public void getRequest(Context context){
        final Request request = new Request.Builder()
                .get()
                .url(APIString.GET_CHAT_ROOMS)
                .build();
        AsyncTask<Void, Void, String> asyncTask = new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {

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
                            ((Activity)context).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ((MainActivity) context).updateChatroomList(rooms);
                                }
                            });
                        }else{
                            throw new ExceptionUtil.GetInfoException();
                        }
                    }else{
                        throw new IOException("failed to receive response!" + response);
                    }
                }catch(IOException e){
                    Log.e("get Request Error", e.toString());
                }catch (ExceptionUtil.GetInfoException e){
                    Log.e("network error", e.toString());
                    ExceptionHandler.handleGetInfoException(context);
                }
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
            }
        };

        asyncTask.execute(null,null);
    }

    static public void getMessages(Context context, int chatroomID, int page){

        final Request request = new Request.Builder()
                .get()
                .url(APIString.GET_MESSAGES + "?chatroom_id=" + chatroomID + "&page=" + page)
                .build();


        AsyncTask<Void, Void, String> asyncTask = new AsyncTask<Void, Void, String>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            protected String doInBackground(Void... params) {
                Response response = null;
                try{
                    response = client.newCall(request).execute();
                    if(response.isSuccessful()){
                        Log.i("response Succeed!", response.body().toString());
                        String res = response.body().string();
                        JSONObject responseJson = JSON.parseObject(res);
                        if(responseJson.get("status").equals("OK")){
                            JSONObject dataJson = responseJson.getJSONObject("data");
                            String currentPage = dataJson.getString("current_page");
                            JSONArray messagesJson = dataJson.getJSONArray("messages");
                            int totalPages = dataJson.getIntValue("total_page");

                            for(int i = 0 ; i < messagesJson.size();i++){
                                JSONObject messageObject = messagesJson.getJSONObject(i);
                                String userName = messageObject.getString("name");
                                String userID = messageObject.getString("user_id");
                                messageObject.put("to", MainActivity.currUser.id);
                                messageObject.remove("name");
                                messageObject.remove("user_id");
                                JSONObject user = new JSONObject();
                                user.put("user_id", userID);
                                user.put("name", userName);
                                messageObject.put("from", user);
                            }
                            List<Msg> messages = JSONObject.parseArray(messagesJson.toJSONString(), Msg.class);
                            for (Msg message:
                                 messages) {
                                message.setTimeMillis(message.date.getTime());
                            }
                            ((Activity)context).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ((DefaultRoomActivity) context).updateMessagesPage(messages);
                                }
                            });
                        }else{
                            throw new ExceptionUtil.GetInfoException();
                        }
                    }else{
                        throw new IOException("failed to receive response!" + response);
                    }
                }catch(IOException e){
                    Log.e("get Request Error", e.toString());
                }catch (ExceptionUtil.GetInfoException e){
                    Log.e("network error", e.toString());
                    ExceptionHandler.handleGetInfoException(context);
                }
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
            }
        };

        asyncTask.execute(null,null);
    }



}
