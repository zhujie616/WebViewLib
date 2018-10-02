package com.xingen.webviewmanager;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.PixelFormat;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

/**
 * Created by ${新根} on 2018/9/27.
 * blog博客:http://blog.csdn.net/hexingen
 * <p>
 * 统一管理WebView,采用1px的技术,借鉴360悬浮窗口，脱离Activity加载
 */

public class WebViewManager {

    private FrameLayout contentLayout;

    private int mScreenWidth = 0;
    private int mScreenHeight = 0;
    private WindowManager windowManager;
    private WindowManager.LayoutParams windowParams;
    private Context appContext;

    public WebViewManager() {
    }

    public void init(Context context, boolean show) {
        this.appContext = context.getApplicationContext();
        if (contentLayout==null){
            calculationScreenSize(context);
            createChild(context);
            addToWindow(context, show);
        }
    }

    /**
     * 添加到window中
     */
    private void addToWindow(Context context, boolean show) {
        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        //创建window的参数
        createWindowParams(show);
        //添加到window
        windowManager.addView(contentLayout, windowParams);
    }

    /**
     * 创建子类控件
     */
    private void createChild(Context context) {
        /**
         * 作为window的子控件，用于装载WebView
         */
        contentLayout = new FrameLayout(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        contentLayout.setLayoutParams(layoutParams);
    }

    /**
     * 获取statusBar高度
     *
     * @param pContext
     * @return
     */
    public static int getStatusBarHeight(Context pContext) {
        int result = 0;
        int resourceId = pContext.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = pContext.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 设置window中参数
     *
     * @param show
     */
    private void createWindowParams(boolean show) {
        windowParams = new WindowManager.LayoutParams();
        windowParams.type = WindowManager.LayoutParams.TYPE_TOAST;
        windowParams.format = PixelFormat.RGBA_8888;
        windowParams.x = 0;
        windowParams.y = 0;
        windowParams.screenOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED;
        windowParams.gravity = Gravity.RIGHT | Gravity.BOTTOM;
        //使窗口支持透明度
        windowParams.format = PixelFormat.TRANSLUCENT;
        if (show) {
            windowParams.width = mScreenWidth;
            windowParams.height = mScreenHeight;
            windowParams.alpha = 10;//设置透明度
        } else {//设置window大小为1px
            windowParams.width = 1;
            windowParams.height = 1;
            windowParams.alpha = 0;//设置透明度
            windowParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                    | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                    | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
        }
    }

    /**
     * 计算屏幕大小
     */
    private void calculationScreenSize(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        final int defaultHeight = 1920;
        final int defaultWidth = 1080;
        mScreenWidth = dm.widthPixels;
        mScreenHeight = dm.heightPixels;
        int barHight = getStatusBarHeight(context);
        mScreenHeight = mScreenHeight - barHight;
        if (mScreenWidth == 0) {
            mScreenWidth = defaultWidth;
            mScreenHeight = defaultHeight;
        }
        //适应android tv
        if (mScreenHeight < mScreenWidth) {
            int hight = mScreenHeight;
            mScreenHeight = mScreenWidth;
            mScreenWidth = hight;
        }
    }

    public WebView  addWebView(){
       return addWebView(null);
    }
    /**
     * 增加WebView
     *
     * @param webView
     */
    public WebView addWebView(WebView webView) {
        //若是外部传入WebView为空，则构建默认的WebView
        webView = (webView == null ? WebViewBuilder.create(appContext) : webView);
        //设置WebView的大小
        FrameLayout.LayoutParams layoutParams2 = new FrameLayout.LayoutParams(mScreenWidth, mScreenHeight);
        webView.setLayoutParams(layoutParams2);
        contentLayout.addView(webView);
        return webView;
    }

    /**
     * 移除某个WebView
     *
     * @param webView
     */
    public void removeWebView(WebView webView) {
        try {
            if (contentLayout != null && webView != null) {
                contentLayout.removeView(webView);
            }
        } catch (Exception e) {

        }
    }

    /**
     * 销毁
     */
    public void quit() {
        if (windowManager != null) {
            try {
                windowManager.removeViewImmediate(contentLayout);
                int childSize = contentLayout.getChildCount();
                for (int i = 0; i < childSize; ++i) {
                    View view = contentLayout.getChildAt(i);
                    if (view instanceof WebView) {
                        WebView webView = (WebView) view;
                        webView.destroy();
                    }
                }
                contentLayout.removeAllViews();
                contentLayout = null;
            } catch (Exception e) {

            }
        }
    }

    /**
     * 构建默认的WebView
     */
    public static final class WebViewBuilder {
        public static WebView create(Context context) {
            WebView webView = new WebView(context);
            WebSettings webSetting = webView.getSettings();
            webSetting.setJavaScriptEnabled(true);
            webSetting.setDomStorageEnabled(true);
            webSetting.setGeolocationEnabled(false);
            webSetting.setSupportZoom(false);
            webSetting.setBuiltInZoomControls(false);
            webSetting.setUseWideViewPort(true);
            webSetting.setSupportMultipleWindows(false);
            webSetting.setLoadsImagesAutomatically(true);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                try {
                    webView.getSettings().setMixedContentMode(
                            WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
            return webView;
        }
    }
}
