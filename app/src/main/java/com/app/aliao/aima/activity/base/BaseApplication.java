package com.app.aliao.aima.activity.base;

import android.app.Application;
import android.content.res.Configuration;
import android.content.res.Resources;

/**
 * Created by ALiao on 2014/12/25.
 * desc:
 */
public class BaseApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
    }


    @Override
    public Resources getResources() {
        //目的是设置app字体不随系统的字体设置改变而改变
        Resources res = super.getResources();
        Configuration configuration = new Configuration();
        configuration.setToDefaults();
        res.updateConfiguration(configuration, res.getDisplayMetrics());
        return super.getResources();
    }
}
