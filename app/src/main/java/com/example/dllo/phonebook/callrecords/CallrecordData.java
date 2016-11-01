package com.example.dllo.phonebook.callrecords;

/**
 * Created by dllo on 16/4/20.
 */
public class CallrecordData {
    private String name,number,time;
  private int imgid;

    private boolean isCheck;

    public CallrecordData(String name, int imgid) {
        this.name = name;
        this.imgid = imgid;
    }

    public CallrecordData() {
    }

    public boolean isCheck(){
       return isCheck;
   }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public CallrecordData(String name, String number, String time) {
        this.name = name;
        this.number = number;
        this.time = time;
    }

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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


}
