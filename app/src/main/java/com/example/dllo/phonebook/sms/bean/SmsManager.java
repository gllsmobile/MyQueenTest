package com.example.dllo.phonebook.sms.bean;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by dllo on 16/5/4.
 */
/*
    * {
    *   '110':[短信1, 短信2, 短信3],
    *   '120':[短信4, 短信5, 短信6],
    *   '122':[短信7, 短信8, 短信9]
    * }
    * */
public class SmsManager {

    private HashMap<String, ArrayList<SmsData>> allSms;
    private ArrayList<SmsData> showInfo;
    private static SmsManager smsManager;

    public static SmsManager getInstance() {
        if (smsManager == null) {
            synchronized (SmsManager.class) {
                if (smsManager == null) {
                    smsManager = new SmsManager();
                }
            }
        }

        return smsManager;
    }

    private SmsManager() {
        allSms = new HashMap<>();

    }
    //用来判断当前hashMap里有没有这个联系人key
    //如果有 就直接加入到联系人对应的集合里

    //通过电话号 获取key
    public void addSms(SmsData s) {

        String num = s.getNumber();

        /*num = FormatNum.Format(num);*/
        if (allSms.containsKey(num)) {

            allSms.get(s.getNumber()).add(s);
        } else {
            //如果没有 就创建一个新的联系人的短息集合,加入到集合里
            ArrayList<SmsData> arrayList = new ArrayList<>();
            arrayList.add(s);
            //按键值对加入到hashMap里 设置一个新的key
            allSms.put(num, arrayList);
        }
    }

    public void insetSms(SmsData s){
        String number = s.getNumber();
        if (allSms.containsKey(number)){
            String key = s.getNumber();
            allSms.get(key).add(0,s);
        }else {
            //如果没有 就创建一个新的联系人的短信集合,加入到集合里
            ArrayList<SmsData> arrayList = new ArrayList<>();
            arrayList.add(s);
            //按键值对加入到hashMap里 设置一个新的key
            allSms.put(number,arrayList);
        }
    }




    //返回点击对应的集合
    public ArrayList<SmsData> getLastInfo(int position) {
        SmsData sms = showInfo.get(position);
        ArrayList<SmsData> smsDataArrayList = allSms.get(sms.getNumber());
        return smsDataArrayList;
    }

    //显示集合中的第一条短信
    public ArrayList<SmsData> getShowInfo() {
        showInfo = new ArrayList<>();
        for (String key : allSms.keySet()) {
            showInfo.add(allSms.get(key).get(0));
        }
        return showInfo;
    }
}
