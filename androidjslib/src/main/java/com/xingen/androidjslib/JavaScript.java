package com.xingen.androidjslib;

import android.webkit.WebView;

import com.xingen.androidjslib.event.ClickEvent;
import com.xingen.androidjslib.event.Event;
import com.xingen.androidjslib.event.InputEvent;
import com.xingen.androidjslib.event.ScrollEvent;
import com.xingen.androidjslib.execute.MainExecutor;
import com.xingen.androidjslib.injection.JSBehavior;
import com.xingen.androidjslib.injection.JSInjection;
import com.xingen.androidjslib.listener.Response;
import com.xingen.androidjslib.utils.LogUtils;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Author by {xinGen}
 * Date on 2018/9/6 09:46
 */
public class JavaScript {
    private static final MainExecutor mainExecutor = new MainExecutor();
    private static final AtomicInteger mSequenceGenerator = new AtomicInteger();
    private static List<Event> eventList = new CopyOnWriteArrayList<>();

    private JavaScript() {
    }

    /**
     * 获取递增序列号
     *
     * @return
     */
    private static int getSequenceNumber() {
        return mSequenceGenerator.incrementAndGet();
    }

    /**
     * 在WebView中执行事件
     *
     * @param event
     * @param webView
     */
    private static void executeEvent(final Event event, final WebView webView) {
        if (event == null || webView == null) {
            LogUtils.i("event对象为null 或者webview对象为null ");
            return;
        }
        event.bindView(webView);
        event.setSequence(getSequenceNumber());
        eventList.add(event);
        mainExecutor.delayTimeExecute(new Runnable() {
            @Override
            public void run() {
                switch (event.eventType) {
                    case Event.TYPE_CLICK: {
                        ClickEvent clickEvent = (ClickEvent) event;
                        webView.loadUrl(JSInjection.collectScreenInfoJS());
                        String js=JSInjection.findIndexElementAreaJS(clickEvent.sequence, clickEvent.elementName, 0);
                        webView.loadUrl(js);
                        LogUtils.i(js);
                    }
                    break;
                    case Event.TYPE_INPUT: {
                        InputEvent inputEvent = (InputEvent) event;
                        webView.loadUrl(JSInjection.inputValueJS(inputEvent.sequence, inputEvent.elementName, inputEvent.value));
                    }
                    break;
                    case Event.TYPE_SCROLL: {
                           ScrollEvent scrollEvent=(ScrollEvent) event;
                           webView.loadUrl(JSInjection.scrollElementJS(scrollEvent.sequence,scrollEvent.elementName,scrollEvent.position));
                    }
                    break;
                }
            }
        }, event.delayTime);
    }

    /**
     * 查找，释放掉该事件
     *
     * @param sequence
     */
    private static Event findEventAndRelease(int sequence) {
        Event designation = null;
        for (Event event : eventList) {
            if (event.sequence == sequence) {
                designation = event;
                eventList.remove(event);
                break;
            }
        }
        return designation;
    }


    private static final JSBehavior.BehaviorCallBack callBack = new JSBehavior.BehaviorCallBack() {
        @Override
        public void doInputBehavior(int sequence, int result) {
            Event event = findEventAndRelease(sequence);
            if (event == null || event.eventType != Event.TYPE_INPUT) {
                return;
            }
            final InputEvent inputEvent = (InputEvent) event;
            if (inputEvent.listener == null) {
                return;
            }
            switch (result) {
                case Response.BEHAVIOR_SUCCESS:
                    mainExecutor.execute(new Runnable() {
                        @Override
                        public void run() {
                            inputEvent.listener.inputSuccess(inputEvent);
                        }
                    });
                    break;
                case Response.BEHAVIOR_FAILURE:
                    mainExecutor.execute(new Runnable() {
                        @Override
                        public void run() {
                            inputEvent.listener.inputFailure(inputEvent);
                        }
                    });
                    break;
            }
        }

        @Override
        public void doClickBehavior(int sequence, int result, final int top, final int left, final int width, final int height, final int sScreenInnerWidth, final int sScreenInnerHeight) {
            Event event = findEventAndRelease(sequence);
            if (event == null || event.eventType != Event.TYPE_CLICK) {
                return;
            }
            final ClickEvent clickEvent = (ClickEvent) event;
            if (clickEvent.listener == null) {
                return;
            }
            switch (result) {
                case Response.BEHAVIOR_SUCCESS:
                    mainExecutor.execute(new Runnable() {
                        @Override
                        public void run() {
                            if (clickEvent.getView() != null) {
                                JSBehavior.handlerClickEvent(clickEvent.getView(), JSBehavior.conversionClickPoints(top, left, width, height, sScreenInnerHeight, sScreenInnerWidth, clickEvent.getView()));
                                clickEvent.listener.clickSuccess(clickEvent);
                            } else {
                                clickEvent.listener.clickFailure(clickEvent);
                            }
                        }
                    });
                    break;
                case Response.BEHAVIOR_FAILURE:
                    mainExecutor.execute(new Runnable() {
                        @Override
                        public void run() {
                            clickEvent.listener.clickFailure(clickEvent);
                        }
                    });
                    break;
            }
        }

        @Override
        public void doScrollBehavior(int sequence, int result, final int start_x, final int start_y, final int end_x, final int end_y, final int windowWidth, final int windowHeight) {
            final Event event = findEventAndRelease(sequence);
            if (event == null || event.eventType != Event.TYPE_SCROLL) {
                return;
            }
            final ScrollEvent scrollEvent = (ScrollEvent) event;
            if (scrollEvent.listener == null) {
                return;
            }
            switch (result) {
                case Response.BEHAVIOR_SUCCESS:
                    mainExecutor.execute(new Runnable() {
                        @Override
                        public void run() {
                            if (scrollEvent.getView()==null){
                                 scrollEvent.listener.scrollFailure(scrollEvent);
                            }else{
                                JSBehavior.handlerScrollEvent(scrollEvent,JSBehavior.conversionScrollPoints(start_x,start_y,end_x,end_y,windowHeight,windowWidth,scrollEvent.getView()));
                            }
                        }
                    });
                    break;
                case Response.BEHAVIOR_FAILURE:
                    mainExecutor.execute(new Runnable() {
                        @Override
                        public void run() {
                            scrollEvent.listener.scrollFailure(scrollEvent);
                        }
                    });
                    break;
            }
        }
    };

    public final static class JavaScriptBuilder {
        /**
         * 执行js事件
         *
         * @param event
         * @param webView
         */
        public static void executeEvent(Event event, WebView webView) {
            JavaScript.executeEvent(event, webView);
        }
        /**
         * 是否开启日志
         * 添加javascript对象
         *
         * @param webView
         * @param hasLog
         */
        public static void init(WebView webView, boolean hasLog) {
            if (webView == null) return;
            LogUtils.init(hasLog);
            //添加JS交互对象
            webView.addJavascriptInterface(new JSBehavior(callBack), JSBehavior.JavascriptInterfaceName);
        }
    }

}



