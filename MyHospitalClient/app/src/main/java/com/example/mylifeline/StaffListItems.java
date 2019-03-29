package com.example.mylifeline;

public class StaffListItems {
    private String mName;
    private String mExpertise;
    private String mImageUrl;
    private String mMobile;
    private String mhospitalName;

    public StaffListItems(String name, String expertise, String imageUrl, String mobile, String hospitalName) {
        mName = name;
        mExpertise = expertise;
        mImageUrl = imageUrl;
        mMobile = mobile;
        mhospitalName = hospitalName;
    }

    public String getmName() {
        return mName;
    }

    public String getmExpertise() {
        return mExpertise;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public String getmMobile() {
        return mMobile;
    }

    public String getMhospitalName() {
        return mhospitalName;
    }
}
