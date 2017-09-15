package com.nene.chicken.Util;

import android.content.Context;
import android.os.Build;

/**
 * Created by ParkHaeSung on 2017-08-05.
 */


public class ResourceUtil {
    public static int getColor(Context context, int id) {
        final int version = Build.VERSION.SDK_INT;
        if (version >= Build.VERSION_CODES.M) {
            return context.getColor(id);
        } else {
            return context.getResources().getColor(id);
        }
    }


    public static String getStringResource(Context context, int id) {
        return context.getString(id);
    }

    public static final String getFormattedStringByLastWord(String rawString, String firstValue, String secondValue) { // 을,를 / 이,가 / 은,는
        char lastName = rawString.charAt(rawString.length() - 1); // 한글의 제일 처음과 끝의 범위밖일 경우는 오류
        if (lastName < 0xAC00 || lastName > 0xD7A3) {
            return rawString;
        }
        String seletedValue = (lastName - 0xAC00) % 28 > 0 ? firstValue : secondValue;
        return rawString + seletedValue;
    }
}
