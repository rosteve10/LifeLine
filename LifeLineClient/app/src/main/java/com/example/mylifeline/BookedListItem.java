package com.example.mylifeline;

public class BookedListItem {

    private String mDoctorNAme, mDate,mTime,mProblem,mStatus;

    public BookedListItem(String DoctorNAme, String Date, String Time, String Problem, String Status) {
       mDoctorNAme = DoctorNAme;
       mDate = Date;
       mTime = Time;
       mProblem = Problem;
       mStatus = Status;
    }

    public String getmDoctorNAme() {
        return mDoctorNAme;
    }

    public String getmDate() {
        return mDate;
    }

    public String getmTime() {
        return mTime;
    }

    public String getmProblem() {
        return mProblem;
    }

    public String getmStatus() {
        return mStatus;
    }
}
