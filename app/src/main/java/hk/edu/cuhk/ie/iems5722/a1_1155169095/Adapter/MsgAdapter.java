package hk.edu.cuhk.ie.iems5722.a1_1155169095.Adapter;

import static hk.edu.cuhk.ie.iems5722.a1_1155169095.Consts.MessageTimeFormat;
import static hk.edu.cuhk.ie.iems5722.a1_1155169095.Utils.Time.getChatStartTime;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import hk.edu.cuhk.ie.iems5722.a1_1155169095.Entity.Msg;
import hk.edu.cuhk.ie.iems5722.a1_1155169095.Utils.Time;
import hk.edu.cuhk.ie.iems5722.a1_1155169095.MainActivity;
import hk.edu.cuhk.ie.iems5722.a1_1155169095.R;

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
        LinearLayout timeBarLayout;
        TextView timeBar;
        TextView receiveMsgSender;
    }

    public int resourceId;
    private Context mContext;

    public MsgAdapter(Context context, int textViewResourceId, List<Msg> msgs){
        super(context, textViewResourceId, msgs);
        this.resourceId = textViewResourceId;
        this.mContext = context;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        ListView msgListView = (ListView) parent;
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

            viewHolder.timeBarLayout = (LinearLayout) view.findViewById(R.id.time_bar_layout);
            viewHolder.timeBar = (TextView) view.findViewById(R.id.msg_time_bar);

            viewHolder.receiveMsgSender = (TextView) view.findViewById(R.id.receive_msg_sender);

            view.setTag(viewHolder);
        }else{
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }

        //checkTime
        //if the message is the first one
        if(position == 0){
            viewHolder.timeBarLayout.setVisibility(View.VISIBLE);
            //given a virtual time: last 2 days can make sure time bar can be shown
            String format = Time.getGapTimeFormat(msg.timeMillis - 2 * Time.oneDay, msg.timeMillis);
            viewHolder.timeBar.setText(Time.getChatStartTime(msg.timeMillis, format));
        }
        else{
            Msg lastMsg = getItem(position - 1);
            String format = Time.getGapTimeFormat(lastMsg.timeMillis, msg.timeMillis);
            if(format.length() == 0){
                viewHolder.timeBarLayout.setVisibility(View.GONE);
            }else{
                viewHolder.timeBarLayout.setVisibility(View.VISIBLE);
                viewHolder.timeBar.setText(Time.getChatStartTime(msg.timeMillis, format));
            }
        }

        String messageShowFormat = getChatStartTime(msg.timeMillis, MessageTimeFormat);

        //if the send person is exactly the current user
        if(msg.from.id.equals(MainActivity.currUser.id)){
            viewHolder.sendLayout.setVisibility(View.VISIBLE);
            viewHolder.receiveLayout.setVisibility(View.GONE);
            viewHolder.sendContext.setText(msg.context);
            viewHolder.sendPic.setImageResource(msg.from.pic);
            viewHolder.sendTime.setText(messageShowFormat);
        }
        else{ //if the send person are others
            viewHolder.sendLayout.setVisibility(View.GONE);
            viewHolder.receiveLayout.setVisibility(View.VISIBLE);
            viewHolder.receiveContext.setText(msg.context);
            viewHolder.receivePic.setImageResource(msg.from.pic);
            viewHolder.receiveTime.setText(messageShowFormat);
            viewHolder.receiveMsgSender.setText(msg.from.name);
        }
//        viewHolder.sendLayout.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//
//                view.getParent();
//                View itemView = convertView;
//                if (itemView==null)
//                    itemView = LayoutInflater.from(mContext).inflate(R.layout.msg_item, parent, false);
//
//                int start = msgListView.getFirstVisiblePosition();
//                int end = msgListView.getLastVisiblePosition();
//                for(int i = start; i <= end; i++){
//                    if(view.findViewById(R.id.ll_send).getVisibility() == View.VISIBLE){//send bubble
//                        int lightness = 240;
//                        if(start != end)
//                            lightness = 240*(1 - (end - i)/(start - end));
//                        int color = (255 & 0xff) << 24 | (0 & 0xff) << 16 | (lightness & 0xff) << 8 | (0 & 0xff);
//                        PorterDuffColorFilter colorFilter = new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_ATOP);
//                        view.findViewById(R.id.send_bubble).getBackground().setColorFilter(colorFilter);
//                        Log.i("0", "start = " + start + ", end = " + end + ", i = " + i);
//                    }
//                }
//                return false;
//            }
//        });
        return view;
    }
}
