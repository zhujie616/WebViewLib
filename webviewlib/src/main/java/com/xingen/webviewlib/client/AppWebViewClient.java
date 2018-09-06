package com.xingen.webviewlib.client;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.xingen.webviewlib.callback.WebViewCallback;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/**
 * Author by {xinGen}
 * Date on 2018/8/8 15:32
 */
public class AppWebViewClient extends WebViewClient {

    private WebViewCallback callback;
    public AppWebViewClient(WebViewCallback webViewCallback) {
        this.callback=webViewCallback;
    }
    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        if (this.callback!=null){
            this.callback.startWeb();
        }
    }
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        Context context=view.getContext();
        if (url.startsWith("weixin://wap/pay")) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if (intent.resolveActivity(context.getPackageManager()) != null) {
                try {
                    context.startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return true;
        } else if (url.startsWith("alipays://platformapi/startApp")) {
            try {
                Intent intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
                intent.addCategory("android.intent.category.BROWSABLE");
                intent.setComponent(null);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                if (intent.resolveActivity(context.getPackageManager()) != null) {
                    try {
                        context.startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return true;
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        } else   if (url.startsWith("https://wx.tenpay.com")) {
            Map<String, String> extraHeaders = new HashMap<String, String>();
            extraHeaders.put("Referer", "http://pay.matchvs.com/wc3/notify/wechat.do");
            view.loadUrl(url, extraHeaders);
            return true;
        }else if (url.startsWith("http")) {
            view.loadUrl(url);
            return true;
        }
        return super.shouldOverrideUrlLoading(view, url);
    }

    /**
     * 处理网页加载失败时，回调
     * @param view
     * @param errorCode
     * @param description
     * @param failingUrl
     */
    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        super.onReceivedError(view, errorCode, description, failingUrl);
        if (this.callback!=null){
            this.callback.errorWeb();
        }
    }
    /**
     *  android 6.0以上执行，当页面加载失败
     * @param view
     * @param request
     * @param error
     */
    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        super.onReceivedError(view, request, error);
        if (this.callback!=null){
            this.callback.errorWeb();
        }
    }
    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        handler.proceed();
    }
}
