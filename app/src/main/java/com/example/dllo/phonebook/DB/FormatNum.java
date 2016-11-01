package com.example.dllo.phonebook.DB;

/**
 * Created by dllo on 16/5/4.
 */
public class FormatNum {
    public  static String Format(String num){
        num = num.replace("-","");
        num = num.replace("(","");
        num = num.replace(")","");
        num = num.replace(" ","");
        return num;
    }
}
