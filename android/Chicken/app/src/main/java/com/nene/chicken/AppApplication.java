package com.nene.chicken;

import android.app.Application;

import com.tsengvn.typekit.Typekit;

/**
 * Created by ParkHaeSung on 2017-09-15.
 */

public class AppApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Typekit.getInstance()
                .addNormal(Typekit.createFromAsset(this, "fonts/NanumSquareR.otf"))
                .addBold(Typekit.createFromAsset(this, "fonts/NanumSquareB.otf"))
                .addCustom1(Typekit.createFromAsset(this, "fonts/NanumBarunGothic.otf"));
    }
}
