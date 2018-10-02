package com.xingen.androidjslib.event;

import android.webkit.WebView;

import java.lang.ref.SoftReference;

/**
 * Author by {xinGen}
 * Date on 2018/9/6 10:12
 */
public  class Event {
    public static final int TYPE_CLICK = 1;
    public static final int TYPE_SCROLL = 2;
    public static final int TYPE_INPUT = 3;
    /**
     * 延迟执行时间
     */
    public int delayTime;
    /**
     * 事件类型：点击，滚动
     */
    public int eventType;

    /**
     * 软引用的WebView的指针
     */
    protected SoftReference<WebView> softReference;
    /**
     * 序列
     */
    public int sequence;

    /**
     * 元素名
     */
    public String elementName;

    public WebView getView() {
        return softReference == null ? null : softReference.get();
    }

    public Event bindView(WebView webView) {
        softReference = new SoftReference<>(webView);
        return  this;
    }

    public Event setSequence(int sequence) {
        this.sequence = sequence;
        return  this;
    }

}
