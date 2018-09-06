package com.xingen.androidjslib.event;

import android.webkit.WebView;

import java.lang.ref.SoftReference;

/**
 * Author by {xinGen}
 * Date on 2018/9/6 10:12
 */
public abstract class Event {
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
    private SoftReference<WebView> softReference;
    /**
     * 序列
     */
    public int sequence;

    /**
     * 元素名
     */
    public String elementName;

    public <T extends Event> T setElementName(String elementName) {
        this.elementName = elementName;
        return (T) this;
    }


    public <T extends Event> T setEventType(int eventType) {
        this.eventType = eventType;
        return (T) this;
    }

    public <T extends Event> T bindView(WebView webView) {
        softReference = new SoftReference<>(webView);
        return (T) this;
    }

    public <T extends Event> T setDelayTime(int delayTime) {
        this.delayTime = delayTime;
        return (T) this;
    }

    public <T extends Event> T setSequence(int sequence) {
        this.sequence = sequence;
        return (T) this;
    }

    public WebView getView() {
        return softReference == null ? null : softReference.get();
    }

    public static <T extends Event> T create(int eventType) {
        switch (eventType) {
            case Event.TYPE_CLICK:
                return new ClickEvent().setEventType(eventType);
            case Event.TYPE_SCROLL:
                return new ScrollEvent().setEventType(eventType);
            case Event.TYPE_INPUT:
                return new InputEvent().setEventType(eventType);
            default:
                return null;
        }
    }
}
