# WebViewLib

广告业务SDK，揭秘广告刷量黑科技。

模块分为：

- 一个JS库(支持自动点击，输入，滑动)，采用js注入技术，webview模拟用户真实操作。
- 一个WebViewManager封装库,采用1px的透明技术，不依赖于Activity进行加载。
- 一个WebView封装库，里面封装，一些常见的国产ROM上的问题处理，常见的操作包含打开京东页面，应用宝支付，微信支付等等。

PS: JS库和WebViewManager库已经在老东家的暗地刷量项目(九点广告，百度糯米推广项目)实战检验过

欢迎参与讨论常见的webview刷量页面广告的黑科技，微信号：H675134792。

先来一波思路分析：
---

**1. 探究如何模拟用户点击webview**:

![image](https://github.com/13767004362/WebViewLib/blob/master/picture/webview%E6%A8%A1%E6%8B%9F%E7%94%A8%E6%88%B7%E7%82%B9%E5%87%BB%E5%88%86%E6%9E%90.png)

可知，通过各种转换方式，可以实现模拟用户点击行为。

**2. 处理Webview中页面元素的不情况**： 
![image](https://github.com/13767004362/WebViewLib/blob/master/picture/WebView%E9%A1%B5%E9%9D%A2%E4%B8%AD%E5%85%83%E7%B4%A0%E4%B8%8D%E5%90%8C%E6%83%85%E5%86%B5%E5%A4%84%E7%90%86.png)

可能处于屏幕中，也有可能处理屏幕外，上端或者下端，因此需要针对性处理，处于屏幕中才能，精确点击到元素。

**3.处理WebView悬浮窗，脱离Activity，1px显示**：

![image](https://github.com/13767004362/WebViewLib/blob/master/picture/WebView%E8%84%B1%E7%A6%BBActivity%E8%BF%9B%E8%A1%8C%E5%8A%A0%E8%BD%BD%20(1).png)
可能存在多个广告项目，因此采用并行方式，多个webview同步加载不同任务。

**使用指南**

---

#### **AndroidJsLib的使用**：

**1. 初始化**：

在webview调用`loadUrl()`之前，调用该方法：
```
  JavaScript.JavaScriptBuilder.init(webView, true);
```
该方法用于，添加javaScript交互接口，和log日志配置。

**2.2 添加输入事件**

例如：百度网页输入框，输入`新根`

通过浏览器获取到元素的css选择器：
![image](https://note.youdao.com/favicon.ico)

```
        String elementName = "\"input.se-input\"";
        InputEvent inputEvent = InputEvent.create()
                .setElementName(elementName)
                .setDelayTime(1000)
                .setValue("新根")
                .setListener(new Response.InputListener() {
                    @Override
                    public void inputFailure(InputEvent inputEvent) {
                        String content = "输入失败";
                        Toast.makeText(getApplicationContext(), content, Toast.LENGTH_SHORT).show();
                        Log.i(TAG, "  input 输入失败 ");
                    }

                    @Override
                    public void inputSuccess(InputEvent inputEvent) {
                        Toast.makeText(getApplicationContext(), "输入成功", Toast.LENGTH_SHORT).show();
                        Log.i(TAG, " input 输入成功");
                    }
                });
        JavaScript.JavaScriptBuilder.executeEvent(inputEvent, webView);
```
**2.2 添加点击事件**

通过浏览器获取到元素的css选择器：

![image](https://note.youdao.com/favicon.ico)

点击百度一下的按钮，进行搜索操作。
```
 ClickEvent clickEvent = ClickEvent.create()
                .setDelayTime(5000)
                .setElementName("\"button.se-bn\"")
                .setListener(new Response.ClickListener() {
                    @Override
                    public void clickFailure(ClickEvent clickEvent) {
                        Toast.makeText(getApplicationContext(), "点击失败", Toast.LENGTH_SHORT).show();
                        Log.i(TAG, "  click点击失败 ");
                    }

                    @Override
                    public void clickSuccess(ClickEvent clickEvent) {
                        Toast.makeText(getApplicationContext(), "点击成功", Toast.LENGTH_SHORT).show();
                        Log.i(TAG, "  click点击成功 ");
                    }
                });
        JavaScript.JavaScriptBuilder.executeEvent(clickEvent, webView);
```
**2.3 滚动事件**



例如： 滚动到指定的元素位置，让该元素滚动屏幕中间。


通过浏览器获取到元素的css选择器：

![image](https://note.youdao.com/favicon.ico)


这里滚动到我的个人博客列表中，第九个角标位置。
```
    doScroll("\"ul.colu_author_c>li\"", 8, 1000);

    public void doScroll(String elementName, int index, int delayTime) {
        ScrollEvent scrollEvent = ScrollEvent.create()
                .setElementName(elementName)
                .setDelayTime(delayTime)
                .setPosition(index)
                .setScrollTime(2000)
                .setListener(new Response.ScrollListener() {
                    @Override
                    public void scrollEnd(ScrollEvent scrollEvent) {
                        Log.i(TAG, " 滚动事件结束 ");
                    }

                    @Override
                    public void scrollFailure(ScrollEvent scrollEvent) {
                        Log.i(TAG, " 滚动事件失败 ");
                    }
                });
        JavaScript.JavaScriptBuilder.executeEvent(scrollEvent, webView);
    }
```

