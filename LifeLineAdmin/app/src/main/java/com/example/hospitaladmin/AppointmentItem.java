package com.example.hospitaladmin;

/**
 * Created by Rishabh Gupta on 23-12-2017.
 */
public class AppointmentItem {

    private String mname;
    private String mtime;
    private String mproblem;

    public String getMproblem() {
        return mproblem;
    }

    public AppointmentItem(String mname, String mtime, String mproblem) {
        this.mname = mname;
        this.mtime = mtime;
        this.mproblem = mproblem;
    }

    public String getMname() {
        return mname;
    }

    public String getMtime() {
        return mtime;
    }
}
