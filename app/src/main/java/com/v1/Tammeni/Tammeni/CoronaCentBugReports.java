package com.v1.Tammeni.Tammeni;

import android.app.Application;

import com.bugsee.library.Bugsee;

public class CoronaCentBugReports extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Bugsee.launch(this, "3207d79c-c106-443b-9580-113a2f80ecdb");
    }
}
