package com.xingen.androidjslib.utils;

import android.util.Log;

/**
 * Author by {xinGen}
 * Date on 2018/9/6 10:02
 */
public class LogUtils {
    public static boolean shoLog=true;
    public static final String DEFAULT_TAG="JavaScript";
    public static void init(boolean open){
        shoLog=open;
    }
    public static void i(String content){
        i(DEFAULT_TAG,content);
    }
    public static void i(String tag,String content){
        if (shoLog){
            Log.i(tag,content);
        }
    }

}
