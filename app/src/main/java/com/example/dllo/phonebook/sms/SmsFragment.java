package com.example.dllo.phonebook.sms;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.dllo.phonebook.base.BaseFragment;
import com.example.dllo.phonebook.R;
import com.example.dllo.phonebook.sms.bean.SmsData;
import com.example.dllo.phonebook.sms.bean.SmsManager;

import java.util.ArrayList;

/**
 * Created by dllo on 16/4/19.
 */
public class SmsFragment extends BaseFragment {

    private RecyclerView recyclerView;
    private MyRecycleViewAdapter adapter;
    private ArrayList<SmsData> fourBeen;
    private SmsManager manager;
    private MyBroadcastReceiver myBroadcastReceiver;

    @Override
    public int setLayout() {
        return R.layout.fragment_sms;
    }

    @Override
    public void initView(View view) {

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

    }

    @Override
    public void initData() {

        adapter = new MyRecycleViewAdapter(context);
        fourBeen = new ArrayList<>();
        manager = SmsManager.getInstance();
        adapter.setDatas(manager.getShowInfo());

        final LinearLayoutManager manager1 = new LinearLayoutManager(context);

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);

        //注册广播
        myBroadcastReceiver = new MyBroadcastReceiver();

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.setPriority(100);
        intentFilter.addAction(context.getPackageName() + ".MYBROAD");
        context.registerReceiver(myBroadcastReceiver, intentFilter);


        adapter.setMyRvOnClickListener(new MyRvOnClickListener() {
            @Override
            public void myOnClick(int position) {
                fourBeen = manager.getLastInfo(position);
                Intent intent = new Intent(getContext(), MsgActivity.class);
                intent.putExtra("key", fourBeen);
                startActivity(intent);
            }
        });

//获取系统短信
    }
    //广播实现短信详情界面发送短信 返回时短信栏目实时更新
    class MyBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            //广播传对象
            Bundle bundle = getResultExtras(true);
            SmsData smsData = bundle.getParcelable("msg");
            Log.d("MyBroadcastReceiver", "smsData:" + smsData.getBody());
            manager.insetSms(smsData);

            adapter.setDatas(manager.getShowInfo());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        context.unregisterReceiver(myBroadcastReceiver);
    }
}


