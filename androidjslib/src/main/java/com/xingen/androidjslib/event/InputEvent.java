package com.xingen.androidjslib.event;

import android.webkit.WebView;

import com.xingen.androidjslib.listener.Response;

import java.lang.ref.SoftReference;

/**
 * Author by {xinGen}
 * Date on 2018/9/6 11:09
 */
public class InputEvent extends Event{
    public  String value;

    public InputEvent setValue(String value) {
        this.value = value;
        return this;
    }

    public Response.InputListener listener;

    public InputEvent setListener(Response.InputListener listener) {
        this.listener = listener;
        return this;
    }

    public InputEvent setElementName(String elementName) {
        this.elementName = elementName;
        return  this;
    }


    public InputEvent setEventType(int eventType) {
        this.eventType = eventType;
        return  this;
    }

    public InputEvent setDelayTime(int delayTime) {
        this.delayTime = delayTime;
        return  this;
    }



    public static  InputEvent create(){
        return new InputEvent().setEventType(Event.TYPE_INPUT);
    }

}
