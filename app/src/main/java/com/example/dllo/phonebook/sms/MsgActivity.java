package com.example.dllo.phonebook.sms;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.dllo.phonebook.R;
import com.example.dllo.phonebook.sms.bean.SmsData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by dllo on 16/5/5.
 */
public class MsgActivity extends Activity implements View.OnClickListener {

    private RecyclerView recyclerView;
    private MsgAdapter adapter;
    private ArrayList<SmsData> data;
    private Button btnmsg;
    private EditText etmsg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msg);

        Intent intent = getIntent();
        data = intent.getParcelableArrayListExtra("key");

        btnmsg = (Button) findViewById(R.id.btn_msg);
        etmsg = (EditText) findViewById(R.id.et_msg);
        btnmsg.setOnClickListener(this);

        etmsg.setFocusable(true);
        etmsg.requestFocus();
        onFocusChanged(etmsg.isFocused());

        //取出缓存数据key为当前电话号码  value为editText中的数据短信内容
        SharedPreferences ss = getSharedPreferences("message", MODE_PRIVATE);
        String msg = ss.getString(data.get(0).getNumber().toString(), "");
        etmsg.setText(msg);


        recyclerView = (RecyclerView) findViewById(R.id.msgRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new MsgAdapter(this);
        adapter.setDatas(data);
        recyclerView.setAdapter(adapter);

    }

    private void onFocusChanged(boolean focused) {
        final boolean isFocused = focused;
        (new Handler()).postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodManager imm = (InputMethodManager) etmsg.getContext().
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                if (isFocused) {
                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                } else {
                    imm.hideSoftInputFromWindow(etmsg.getWindowToken(), 0);
                }
            }
        }, 100);
    }


    //发送短信  获取号码和内容
    @Override
    public void onClick(View v) {
        String num = data.get(0).getNumber();

        String message = etmsg.getText().toString();
        SmsManager manager = SmsManager.getDefault();

        if (!(message.isEmpty() || num.isEmpty())) {
            manager.sendTextMessage(num, null, message, null, null);
            Toast.makeText(this, "短信发送成功", Toast.LENGTH_SHORT).show();

            SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            String time = format.format(new Date());

            SmsData sms = new SmsData(data.get(0).getName(),data.get(0).getNumber(),message,time,1);
            adapter.addData(sms);
            etmsg.setText("");
            sendMyBroad(sms);
            Log.d("MsgActivity", "sms:----->" + sms.getNumber());
        }
        //增加广播
        if (!(num.isEmpty() || message.isEmpty())) {
            Intent intent = new Intent("com.example.dllo.phonebook.MY_BROAD");
            intent.putExtra("messageBroadcast",etmsg.getText());

            //调用发送广播方法
            sendBroadcast(intent);
        }
    }

    //复写返回键方法
    public void onBackPressed() {
        super.onBackPressed();
        etSendContent();
        if (etmsg.getText().length() > 0) {
            Toast.makeText(this, "短信已存入草稿", Toast.LENGTH_SHORT).show();
        }
    }

    //像本地缓存中增加短信草稿
    private void etSendContent() {
        SharedPreferences sp = getSharedPreferences("message", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(data.get(0).getNumber().toString(), etmsg.getText().toString());
        editor.commit();
    }
    //发送广播方法  并将bundle传出
    public void sendMyBroad(SmsData sms){
        Intent intent = new Intent(getPackageName() +".MYBROAD");
        Bundle bundle = new Bundle();
        bundle.putParcelable("msg",sms);
        sendOrderedBroadcast(intent,null,null,null,0,null,bundle);
    }
}
