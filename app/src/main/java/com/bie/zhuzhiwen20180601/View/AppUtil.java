package com.bie.zhuzhiwen20180601.View;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created 朱治文lenovo on 2018/6/1
 * .
 */

public class AppUtil {

    public static int screenWidth(Context context){
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return  metrics.widthPixels;
    }
}