package com.xingen.app;

import android.content.Context;
import android.net.http.SslError;
import android.support.annotation.Nullable;
import android.util.Log;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.xingen.webviewmanager.WebViewManager;

/**
 * Author by ${HeXinGen}, Date on 2018/10/1.
 */
public class WebViewManagerTest {
    private static final String TAG = WebViewManagerTest.class.getSimpleName();
    private static WebViewManager webViewManager=new WebViewManager();

    public static void test(Context context) {
        //进行初始化,传入false，则视图不显示
        webViewManager.init(context, false);
        WebView webView = webViewManager.addWebView();
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.i(TAG, " 1px透明悬浮窗 技术加载  页面的url是：" + url);
            }
        });
        webView.loadUrl("https://github.com/13767004362");
    }

    public static void destroy(){
       webViewManager.quit();
    }

}
