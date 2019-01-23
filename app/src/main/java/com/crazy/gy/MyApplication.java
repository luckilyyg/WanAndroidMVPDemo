package com.crazy.gy;

import android.app.Application;
import android.content.Context;

import com.blankj.utilcode.util.Utils;


//import com.tencent.bugly.crashreport.CrashReport;

/**
 * author: fangxiaogang
 * date: 2018/9/1
 */

public class MyApplication extends Application {

    private static MyApplication myApplication;





    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;
        Utils.init(this);
    }


    public static Context getAppContext() {
        return myApplication.getApplicationContext();
    }




    public static MyApplication getInstance() {
        return myApplication;
    }


}
