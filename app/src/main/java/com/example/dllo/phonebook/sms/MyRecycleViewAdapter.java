package com.example.dllo.phonebook.sms;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dllo.phonebook.R;
import com.example.dllo.phonebook.sms.bean.SmsData;

import java.util.ArrayList;

/**
 * Created by dllo on 16/4/29.
 */

public class MyRecycleViewAdapter extends RecyclerView.Adapter<MyRecycleViewAdapter.ItemSms> {

    private ArrayList<SmsData> datas;
    private Context context;
    private MyRvOnClickListener myRvOnClickListener;

    public MyRecycleViewAdapter(Context context) {
        this.context = context;
    }

    //实现点击事件
    public void setMyRvOnClickListener(MyRvOnClickListener myRvOnClickListener) {
        this.myRvOnClickListener = myRvOnClickListener;
    }

    public void setDatas(ArrayList<SmsData> datas) {
        this.datas = datas;
        notifyDataSetChanged();

    }


    //绑定布局
    @Override
    public ItemSms onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.itme_sms, parent, false);

        ItemSms holder = new ItemSms(view);
        return holder;
    }

    //实现每一行操作
    @Override
    public void onBindViewHolder(final ItemSms holder, int position) {
        holder.name.setText(datas.get(position).getName());
        holder.body.setText(datas.get(position).getBody());
        holder.time.setText(datas.get(position).getDate());

        //实现点击事件
        if (myRvOnClickListener != null) {
            holder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int position = holder.getLayoutPosition();
                    myRvOnClickListener.myOnClick(position);
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return datas != null && datas.size() > 0 ? datas.size() : 0;
    }

    class ItemSms extends RecyclerView.ViewHolder {
        TextView name, body, time;
        LinearLayout layout;

        public ItemSms(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.nameRVItem);
            body = (TextView) itemView.findViewById(R.id.teleRVItem);
            time = (TextView) itemView.findViewById(R.id.timeRVItem);
            layout = (LinearLayout) itemView.findViewById(R.id.myItemLayout);
        }
    }

}
