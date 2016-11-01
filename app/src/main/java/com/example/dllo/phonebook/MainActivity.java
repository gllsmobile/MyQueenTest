package com.example.dllo.phonebook;

import android.content.DialogInterface;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.dllo.phonebook.callrecords.CallrecordsFragment;
import com.example.dllo.phonebook.contact.ContactFragment;
import com.example.dllo.phonebook.call.CallFragment;
import com.example.dllo.phonebook.sms.SmsFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private MainFragmentPagerAdapter myAdapter;
    private ViewPager viewPager;
    private ArrayList<Fragment> fragmentArrayList;
    private TabLayout tabLayout;
    private Button button;
    private TextView titleTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        button = (Button) findViewById(R.id.rightButton);
        button.setOnClickListener(this);
        titleTv = (TextView) findViewById(R.id.myTitle);

        myAdapter = new MainFragmentPagerAdapter(getSupportFragmentManager());

        fragmentArrayList = new ArrayList<>();
        fragmentArrayList.add(new CallFragment());
        fragmentArrayList.add(new CallrecordsFragment());
        fragmentArrayList.add(new ContactFragment());
        fragmentArrayList.add(new SmsFragment());
        //把数据放到adapter里
        myAdapter.setFragments(fragmentArrayList);
        viewPager.setAdapter(myAdapter);
        //联系tabLayout和viewPager
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        tabLayout.getTabAt(3).setIcon(R.drawable.information);
        tabLayout.getTabAt(0).setIcon(R.drawable.dial);
        tabLayout.getTabAt(1).setIcon(R.drawable.callrecords);
        tabLayout.getTabAt(2).setIcon(R.drawable.contact);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //设置标题文字
                setTitleBar(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //初始化tab的图标
        initTabIcon();

    }

    private void initTabIcon() {

    }


    private void setTitleBar(int pos) {
        //position就是当前viewPager选中的页面
        titleTv.setText(myAdapter.getPageTitle(pos));
        switch (pos) {
            case 0:
                button.setVisibility(View.GONE);
                break;
            case 1:
                button.setVisibility(View.GONE);
                break;
            case 2:
                button.setVisibility(View.GONE);
                break;
            case 3:
                button.setVisibility(View.VISIBLE);
                break;
        }
    }


    @Override
    public void onClick(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("发送短信");
        View view = LayoutInflater.from(this).inflate(R.layout.itme_sms_one, null);
        final EditText numberEt = (EditText) view.findViewById(R.id.et_one_one);
        final EditText sendText = (EditText) view.findViewById(R.id.et_one_two);
        builder.setView(view);

        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String num = numberEt.getText().toString();
                String text = sendText.getText().toString();
                if (text.length() != 0 && num.length() != 0 ){
                    SmsManager manager = SmsManager.getDefault();
                    manager.sendTextMessage(num, null, text, null,null);
                }
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "取消短信", Toast.LENGTH_SHORT).show();
            }
        });
        builder.show();
    }
}
