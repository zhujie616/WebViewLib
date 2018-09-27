package com.xingen.webviewlib.view;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * Author by {xinGen}
 * Date on 2018/8/8 13:43
 */
public class CommonWebView extends WebView {
    private static final String TAG="CommonWebView";
    public CommonWebView(Context context) {
        super(context);
        initDefaultConfig();
    }
    public CommonWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initDefaultConfig();
    }
    public CommonWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initDefaultConfig();
    }
    /**
     * 设置默认配置
     */
    private void initDefaultConfig(){
        WebSettings settings = getSettings();
        //支持与js交互
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        if(Build.VERSION.SDK_INT >= 21){
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
}
