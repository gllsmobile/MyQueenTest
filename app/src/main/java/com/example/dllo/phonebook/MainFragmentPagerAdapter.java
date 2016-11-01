package com.example.dllo.phonebook;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by dllo on 16/4/19.
 */
public class MainFragmentPagerAdapter extends FragmentPagerAdapter {
    private String[] titles = {"拨号", "通话记录", "联系人", "短信"};
    private ArrayList<Fragment> fragments;

    public MainFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setFragments(ArrayList<Fragment> fragments) {
        this.fragments = fragments;
        //当数据发生变化的时候 通知adapter中的对象   数据改变的时候刷新视图
        notifyDataSetChanged();
    }

    public ArrayList<Fragment> getFragments() {
        return fragments;
    }

    //通过集合的下标得到Fragment


    @Override
    public Fragment getItem(int position) {
        return fragments.size() > 0 && fragments != null ? fragments.get(position) : null;
    }

    //得到集合的数量
    @Override
    public int getCount() {
        return fragments.size();
    }

    //通过getPageTitle方法给tabLayout设置标题
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
