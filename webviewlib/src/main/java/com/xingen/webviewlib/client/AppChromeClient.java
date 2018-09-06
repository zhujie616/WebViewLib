package com.xingen.webviewlib.client;

import android.text.TextUtils;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.xingen.webviewlib.callback.WebViewCallback;

/**
 * Author by {xinGen}
 * Date on 2018/8/8 15:31
 *
 * WebChromeClient主要辅助WebView处理Javascript的对话框、网站图标、网站title、加载进度等
 */
public class AppChromeClient extends WebChromeClient {

    private WebViewCallback callback;

    public AppChromeClient(WebViewCallback callback) {
        this.callback = callback;
    }

    /**
     * 判断标题 title 中是否包含有“error”字段，如果包含“error”字段，则设置加载失败，显示加载失败的视图
     * @param view
     * @param title
     */
    @Override
    public void onReceivedTitle(WebView view, String title) {
        if(!TextUtils.isEmpty(title)&&title.toLowerCase().contains("error")){
            this.callback.errorWeb();
        }
    }
    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        if (newProgress==100){
            if (this.callback!=null&&!this.callback.isRepeat()){
                this.callback.endWeb();
            }
        }
    }

}
