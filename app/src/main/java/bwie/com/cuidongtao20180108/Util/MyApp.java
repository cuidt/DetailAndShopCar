package bwie.com.cuidongtao20180108.Util;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * 作者： 崔冬涛
 * 时间： 2018/1/8.
 */

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this.getApplicationContext());
    }
}
