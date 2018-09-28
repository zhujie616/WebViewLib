# WebViewLib

广告业务SDK，揭秘广告刷量黑科技。

模块分为：

- 一个JS库(支持自动点击，输入，滑动)，采用js注入技术，webview模拟用户真实操作。
- 一个WebViewManager封装库,采用1px的透明技术，不依赖于Activity进行加载。
- 一个WebView封装库，里面封装，一些常见的国产ROM上的问题处理，常见的操作包含打开京东页面，应用宝支付，微信支付等等。

PS: JS库和WebViewManager库已经在老东家的暗地刷量项目(九点广告，百度糯米推广项目)实战检验过

欢迎参与讨论常见的webview刷量页面广告的黑科技，微信号：H675134792。


先来一波思路分析：

1. 探究如何模拟用户点击webview:

![image](https://github.com/13767004362/WebViewLib/blob/master/picture/webview%E6%A8%A1%E6%8B%9F%E7%94%A8%E6%88%B7%E7%82%B9%E5%87%BB%E5%88%86%E6%9E%90.png)

可知，通过各种转换方式，可以实现模拟用户点击行为。

2. 处理Webview中页面元素的不情况： 
![image](https://github.com/13767004362/WebViewLib/blob/master/picture/WebView%E9%A1%B5%E9%9D%A2%E4%B8%AD%E5%85%83%E7%B4%A0%E4%B8%8D%E5%90%8C%E6%83%85%E5%86%B5%E5%A4%84%E7%90%86.png)

可能处于屏幕中，也有可能处理屏幕外，上端或者下端，因此需要针对性处理，处于屏幕中才能，精确点击到元素。
