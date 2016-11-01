package com.example.dllo.phonebook.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by dllo on 16/4/23.
 */
public abstract class BaseFragment extends Fragment {
    protected Context context;


    //要求使用该基类的Fragment重写这个方法
    //来设置布局
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(setLayout(), container, false);
    }

    public abstract int setLayout();

    //该方法是专门用来初始化组件的
    //参数中的View 就是在onCreateView中返回的view
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }
    //初始化组件的方法
    public abstract void initView(View view);


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();//让子类在这里初始化数据
    }
    //初始化数据,逻辑代码也写在这
    public abstract void initData();
}
