package com.nene.chicken;

import android.app.Application;

import com.tsengvn.typekit.Typekit;

/**
 * Created by ParkHaeSung on 2017-09-15.
 */

public class AppApplication extends Application {
    public static String NAVER_CLIENT_ID="gkfBBEDqefty0QjgtA6n";
    public static String GOOGLE_MAP_ID="AIzaSyBYIW9QkmglehhlXgrW5x_alVxaeJy-efQ";
    @Override
    public void onCreate() {
        super.onCreate();
        Typekit.getInstance()
                .addNormal(Typekit.createFromAsset(this, "fonts/dool.ttf"))
                .addBold(Typekit.createFromAsset(this, "fonts/dool.ttf"));
    }
}
