package com.xingen.androidjslib.event;

import com.xingen.androidjslib.listener.Response;

/**
 * Author by {xinGen}
 * Date on 2018/9/6 10:15
 */
public class ScrollEvent extends  Event{
    /**
     * 滑动时间
     */
    public int scrollTime;

    public Response.ScrollListener listener;


    public ScrollEvent setListener(Response.ScrollListener listener) {
        this.listener = listener;
        return this;
    }
    public ScrollEvent setScrollTime(int scrollTime){
        this.scrollTime=scrollTime;
        return this;
    }
}
