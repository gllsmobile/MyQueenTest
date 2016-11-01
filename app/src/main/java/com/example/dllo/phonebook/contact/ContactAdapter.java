package com.example.dllo.phonebook.contact;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dllo.phonebook.R;

import java.util.ArrayList;

/**
 * Created by dllo on 16/4/25.
 */
public class ContactAdapter extends BaseAdapter {
    private ArrayList<StudentBade> datas;
    private Context context;

    public ContactAdapter(Context context) {
        this.context = context;
    }

    public void setDatas(ArrayList<StudentBade> datas) {
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
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;

        if (convertView == null) {

            convertView = LayoutInflater.from(context).inflate(R.layout.item_contact, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);

        } else {

            viewHolder = (ViewHolder) convertView.getTag();

        }
        Bitmap bitmap = getRoundImageBitmap(datas.get(position).getImgId());
        viewHolder.imgId.setImageBitmap(bitmap);
        viewHolder.tv_name.setText(datas.get(position).getTv_name());
        viewHolder.tv_number.setText(datas.get(position).getTv_number());

        return convertView;
    }


    //切圆方法
    private Bitmap getRoundImageBitmap(Bitmap bitmap) {
        //生成一张空白的和bitmap宽高相同的图
        Bitmap outBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);

        //把图放到画布上  一会绘制用
        Canvas canvas = new Canvas(outBitmap);

        Paint paint = new Paint();
        paint.setColor(Color.BLACK);

        //绘制一个底层的圆
        canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2, bitmap.getWidth() / 2, paint);

        //设置画笔的叠放风格,SRC_IN (显示前景的交集部分)
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        //再这上边绘制一种目标bitmap
        Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        //
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return outBitmap;


    }
    //内部类用来绑定布局
   public class ViewHolder {

        TextView tv_name;
        TextView tv_number;
        ImageView imgId;

        public ViewHolder(View view) {

            tv_name = (TextView) view.findViewById(R.id.tv_name);
            tv_number = (TextView) view.findViewById(R.id.tv_number);
            imgId = (ImageView) view.findViewById(R.id.item_imgId);
        }
    }
}
