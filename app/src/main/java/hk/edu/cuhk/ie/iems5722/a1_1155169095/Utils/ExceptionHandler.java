package hk.edu.cuhk.ie.iems5722.a1_1155169095.Utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.Toast;

import hk.edu.cuhk.ie.iems5722.a1_1155169095.R;

public class ExceptionHandler {
    public static void handleIllegalChatroomIDException(Context context){
        ((Activity)context).findViewById(android.R.id.content).post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, "The chat room has been disbanded", Toast.LENGTH_LONG).show();
            }
        });
    }

    public static void handleGetInfoException(Context context){
        ((Activity)context).findViewById(android.R.id.content).post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, "Network Error!", Toast.LENGTH_LONG).show();
            }
        });
    }
}
