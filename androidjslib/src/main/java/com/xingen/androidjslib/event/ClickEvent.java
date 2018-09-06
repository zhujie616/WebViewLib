package com.xingen.androidjslib.event;

import com.xingen.androidjslib.listener.Response;

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



}
