package com.example.dllo.phonebook.sms;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dllo.phonebook.R;
import com.example.dllo.phonebook.sms.bean.SmsData;

import java.util.ArrayList;

/**
 * Created by dllo on 16/5/5.
 */
public class MsgAdapter extends RecyclerView.Adapter {

    private static final int REC = 2;
    private static final int SEND = 1;
    private ArrayList<SmsData> datas;
    private Context context;


    public void setDatas(ArrayList<SmsData> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    public MsgAdapter(Context context) {
        this.context = context;
    }

    public void addData(SmsData s){
        datas.add(s);
        notifyItemInserted(datas.size());
    }

    //该方法返回指定position行布局的类型
    //返回当前的行布局里面的东西
    @Override
    public int getItemViewType(int position) {
        Log.d("MsgAdapter", "datas.get(position).getType():" + datas.get(position).getType());
        return datas.get(position).getType();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;

        switch (viewType) {
            case REC:
                View itemView = LayoutInflater.from(context)
                        .inflate(R.layout.item_msg_rec, parent, false);
                holder = new RecHolder(itemView);
                break;
            default:

                View item = LayoutInflater.from(context)
                        .inflate(R.layout.item_msg_send, parent, false);
                holder = new SendHolder(item);
                break;
        }


        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //根据position拿到对应的viewType
        int viewType = getItemViewType(position);
        switch (viewType) {
            case REC:
                //类型强转
                RecHolder holder1 = (RecHolder) holder;
                //设置数据
                holder1.textView.setText(datas.get(position).getBody());
                break;
            case SEND:
                SendHolder holder2 = (SendHolder) holder;
                holder2.textView.setText(datas.get(position).getBody());
                break;
        }
    }

    @Override
    public int getItemCount() {
        return datas != null && datas.size() > 0 ? datas.size() : 0;
    }

    class SendHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public SendHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tvSendMsg);
        }
    }

    class RecHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public RecHolder(View itemView) {
            super(itemView);

            textView = (TextView) itemView.findViewById(R.id.tvRecMsg);
        }


    }

}
