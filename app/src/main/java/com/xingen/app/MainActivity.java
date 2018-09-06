package com.xingen.app;

import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.xingen.androidjslib.JavaScript;
import com.xingen.androidjslib.event.ClickEvent;
import com.xingen.androidjslib.event.Event;
import com.xingen.androidjslib.event.InputEvent;
import com.xingen.androidjslib.listener.Response;
import com.xingen.androidjslib.utils.LogUtils;

public class MainActivity extends AppCompatActivity {
    private WebView webView;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView = findViewById(R.id.main_webview);
        setWebViewConfig();
        webView.loadUrl("https://www.baidu.com/");
    }

    private boolean isFirst=true;

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
        JavaScript.JavaScriptBuilder.init(webView,true);
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
                Log.i(TAG," 页面加载完成 "+url);
                if (isFirst) {
                    isFirst = false;
                    doFirstJs();
                } else {

                }
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

    private void doFirstJs() {
        String elementName = "\"input.se-input\"";
        InputEvent inputEvent = Event.create(Event.TYPE_INPUT);
        inputEvent.setElementName(elementName).setDelayTime(1000);
        inputEvent.setValue("新根").setListener(new Response.InputListener() {
            @Override
            public void inputFailure(InputEvent inputEvent) {
                Toast.makeText(getApplicationContext(),"输入失败",Toast.LENGTH_SHORT).show();
                Log.i(TAG, "  input 输入失败 ");
            }
            @Override
            public void inputSuccess(InputEvent inputEvent) {
                Toast.makeText(getApplicationContext(),"输入成功",Toast.LENGTH_SHORT).show();
                Log.i(TAG, " input 输入成功");
            }
        });
        JavaScript.JavaScriptBuilder.executeEvent(inputEvent, webView);
        ClickEvent clickEvent=Event.create(Event.TYPE_CLICK).setDelayTime(5000).setElementName("\"button.se-bn\"");
        clickEvent.setListener(new Response.ClickListener() {
            @Override
            public void clickFailure(ClickEvent clickEvent) {
                Toast.makeText(getApplicationContext(),"点击失败",Toast.LENGTH_SHORT).show();
                Log.i(TAG, "  click点击失败 ");
            }

            @Override
            public void clickSuccess(ClickEvent clickEvent) {
                Toast.makeText(getApplicationContext(),"点击成功",Toast.LENGTH_SHORT).show();
                Log.i(TAG, "  click点击成功 ");
            }
        });
       JavaScript.JavaScriptBuilder.executeEvent(clickEvent,webView);
    }

}
