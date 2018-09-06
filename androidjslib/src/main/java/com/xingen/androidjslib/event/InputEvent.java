package com.xingen.androidjslib.event;

import com.xingen.androidjslib.listener.Response;

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


}
