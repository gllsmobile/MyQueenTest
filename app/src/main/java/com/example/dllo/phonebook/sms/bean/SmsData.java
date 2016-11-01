package com.example.dllo.phonebook.sms.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by dllo on 16/4/29.
 */
public class SmsData implements Parcelable {
    private String name, number, body, date;
    private int type;

    public SmsData(String name, String number, String body, String date, int type) {
        this.name = name;//名字
        this.number = number;//电话号
        this.body = body;//短信的内容
        this.date = date;//短信的时间
        this.type = type;//短信的类型
    }

    public SmsData() {
    }

    protected SmsData(Parcel in) {
        name = in.readString();
        number = in.readString();
        body = in.readString();
        date = in.readString();
        type = in.readInt();
    }

    public static final Creator<SmsData> CREATOR = new Creator<SmsData>() {
        @Override
        public SmsData createFromParcel(Parcel in) {
            return new SmsData(in);
        }

        @Override
        public SmsData[] newArray(int size) {
            return new SmsData[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(number);
        dest.writeString(body);
        dest.writeString(date);
        dest.writeInt(type);
    }
}
