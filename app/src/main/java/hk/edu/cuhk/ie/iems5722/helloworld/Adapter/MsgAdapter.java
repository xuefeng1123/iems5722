package hk.edu.cuhk.ie.iems5722.helloworld.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import hk.edu.cuhk.ie.iems5722.helloworld.Entity.Msg;
import hk.edu.cuhk.ie.iems5722.helloworld.MainActivity;
import hk.edu.cuhk.ie.iems5722.helloworld.R;

public class MsgAdapter extends ArrayAdapter<Msg> {
    class ViewHolder{
        LinearLayout sendLayout;
        LinearLayout receiveLayout;
        TextView sendContext;
        TextView sendTime;
        TextView receiveContext;
        ImageView sendPic;
        ImageView receivePic;
        TextView receiveTime;
    }

    public int resourceId;

    public MsgAdapter(Context context, int textViewResourceId, List<Msg> msgs){
        super(context, textViewResourceId, msgs);
        this.resourceId = textViewResourceId;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        Msg msg = getItem(position);

        View view;
        ViewHolder viewHolder;

        if(convertView == null){
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHolder = new ViewHolder();
            viewHolder.sendLayout = (LinearLayout)view.findViewById(R.id.ll_send);
            viewHolder.sendContext = (TextView) view.findViewById(R.id.send_text);
            viewHolder.sendPic = (ImageView) view.findViewById(R.id.send_text_user);
            viewHolder.sendTime = (TextView) view.findViewById(R.id.send_time);

            viewHolder.receiveLayout = (LinearLayout)view.findViewById(R.id.ll_receive);
            viewHolder.receiveContext = (TextView) view.findViewById(R.id.receive_text);
            viewHolder.receivePic = (ImageView) view.findViewById(R.id.receive_text_user);
            viewHolder.receiveTime = (TextView) view.findViewById(R.id.receive_time);

            view.setTag(viewHolder);
        }else{
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }

        //if the send person is exactly the current user
        if(msg.from.id.equals(MainActivity.currUser.id)){
            viewHolder.sendLayout.setVisibility(View.VISIBLE);
            viewHolder.receiveLayout.setVisibility(View.GONE);
            viewHolder.sendContext.setText(msg.context);
            viewHolder.sendPic.setImageResource(msg.from.pic);
            viewHolder.sendTime.setText(msg.time);
        }
        else{ //if the send person are others
            viewHolder.sendLayout.setVisibility(View.GONE);
            viewHolder.receiveLayout.setVisibility(View.VISIBLE);
            viewHolder.receiveContext.setText(msg.context);
            viewHolder.receivePic.setImageResource(msg.from.pic);
            viewHolder.receiveTime.setText(msg.time);
        }

        return view;
    }
}
