package com.example.mylifeline;

/**
 * Created by Rishabh Gupta on 29-03-2019
 */

public class HospitalListItem {
    private String mName;
    private String mAddress;


    public HospitalListItem(String name, String address) {
        mName = name;
        mAddress = address;
    }

    public String getmName() {
        return mName;
    }

    public String getmAddress() {
        return mAddress;
    }
}
