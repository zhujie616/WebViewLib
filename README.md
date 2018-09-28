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


