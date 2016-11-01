package com.example.dllo.phonebook.contact;

import android.graphics.Bitmap;

/**
 * Created by dllo on 16/4/25.
 */
public class StudentBade {
    private String tv_name, tv_number;
    private Bitmap imgId;

    public StudentBade() {
    }

    public StudentBade(String tv_name, String tv_number, Bitmap imgId) {
        this.tv_name = tv_name;
        this.tv_number = tv_number;
        this.imgId = imgId;
    }

    public String getTv_name() {
        return tv_name;
    }

    public void setTv_name(String tv_name) {
        this.tv_name = tv_name;
    }

    public String getTv_number() {
        return tv_number;
    }

    public void setTv_number(String tv_number) {
        this.tv_number = tv_number;
    }

    public Bitmap getImgId() {
        return imgId;
    }

    public void setImgId(Bitmap imgId) {
        this.imgId = imgId;
    }
}
