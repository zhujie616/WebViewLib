package com.xingen.androidjslib.event;

import android.webkit.WebView;

import com.xingen.androidjslib.listener.Response;

import java.lang.ref.SoftReference;

/**
 * Author by {xinGen}
 * Date on 2018/9/6 10:15
 */
public class ClickEvent extends Event {

    public Response.ClickListener listener;

    public ClickEvent setListener(Response.ClickListener listener) {
        this.listener = listener;
        return this;
    }


    public  ClickEvent setElementName(String elementName) {
        this.elementName = elementName;
        return  this;
    }


    public ClickEvent setEventType(int eventType) {
        this.eventType = eventType;
        return this;
    }



    public ClickEvent setDelayTime(int delayTime) {
        this.delayTime = delayTime;
        return this;
    }




    public static  ClickEvent create(){
        return new ClickEvent().setEventType(Event.TYPE_CLICK);
    }



}
