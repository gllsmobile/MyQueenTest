package com.example.dllo.phonebook.call;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dllo.phonebook.base.BaseFragment;
import com.example.dllo.phonebook.R;


/**
 * Created by dllo on 16/4/19.
 */
public class CallFragment extends BaseFragment implements View.OnClickListener {
    private ImageView key_1_iv, key_2_iv, key_3_iv, key_4_iv, key_5_iv, key_6_iv, key_7_iv, key_8_iv, key_9_iv,
            key_0_iv, key_pound_iv, key_hash_iv, call_iv;
    private Button del_btn;

    private TextView showNumber;


    @Override
    public int setLayout() {
        return R.layout.fragment_dial;
    }

    @Override
    public void initView(View view) {
        showNumber = (TextView) view.findViewById(R.id.showNumber);
        del_btn = (Button) view.findViewById(R.id.del_btn);
        key_1_iv = (ImageView) view.findViewById(R.id.key_1_iv);
        key_2_iv = (ImageView) view.findViewById(R.id.key_2_iv);
        key_3_iv = (ImageView) view.findViewById(R.id.key_3_iv);
        key_4_iv = (ImageView) view.findViewById(R.id.key_4_iv);
        key_5_iv = (ImageView) view.findViewById(R.id.key_5_iv);
        key_6_iv = (ImageView) view.findViewById(R.id.key_6_iv);
        key_7_iv = (ImageView) view.findViewById(R.id.key_7_iv);
        key_8_iv = (ImageView) view.findViewById(R.id.key_8_iv);
        key_9_iv = (ImageView) view.findViewById(R.id.key_9_iv);
        key_0_iv = (ImageView) view.findViewById(R.id.key_0_iv);
        key_pound_iv = (ImageView) view.findViewById(R.id.key_pound_iv);
        key_hash_iv = (ImageView) view.findViewById(R.id.key_hash_iv);
        call_iv = (ImageView) view.findViewById(R.id.call_iv);
        del_btn.setOnClickListener(this);
        key_1_iv.setOnClickListener(this);
        key_2_iv.setOnClickListener(this);
        key_3_iv.setOnClickListener(this);
        key_4_iv.setOnClickListener(this);
        key_5_iv.setOnClickListener(this);
        key_6_iv.setOnClickListener(this);
        key_7_iv.setOnClickListener(this);
        key_8_iv.setOnClickListener(this);
        key_9_iv.setOnClickListener(this);
        key_0_iv.setOnClickListener(this);
        key_pound_iv.setOnClickListener(this);
        key_hash_iv.setOnClickListener(this);
        call_iv.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.call_iv:
                Uri uri = Uri.parse("tel:" + showNumber.getText().toString());
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(uri);
                startActivity(intent);
                break;
            case R.id.key_1_iv:
                showNumber.append("1");
                break;
            case R.id.key_2_iv:
                showNumber.append("2");
                break;
            case R.id.key_3_iv:
                showNumber.append("3");
                break;
            case R.id.key_4_iv:
                showNumber.append("4");
                break;
            case R.id.key_5_iv:
                showNumber.append("5");
                break;
            case R.id.key_6_iv:
                showNumber.append("6");
                break;
            case R.id.key_7_iv:
                showNumber.append("7");
                break;
            case R.id.key_8_iv:
                showNumber.append("8");
                break;
            case R.id.key_9_iv:
                showNumber.append("9");
                break;
            case R.id.key_0_iv:
                showNumber.append("0");
                break;
            case R.id.key_pound_iv:
                showNumber.append("*");
                break;
            case R.id.key_hash_iv:
                showNumber.append("#");
                break;
            case R.id.del_btn:
                String num = showNumber.getText().toString();
                if (num.length() >= 1) {
                    num = num.substring(0, num.length() - 1);
                    showNumber.setText(num);
                }
                break;
        }
    }
//老师的方式
    /*private TextView showNumberTv;
    private String number;
    private int ids[] = {R.id.key_0_iv, R.id.key_1_iv,
            R.id.key_2_iv, R.id.key_3_iv, R.id.key_4_iv,
            R.id.key_5_iv, R.id.key_6_iv, R.id.key_7_iv,
            R.id.key_8_iv, R.id.key_9_iv, R.id.key_pound_iv,
            R.id.key_hash_iv, R.id.call_iv, R.id.del_btn};


    @Override
    public int setLayout() {
        return R.layout.fragment_call;
    }

    @Override
    public void initView(View view) {
        showNumberTv = (TextView) view.findViewById(R.id.showNumber);
        for (int i = 0; i < ids.length; i++) {
            view.findViewById(ids[i])
                    .setOnClickListener(this);
        }
    }

    @Override
    public void initData() {
        number = showNumberTv
                .getText().toString();
    }


    @Override
    public void onClick(View v) {
        for (int i = 0; i < 10; i++) {
            if (v.getId() == ids[i]) {
                number += i;
                break;
            }
        }

        switch (v.getId()) {
            case R.id.key_pound_iv:
                number += "*";
                break;
            case R.id.key_hash_iv:
                number += "#";
                break;
            case R.id.call_iv:
                //打电话
                Intent callIntent = new Intent(Intent.ACTION_CALL,
                        Uri.parse("tel:" + number));
                startActivity(callIntent);

                number = "";
                break;
            case R.id.del_btn:
                //删除
                if (number.length() >= 1) {
                    number = number.substring(0, number.length() - 1);
                }
                break;
        }
        //将电话号码设置给textView
        showNumberTv.setText(number);
    }*/
}
