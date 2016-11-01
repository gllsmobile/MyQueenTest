package com.example.dllo.phonebook.callrecords;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dllo.phonebook.R;

import java.util.ArrayList;

/**
 * Created by dllo on 16/4/20.
 */
public class CallrecordBaseAdapter extends BaseAdapter {
    private ArrayList<CallrecordData> datas;
    private Context context;

    public CallrecordBaseAdapter(Context context) {
        this.context = context;
    }


    public void setDatas(ArrayList<CallrecordData> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return datas != null && datas.size() > 0 ? datas.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return datas == null ? null : datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_mainlist, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.name.setText(datas.get(position).getName());
        viewHolder.number.setText(datas.get(position).getNumber());
        viewHolder.time.setText(datas.get(position).getTime());
        viewHolder.checkBox.setChecked(datas.get(position).isCheck());
        viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox checkBox = (CheckBox) v;
                datas.get(position).setCheck(checkBox.isChecked());
            }
        });
        return convertView;
    }

    //增加删除的方法
    public void deleData(int position) {
        if (position > datas.size() || datas.size() <= 0) {

            Toast.makeText(context, "你点错了", Toast.LENGTH_SHORT).show();

        } else {

            datas.remove(position);
        }
        notifyDataSetChanged();
    }

//增加数据的方法
    public void addData(CallrecordData data, int i) {
        if (i > datas.size()) {
            datas.add(data);

        } else {
            datas.add(i, data);
            notifyDataSetChanged();
        }
    }

    class ViewHolder {
        ImageView imageView;
        TextView name;
        TextView number;
        TextView time;
        Button btn_Determine;
        CheckBox checkBox;


        public ViewHolder(View itemView) {
            imageView = (ImageView) itemView.findViewWithTag(R.id.itemImage);
            name = (TextView) itemView.findViewById(R.id.name);
            number = (TextView) itemView.findViewById(R.id.number);
            time = (TextView) itemView.findViewById(R.id.time);
            btn_Determine = (Button) itemView.findViewById(R.id.btn_Determine);
            checkBox = (CheckBox) itemView.findViewById(R.id.checkBox);


        }
    }
}
