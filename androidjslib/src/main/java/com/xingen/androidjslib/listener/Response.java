package com.xingen.androidjslib.listener;

import com.xingen.androidjslib.event.ClickEvent;
import com.xingen.androidjslib.event.InputEvent;
import com.xingen.androidjslib.event.ScrollEvent;

/**
 * Author by {xinGen}
 * Date on 2018/9/6 09:47
 */
public interface Response {
    /**
     * 成功，执行行为
     */
    int BEHAVIOR_SUCCESS = 1;
    /**
     * 失败，执行行为
     */
    int BEHAVIOR_FAILURE = 2;

    /**
     * 点击事件监听器
     */
    interface ClickListener {
        void clickFailure(ClickEvent clickEvent);

        void clickSuccess(ClickEvent clickEvent);
    }

    /**
     * 滚动事件监听器
     */
    interface ScrollListener {
        void scrollEnd(ScrollEvent scrollEvent);

        void scrollFailure(ScrollEvent scrollEvent);
    }

    /**
     * 输入框监听器
     */
    interface InputListener {
        void inputFailure(InputEvent inputEvent);

        void inputSuccess(InputEvent inputEvent);
    }
}
