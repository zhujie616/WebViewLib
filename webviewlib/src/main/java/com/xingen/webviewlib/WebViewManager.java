package com.xingen.webviewlib;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.PixelFormat;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;

/**
 * Created by ${新根} on 2018/9/27.
 * blog博客:http://blog.csdn.net/hexingen
 * <p>
 * 统一管理WebView,采用1px的技术
 */

public class WebViewManager {

    private LinearLayout contentLayout;
    private WebView webView;
    private int mScreenWidth = 0;
    private int mScreenHight = 0;
    private WindowManager windowManager;
    private WindowManager.LayoutParams windowParams;

    public WebViewManager() {
    }

    public void init(Context context,boolean show) {
        init(context, null,show);
    }

    public void init(Context context, WebView webView ,boolean show) {
          calculationScreenSize(context);
          createChild(context,webView);
          addToWindow(context,show);
    }

    /**
     * 添加到window中
     */
    private void addToWindow(Context context,boolean show) {
        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        //创建window的参数
        createWindowParams(show);
         //添加到window
        windowManager.addView(contentLayout,windowParams);
    }
    /**
     * 创建子类控件
     */
    private void createChild(Context context,WebView view) {
        /**
         * 作为window的子控件，用于装载WebView
         */
        contentLayout=new LinearLayout(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        contentLayout.setLayoutParams(layoutParams);
        //若是外部传入WebView为空，则构建默认的WebView
        webView=(view==null?WebViewBuilder.create(context):view);
        //设置WebView的大小
        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(mScreenWidth, mScreenHight);
        webView.setLayoutParams(layoutParams2);
        contentLayout.addView(webView);
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
     * @param show
     */
    private void createWindowParams(boolean show){
        windowParams=new WindowManager.LayoutParams();
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
            windowParams.height = mScreenHight;
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
        mScreenHight = dm.heightPixels;
        int barHight = getStatusBarHeight(context);
        mScreenHight = mScreenHight - barHight;
        if (mScreenWidth == 0) {
            mScreenWidth = 1080;
            mScreenHight = 1920;
        }
        //适应android tv
        if (mScreenHight < mScreenWidth) {
            int hight = mScreenHight;
            mScreenHight = mScreenWidth;
            mScreenWidth = hight;
        }
    }
    /**
     * 销毁
     */
    public  void quit(){
        if (windowManager!=null&&webView!=null){
            try {
                windowManager.removeViewImmediate(contentLayout);
                contentLayout.removeView(webView);
                webView.destroy();
                webView=null;
                contentLayout=null;
            }catch (Exception e){

            }
        }
    }
    /**
     *  构建默认的WebView
     */
    private static final class WebViewBuilder{
        public static WebView create(Context context){
            WebView webView=new WebView(context);
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
