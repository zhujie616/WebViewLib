package com.xingen.androidjslib.execute;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;


/**
 * Author by {xinGen}
 * Date on 2018/9/6 10:23
 */
public class MainExecutor implements Executor {
    private Handler handler=new Handler(Looper.getMainLooper());
    @Override
    public void execute(Runnable command) {
        if (command!=null){
            handler.post(command);
        }
    }
    public void delayTimeExecute(Runnable command,int delayTime){
        if (command!=null){
            handler.postDelayed(command,delayTime);
        }
    }
}
