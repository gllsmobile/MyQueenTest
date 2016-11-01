package com.example.dllo.phonebook;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.provider.Telephony;
import android.support.v4.app.ActivityCompat;
import android.widget.TextView;
import com.example.dllo.phonebook.DB.DBTools;
import com.example.dllo.phonebook.callrecords.CallrecordData;
import com.example.dllo.phonebook.contact.StudentBade;
import com.example.dllo.phonebook.sms.bean.SmsData;
import com.example.dllo.phonebook.sms.bean.SmsManager;
import com.example.dllo.phonebook.DB.FormatNum;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by dllo on 16/5/9.
 */
public class WelcomeActivity extends Activity {
    private TextView timeTv;
    private CountDownTimer countDownTimer;
    private SmsManager manager;
    private ArrayList<CallrecordData> adressBooks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_welcome);
        timeTv = (TextView) findViewById(R.id.timeTv);

        //第一个参数查几毫秒,第二个参数隔多少毫秒查一次
        countDownTimer = new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                String time = millisUntilFinished / 1000 + "s";
                timeTv.setText(time);
            }

            @Override
            public void onFinish() {
                Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }.start();

        MyThreadPool myThreadPool = MyThreadPool.getInstance();

        //增加通讯录数据库的线程

        myThreadPool.getThreadPoolExecutor().execute(new Runnable() {
            @Override
            public void run() {
                initAdressBook();

            }
        });
        //增加联系人的数据库方法
        myThreadPool.getThreadPoolExecutor().execute(new Runnable() {
            @Override
            public void run() {
                initContact();
            }
        });

        myThreadPool.getThreadPoolExecutor().execute(new Runnable() {
            @Override
            public void run() {
                initMsg();
            }
        });


    }
    //系统数据库获得短信
    private void initMsg() {
        Handler handler = null;
        DBTools tools2 = new DBTools(WelcomeActivity.this);
        manager = SmsManager.getInstance();
        ContentResolver resolver = WelcomeActivity.this.getContentResolver();
        Cursor cursor = resolver.query(Telephony.Sms.CONTENT_URI, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String name, number, date, body;
                int type;
                number = cursor.getString(cursor.getColumnIndex(Telephony.Sms.ADDRESS));
                date = cursor.getString(cursor.getColumnIndex(Telephony.Sms.DATE));
                body = cursor.getString(cursor.getColumnIndex(Telephony.Sms.BODY));
                type = cursor.getType(cursor.getColumnIndex(Telephony.Sms.TYPE));

                number = FormatNum.Format(number);

                name = tools2.getNameFromNum(number);

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                date = simpleDateFormat.format(new Date(Long.valueOf(date)));

                SmsData singleSms = new SmsData();
                singleSms.setName(name);
                singleSms.setBody(body);
                singleSms.setDate(date);
                singleSms.setNumber(number);
                singleSms.setType(type);

                manager.addSms(singleSms);
            }
            cursor.close();
        }
    }

    private void initContact() {

        DBTools tools1 = new DBTools(WelcomeActivity.this);
        //单个数据类
        ContentResolver resolver = WelcomeActivity.this.getContentResolver();
        Cursor cursor = resolver.query(ContactsContract.CommonDataKinds
                .Phone.CONTENT_URI, null, null, null, null);
        if (cursor != null) {
            StudentBade studentBade = null;
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex
                        (ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String number = cursor.getString(cursor.getColumnIndex
                        (ContactsContract.CommonDataKinds.Phone.NUMBER));
                //获取联系人头像,需要两个参数
                //联系人ID 和 头像ID
                Long contactID = cursor.getLong(cursor.getColumnIndex
                        (ContactsContract.CommonDataKinds.Phone.CONTACT_ID));
                Long photoID = cursor.getLong(cursor.getColumnIndex
                        (ContactsContract.CommonDataKinds.Phone.PHOTO_ID));

                //头像,获取到了,就对它赋值
                Bitmap contactPhoto = null;
                if (photoID > 0) {
                    //证明该联系人有头像
                    //生成查询头像的URI
                    Uri uri = ContentUris.withAppendedId(ContactsContract.
                            Contacts.CONTENT_URI, contactID);
                    InputStream is = ContactsContract.Contacts.
                            openContactPhotoInputStream(resolver, uri);
                    //把输入流转化成Bitmap
                    contactPhoto = BitmapFactory.decodeStream(is);
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    //该联系人没有头像,使用默认图片
                    //通过BitmapFactory来获取资源文件下的ic_launcher
                    contactPhoto = BitmapFactory.decodeResource
                            (WelcomeActivity.this.getResources(), R.mipmap.ic_launcher);
                }
                studentBade = new StudentBade(name, number, contactPhoto);
                tools1.insertDBContact(studentBade);
            }

            cursor.close();
        }

    }

    private void initAdressBook() {
        adressBooks = new ArrayList<>();
        DBTools tools = new DBTools(WelcomeActivity.this);

        ContentResolver contentResolver = WelcomeActivity.this.getContentResolver();
        //从内容提供者获得数据
        if (ActivityCompat.checkSelfPermission(WelcomeActivity.this,
                Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Cursor cursor = contentResolver.query(CallLog.Calls.CONTENT_URI, null, null, null, null);
        if (cursor != null) {
            CallrecordData data;
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex(CallLog.Calls.CACHED_NAME));
                String number = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));
                String time = cursor.getString(cursor.getColumnIndex(CallLog.Calls.DATE));
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                time = simpleDateFormat.format(new Date(Long.valueOf(time)));
                data = new CallrecordData(name, number, time);
                tools.insertDB(data);

            }
            cursor.close();//关闭cursor


        }

    }
}
