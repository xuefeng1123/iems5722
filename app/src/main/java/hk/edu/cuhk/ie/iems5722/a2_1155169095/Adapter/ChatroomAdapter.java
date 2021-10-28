package hk.edu.cuhk.ie.iems5722.a2_1155169095.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import hk.edu.cuhk.ie.iems5722.a2_1155169095.Entity.Chatroom;
import hk.edu.cuhk.ie.iems5722.a2_1155169095.R;

public class ChatroomAdapter extends ArrayAdapter<Chatroom> {
    class ViewHolder{
        TextView chatroomName;
        ImageView chatroomPic;
    }
    public int resourceId;

    public ChatroomAdapter(Context context, int textViewResourceId, List<Chatroom> chatrooms){
        super(context, textViewResourceId, chatrooms);
        this.resourceId = textViewResourceId;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        Chatroom chatroom = getItem(position);
        View view;
        ChatroomAdapter.ViewHolder viewHolder;

        if(convertView == null){
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHolder = new ChatroomAdapter.ViewHolder();

            viewHolder.chatroomName = (TextView) view.findViewById(R.id.chatroom_name);
            viewHolder.chatroomPic = (ImageView) view.findViewById(R.id.chatroom_pic);

            view.setTag(viewHolder);
        }else{
            view = convertView;
            viewHolder = (ChatroomAdapter.ViewHolder) view.getTag();
        }

        viewHolder.chatroomPic.setImageResource(chatroom.pic);
        viewHolder.chatroomName.setText(chatroom.name);

        return view;
    }
}
