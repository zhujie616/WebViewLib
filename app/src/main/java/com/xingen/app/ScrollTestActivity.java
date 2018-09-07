package com.xingen.app;

import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.xingen.androidjslib.JavaScript;
import com.xingen.androidjslib.utils.LogUtils;

/**
 * Author by {xinGen}
 * Date on 2018/9/6 17:47
 */
public class ScrollTestActivity extends AppCompatActivity {
    private WebView webView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        webView=new WebView(this);
        setWebViewConfig();
        setContentView(webView);
        webView.loadUrl("https://blog.csdn.net/hexingen");
    }

    private void setWebViewConfig() {
        WebSettings webSetting = webView.getSettings();
        webSetting.setJavaScriptEnabled(true);
        webSetting.setDomStorageEnabled(true);
        webSetting.setGeolocationEnabled(false);
        webSetting.setSupportZoom(false);
        webSetting.setBuiltInZoomControls(false);
        webSetting.setUseWideViewPort(true);
        webSetting.setSupportMultipleWindows(false);
        webSetting.setLoadsImagesAutomatically(true);
        JavaScript.JavaScriptBuilder.init(webView, true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            try {
                webView.getSettings().setMixedContentMode(
                        WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
        webView.setWebViewClient(new WebViewClient() {
            /**
             * WebView ssl 访问证书出错，handler.cancel()取消加载，handler.proceed()对然错误也继续加载
             *
             * @param view
             * @param handler
             * @param error
             */
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                LogUtils.i("   " + errorCode + " " + description + " " + failingUrl);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

            }
        });
        webView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                LogUtils.i(" WebView touch 事件 " + event.getX() + " " + event.getY());
                return false;
            }
        });
    }
}
