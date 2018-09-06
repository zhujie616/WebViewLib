package com.xingen.webviewlib.callback;

/**
 * Author by {xinGen}
 * Date on 2018/8/8 16:00
 */
public interface WebViewCallback {
    /**
     * 开始加载
     */
    void startWeb();
    /**
     * 发生异常
     */
    void errorWeb();
    /**
     * 加载结束,可能加载成功或者加载失败
     */
    void endWeb();

    boolean isRepeat();
}
