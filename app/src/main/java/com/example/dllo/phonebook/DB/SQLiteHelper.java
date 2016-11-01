package com.example.dllo.phonebook.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by dllo on 16/4/27.
 */
public class SQLiteHelper extends SQLiteOpenHelper {

    private String str = " create table " + DBValues.TABLE_NAME +
            "(" + DBValues.COLUMN_ID + " integer primary key autoincrement ," + DBValues.
            COLUMN_NAME + " text ," + DBValues.COLUMN_NUMBER + " text ," +
            DBValues.COLUMN_TIME + " text " + ")";

    private String str1 = " create table " + DBValuesContact.TABLE_NAME +
            "(" + DBValuesContact.COLUMN_ID + " integer primary key autoincrement ," +
            DBValuesContact.COLUMN_NAME + " text ," + DBValuesContact.COLUMN_NUMBER +
            " text ," + DBValuesContact.COLUMN_IMG + " BLOB " + ")";

    public SQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(str);
        db.execSQL(str1);
    }

    //数据库版本的升级方法
    //当应用发布了之后还需要改动数据库
    //就可以将改动的代码放到该方法内执行
    //只要新的版本号比之前的大,就会进入该方法
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
