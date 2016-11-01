package com.example.dllo.phonebook.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.dllo.phonebook.callrecords.CallrecordData;
import com.example.dllo.phonebook.contact.StudentBade;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by dllo on 16/4/27.
 */
public class DBTools {
    private SQLiteDatabase database;
    private Context context;
    private SQLiteHelper helper;


    public DBTools(Context context) {
        this.context = context;
        //通过helper 获得一个可读可写的数据库
        helper = new SQLiteHelper(context, DBValues.DB_NAME, null, 1);
        database = helper.getWritableDatabase();

    }

    //增加通讯录的方法
    public void insertDB(CallrecordData data) {
        String number = FormatNum.Format(data.getNumber());

        if (hasThisCallrecord(data.getTime()) == false) {

            ContentValues contentValues = new ContentValues();
            contentValues.put(DBValues.COLUMN_NAME, data.getName());
            contentValues.put(DBValues.COLUMN_NUMBER, number);
            contentValues.put(DBValues.COLUMN_TIME, data.getTime());

            database.insert(DBValues.TABLE_NAME, null, contentValues);
        }
    }

    //增加联系人的方法
    public void insertDBContact(StudentBade bade) {
        String fumber = FormatNum.Format(bade.getTv_number());

        if (hasThisContact(bade.getTv_name()) == false) {
            ContentValues values = new ContentValues();
            //bitmop格式转换为byte
            ByteArrayOutputStream stream = new ByteArrayOutputStream();

            //格式化转换格式,100 图片质量 ,读流

            bade.getImgId().compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] pic = stream.toByteArray();
            pic = stream.toByteArray();
            values.put(DBValuesContact.COLUMN_NAME, bade.getTv_name());
            values.put(DBValuesContact.COLUMN_NUMBER, fumber);
            values.put(DBValuesContact.COLUMN_IMG, pic);

            database.insert(DBValuesContact.TABLE_NAME, null, values);

            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //查询通讯录的方法
    public ArrayList<CallrecordData> queryDBAll() {
        ArrayList<CallrecordData> datas = new ArrayList<>();
        Cursor cursor = database.query(DBValues.TABLE_NAME, null,
                null, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex
                        (DBValues.COLUMN_NAME));
                String number = cursor.getString(cursor.getColumnIndex
                        (DBValues.COLUMN_NUMBER));
                String time = cursor.getString(cursor.getColumnIndex
                        (DBValues.COLUMN_TIME));
                CallrecordData data = new CallrecordData();
                data.setName(name);
                data.setNumber(number);
                data.setTime(time);
                datas.add(data);
            }
            cursor.close();
        }

        return datas;
    }

    //查询联系人的方法
    public ArrayList<StudentBade> queryAll() {
        ArrayList<StudentBade> bades = new ArrayList<>();
        Cursor cursor = database.query(DBValuesContact.TABLE_NAME, null, null, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex
                        (DBValuesContact.COLUMN_NAME));
                String number = cursor.getString(cursor.getColumnIndex
                        (DBValuesContact.COLUMN_NUMBER));
                byte[] icon = cursor.getBlob(cursor.getColumnIndex
                        (DBValuesContact.COLUMN_IMG));
                //将byte数组转换成bitmap数组
                //三参:
                Bitmap bitmap = BitmapFactory.decodeByteArray(icon, 0, icon.length);

                StudentBade bade = new StudentBade();
                bade.setTv_name(name);
                bade.setTv_number(number);
                bade.setImgId(bitmap);
                bades.add(bade);
            }
            if (cursor != null){
                cursor.close();
            }
        }
        return bades;
    }

    //根据电话号码,查询该人的姓名
    public String getNameFromNum(String number) {

        String nu = FormatNum.Format(number);
        Log.d("DBTools------>", nu);
        String name = nu;

        Cursor cursor = database.rawQuery(" select * from "
                + DBValuesContact.TABLE_NAME + " where " + DBValuesContact.COLUMN_NUMBER
                + " = ? ", new String[]{nu});

        if (cursor != null) {
            while (cursor.moveToNext()) {
                name = cursor.getString(cursor.getColumnIndex(DBValuesContact.COLUMN_NAME));

            }
            cursor.close();
        }
        return name;

    }

    //删除多余的联系人
    public void deleteDB() {

        database.delete(DBValues.TABLE_NAME, null, null);

    }

    //删除多余的联系人
    public void deleteDbAll() {
        database.delete(DBValuesContact.TABLE_NAME, null, null);
    }

    //判断是否有相同的通话记录对象,有则不进行重复添加,无则添加
    public boolean hasThisCallrecord(String time) {
        boolean hasTheCallrecord = false;
        Cursor cursor = database.rawQuery(" select * from " + DBValues.TABLE_NAME + " where " + DBValues.COLUMN_TIME + " = ? ", new String[]{time});
        if (cursor.getCount() > 0) {
            hasTheCallrecord = true;
        }
        cursor.close();
        return hasTheCallrecord;
    }

    //判断是否有重复的联系人添加到数据库中
    public boolean hasThisContact(String name) {
        boolean hasTheContact = false;
        Cursor cursor = database.rawQuery(" select * from " + DBValuesContact.TABLE_NAME + " where " + DBValuesContact.COLUMN_NAME + " = ? ", new String[]{name});
        if (cursor.getCount() > 0) {
            hasTheContact = true;
        }
        cursor.close();
        return hasTheContact;
    }


}
