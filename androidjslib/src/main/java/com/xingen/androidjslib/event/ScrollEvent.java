package com.xingen.androidjslib.event;

import android.webkit.WebView;

import com.xingen.androidjslib.listener.Response;

import java.lang.ref.SoftReference;

/**
 * Author by {xinGen}
 * Date on 2018/9/6 10:15
 */
public class ScrollEvent extends  Event{
    /**
     * 滑动时间
     */
    public int scrollTime;

    public int position;

    public Response.ScrollListener listener;

    public ScrollEvent setPosition(int position) {
        this.position = position;
        return this;
    }

    public ScrollEvent setListener(Response.ScrollListener listener) {
        this.listener = listener;
        return this;
    }
    public ScrollEvent setScrollTime(int scrollTime){
        this.scrollTime=scrollTime;
        return this;
    }
    public ScrollEvent setElementName(String elementName) {
        this.elementName = elementName;
        return  this;
    }
    public ScrollEvent setEventType(int eventType) {
        this.eventType = eventType;
        return  this;
    }

    public ScrollEvent setDelayTime(int delayTime) {
        this.delayTime = delayTime;
        return  this;
    }


    public  static  ScrollEvent create(){
        return new ScrollEvent().setEventType(Event.TYPE_SCROLL);
    }
}
