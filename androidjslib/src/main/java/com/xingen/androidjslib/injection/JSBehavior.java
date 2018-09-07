package com.xingen.androidjslib.injection;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.animation.LinearInterpolator;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import com.xingen.androidjslib.event.ScrollEvent;
import com.xingen.androidjslib.utils.LogUtils;
import com.xingen.androidjslib.utils.ScreenUtils;

/**
 * Author by {xinGen}
 * Date on 2018/9/6 12:43
 */
public class JSBehavior {

    /**
     * JavascriptInterface对象的名字，这里在webview.addJavascriptInterface()传入
     */
    public static final String JavascriptInterfaceName = "JSBehavior";
    /**
     * js 回调接口的名字
     */
    /**
     * 获取到WebView加载页面的宽高
     */
    private static int sScreenInnerWidth;
    private static int sScreenInnerHeight;

    private BehaviorCallBack callBack;

    public JSBehavior(BehaviorCallBack callBack) {
        this.callBack = callBack;
    }

    /**
     * 获取到页面的宽高
     *
     * @param screenWidth
     * @param screenHeight
     */
    @JavascriptInterface
    public void setInnerScreenInfo(int screenWidth, int screenHeight) {
        //  LogUtils.i("set web inner screen width " + screenWidth + " screen height " + screenHeight);
        sScreenInnerWidth = screenWidth;
        sScreenInnerHeight = screenHeight;
    }

    @JavascriptInterface
    public void inputResult(int sequence, int result) {
        LogUtils.i("获取到输入结果" + result);
        if (callBack != null) {
            callBack.doInputBehavior(sequence, result);
        }
    }

    /**
     * 获取到元素在页面上的区域
     *
     * @param top
     * @param left
     * @param width
     * @param height
     */
    @JavascriptInterface
    public void clickArea(int sequence, int result, int top, int left, int width, int height) {
        LogUtils.i(" 获取到点击区域 " + top + " " + left + " " + width + " " + height);
        if (callBack != null) {
            callBack.doClickBehavior(sequence, result, top, left, width, height, sScreenInnerWidth, sScreenInnerHeight);
        }
    }

    /**
     * 滚动到指定元素，让元素处于中间位置
     *
     * @param sequence
     * @param result
     * @param start_x
     * @param start_y
     * @param end_x
     * @param end_y
     * @param windowWidth
     * @param windowHeight
     */
    @JavascriptInterface
    public void scrollScreen(int sequence, int result, int start_x, int start_y, int end_x, int end_y, int windowWidth, int windowHeight) {
        LogUtils.i(" 获取滑动的起点和终点坐标 ");
        if (callBack != null) {
            callBack.doScrollBehavior(sequence, result, start_x, start_y, end_x, end_y, windowWidth, windowHeight);
        }
    }

    /**
     * 处理点击事件
     *
     * @param point
     */
    public static void handlerClickEvent(final WebView webView, final int[] point) {
        long downTime = SystemClock.uptimeMillis();
        long eventTime = SystemClock.uptimeMillis();
        int down_x, down_y, up_x, up_y;
        down_x = point[0];
        down_y = point[1];
        up_x = point[2];
        up_y = point[3];
        MotionEvent event = MotionEvent.obtain(downTime, eventTime, MotionEvent.ACTION_DOWN, down_x, down_y, 0);
        webView.dispatchTouchEvent(event);
        event.recycle();
        eventTime += ScreenUtils.random(100);
        event = MotionEvent.obtain(downTime, eventTime, MotionEvent.ACTION_UP, up_x, up_y, 0);
        webView.dispatchTouchEvent(event);
        event.recycle();
    }

    /**
     * 处理滑动事件
     *
     * @param scrollEvent
     * @param point
     */
    public static void handlerScrollEvent(final ScrollEvent scrollEvent, final int[] point) {
        final WebView webView = scrollEvent.getView();
        if (webView == null) return;
        final ValueAnimator scrollAnimator = ValueAnimator.ofFloat(0, 1f);
        final long downTime = SystemClock.currentThreadTimeMillis();
        scrollAnimator.setInterpolator(new LinearInterpolator());
        scrollAnimator.setDuration(scrollEvent.scrollTime);
        final int start_x = point[0];
        final int start_y = point[1];
        final int end_x = point[2];
        final int end_y = point[3];
        scrollAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                disPatchEvent(webView, start_x, start_y, MotionEvent.ACTION_DOWN, downTime);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                disPatchEvent(webView, end_x, end_y, MotionEvent.ACTION_UP, downTime);
                if (scrollEvent.listener != null) {
                    scrollEvent.listener.scrollEnd(scrollEvent);
                }
            }
        });
        scrollAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float fraction = animation.getAnimatedFraction();
                int move_x = (int) (start_x + (end_x - start_x) * fraction);
                int move_y = (int) (start_y + (end_y - start_y) * fraction);
                disPatchEvent(webView, move_x, move_y, MotionEvent.ACTION_MOVE, downTime);
            }
        });
        scrollAnimator.start();
    }

    private static final void disPatchEvent(WebView webView, int x, int y, int eventType, long downTime) {
        if (webView == null) return;
        long time = SystemClock.uptimeMillis();
        MotionEvent event = MotionEvent.obtain(downTime, time, eventType, x, y, 0);
        webView.dispatchTouchEvent(event);
        event.recycle();
    }

    public static int[] conversionScrollPoints(int start_x, int start_y, int end_x, int end_y, int sScreenInnerHeight, int sScreenInnerWidth, WebView webView) {
        int[] points = new int[4];
        points[0] = ScreenUtils.converseWebScreenPixel(start_x, sScreenInnerWidth, webView.getWidth());
        points[1] = ScreenUtils.converseWebScreenPixel(start_y, sScreenInnerHeight, webView.getHeight());
        points[2] = ScreenUtils.converseWebScreenPixel(end_x, sScreenInnerWidth, webView.getWidth());
        points[3] = ScreenUtils.converseWebScreenPixel(end_y, sScreenInnerHeight, webView.getHeight());
        return points;
    }

    /**
     * 1.将页面上的坐标转换成手机屏幕上的坐标
     * 2.然后在区域内选取动作点
     *
     * @param top
     * @param left
     * @param width
     * @param height
     * @return
     */
    public static int[] conversionClickPoints(int top, int left, int width, int height, int sScreenInnerHeight, int sScreenInnerWidth, WebView webView) {
        //先转换成屏幕上的坐标点
        top = ScreenUtils.converseWebScreenPixel(top, sScreenInnerHeight, webView.getHeight());
        left = ScreenUtils.converseWebScreenPixel(left, sScreenInnerWidth, webView.getWidth());
        width = ScreenUtils.converseWebScreenPixel(width, sScreenInnerWidth, webView.getWidth());
        height = ScreenUtils.converseWebScreenPixel(height, sScreenInnerHeight, webView.getWidth());
        LogUtils.i(" 转换后的手机屏幕坐标点 " + top + " " + left + " " + width + " " + height);
        //获取到中心点位置
        int[] points = new int[4];
        int down_x, down_y, up_x, up_y;
        down_x = left + width / 2;
        down_y = top + height / 2;
        up_x = down_x + ScreenUtils.random(5);
        up_y = down_y + ScreenUtils.random(5);
        points[0] = down_x;
        points[1] = down_y;
        points[2] = up_x;
        points[3] = up_y;
        return points;
    }

    /**
     * 行为回调
     */
    public interface BehaviorCallBack {
        void doInputBehavior(int sequence, int result);

        void doClickBehavior(int sequence, int result, int top, int left, int width, int height, int sScreenInnerWidth, int sScreenInnerHeight);

        void doScrollBehavior(int sequence, int result, int start_x, int start_y, int end_x, int end_y, int windowWidth, int windowHeight);
    }

}
