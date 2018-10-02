package com.xingen.androidjslib.injection;

import com.xingen.androidjslib.listener.Response;
import com.xingen.androidjslib.utils.LogUtils;

/**
 * Author by {xinGen}
 * Date on 2018/9/6 10:19
 */
public class JSInjection {

    public static String collectScreenInfoJS() {
        LogUtils.i("collectScreenInfoJs");
        return "javascript:(function() {"
                + "    JSBehavior.setInnerScreenInfo(document.documentElement.clientWidth, document.documentElement.clientHeight);"
                + "})()";
    }

    /**
     *  获取到指定元素的区域范围
     * @param  sequence  序列号
     * @param className 元素的css选择器
     * @param index  角标
     * @return  执行的js脚本
     */
    public static String findIndexElementAreaJS(int sequence, String className, int index) {
        LogUtils.i(" findIndexElementArea " + className);
        return  "javascript: (function() {"
                +"    function getTop(e) {"
                +"        var offset = e.offsetTop;"
                +"        if (e.offsetParent != null) {"
                +"            offset += getTop(e.offsetParent);"
                +"        }"
                +"        return offset;"
                +"    }"
                +"    function findItem(e, i) {"
                +"        if (e) {"
                +"            if (e.length > i) {"
                +"                var item = e[i];"
                +"                if (item.clientWidth > 0 && item.clientHeight > 0) {"
                +"                    return item;"
                +"                }"
                +"            }"
                +"        }"
                +"        return null;"
                +"    }"
                +"    function getLeft(e) {"
                +"        var offset = e.offsetLeft;"
                +"        if (e.offsetParent != null) {"
                +"            offset += getLeft(e.offsetParent);"
                +"        }"
                +"        return offset;"
                +"    }"
                +"    function random(min, max) {"
                +"        return Math.round(Math.random() * (max - min)) + min;"
                +"    }"
                +"    var elementList = document.querySelectorAll("+className+");"
                +"    if (elementList) {"
                +"        var index = "+index+";"
                +"        if (index >= elementList.length) {"
                +"            index = random(0, elementList.length - 1);"
                +"        }"
                +"        var element = elementList[index];"
                +"        if (element) {"
                +"            var paddingTop = getTop(element);"
                +"            var paddingleft = getLeft(element);"
                +"            var windowHeight = window.outerHeight;"
                +"            var windowWidth = window.outerWidth;"
                +"            if (paddingTop >= windowHeight) {"
                +"                window.scrollTo(0, paddingTop - windowHeight);"
                +"                window.scrollBy(0, Math.floor(windowHeight / 2));"
                +"                var scrollDistance = window.scrollY;"
                +"                JSBehavior.clickArea("+sequence+", "+Response.BEHAVIOR_SUCCESS+", paddingTop - scrollDistance, paddingleft, element.clientWidth, element.clientHeight, windowWidth, windowHeight);"
                +"            } else {"
                +"                window.scrollTo(0, 0);"
                +"                JSBehavior.clickArea("+sequence+", "+Response.BEHAVIOR_SUCCESS+", paddingTop, paddingleft, element.clientWidth, element.clientHeight, windowWidth, windowHeight);"
                +"            }"
                +"        } else {"
                +"            JSBehavior.clickArea("+sequence+", "+Response.BEHAVIOR_FAILURE+", 0, 0, 0, 0, 0, 0);"
                +"        }"
                +"    } else {"
                +"        JSBehavior.clickArea("+sequence+", "+Response.BEHAVIOR_FAILURE+", 0, 0, 0, 0, 0, 0);"
                +"    }"
                +"})()";
    }

    /**
     * 滚动到指定元素，让该元素处于界面中间位置
     * @param sequence  序列号
     * @param elementName  元素的css选择器
     * @param index   角标
     * @return  js 脚本
     */
    public  static String scrollElementJS(int sequence,String elementName,int index ){
       return "javascript: (function() {"
               + "    function getTop(e) {"
               +"        var offset = e.offsetTop;"
               +"        if (e.offsetParent != null) {"
               +"            offset += getTop(e.offsetParent);"
               +"        }"
               +"        return offset;"
               +"    }"
               +"    function findItem(e, i) {"
               +"        if (e) {"
               +"            if (e.length > i) {"
               +"                var item = e[i];"
               +"                if (item.clientWidth > 0 && item.clientHeight > 0) {"
               +"                    return item;"
               +"                }"
               +"            }"
               +"        }"
               +"        return null;"
               +"    }"
               +"    function random(min, max) {"
               +"        return Math.round(Math.random() * (max - min)) + min;"
               +"    }"
               +"    var elementList = document.querySelectorAll("+elementName+");"
               +"    if (elementList) {"
               +"        var index = "+index+";"
               +"        if (index >= elementList.length) {"
               +"            index = random(0, elementList.length - 1);"
               +"        }"
               +"        var element = elementList[index];"
               +"        if (element) {"
               +"            var paddingTop = getTop(element);"
               +"            var windowHeight = window.outerHeight;"
               +"            var windowWidth = window.outerWidth;"
               +"            var start_x, start_y, end_x, end_y;"
               +"            if (paddingTop > windowHeight) {"
               +"                window.scrollTo(0, paddingTop - windowHeight);"
               +"                start_x = windowWidth / 2 + random(5, 10);"
               +"                end_x = start_x;"
               +"                start_y = Math.floor(windowHeight * 0.75) - random(1, 10);"
               +"                end_y = start_y - Math.floor(windowHeight / 2);"
               +"                JSBehavior.scrollScreen("+sequence+", "+Response.BEHAVIOR_SUCCESS+", start_x, start_y, end_x, end_y, windowWidth, windowHeight);"
               +"            } else {"
               +"                var scrollDistance = window.scrollY;"
               +"                if (paddingTop < scrollDistance) {"
               +"                    window.scrollTo(0, paddingTop);"
               +"                    start_x = windowWidth / 2 + random(5, 10);"
               +"                    end_x = start_x;"
               +"                    start_y = Math.floor(windowHeight * 0.25) + random(1, 10);"
               +"                    end_y = start_y + Math.floor(windowHeight / 2);"
               +"                    JSBehavior.scrollScreen("+sequence+", "+Response.BEHAVIOR_SUCCESS+", start_x, start_y, end_x, end_y, windowWidth, windowHeight);"
               +"                } else {"
               +"                    if (paddingTop <= Math.floor(windowHeight / 2)) {"
               +"                        start_x = windowWidth / 2 + random(5, 10);"
               +"                        end_x = start_x;"
               +"                        start_y = Math.floor(windowHeight * 0.25) + random(1, 10);"
               +"                        end_y = start_ + scrollDistance;"
               +"                        JSBehavior.scrollScreen("+sequence+", "+Response.BEHAVIOR_SUCCESS+", start_x, start_y, end_x, end_y, windowWidth, windowHeight);"
               +"                    } else {"
               +"                        window.scrollTo(0, 0);"
               +"                        start_x = windowWidth / 2 + random(5, 10);"
               +"                        end_x = start_x;"
               +"                        start_y = Math.floor(windowHeight * 0.75) - random(1, 10);"
               +"                        end_y = start_y - (paddingTop - Math.floor(windowHeight / 2));"
               +"                        JSBehavior.scrollScreen("+sequence+", "+Response.BEHAVIOR_SUCCESS+", start_x, start_y, end_x, end_y, windowWidth, windowHeight);"
               +"                    }"
               +"                }"
               +"            }"
               +"        } else {"
               +"            JSBehavior.scrollScreen("+sequence+", "+Response.BEHAVIOR_FAILURE+", 0, 0, 0, 0, 0, 0);"
               +"        }"
               +"    } else {"
               +"        JSBehavior.scrollScreen("+sequence+", "+Response.BEHAVIOR_FAILURE+", 0, 0, 0, 0, 0, 0);"
               +"    }"
               +"})()";
    }
    /**
     * 往元素中设置值
     * @param  sequence 序列号
     * @param elementName 元素的css选择器
     * @param value  输入的值
     * @return  执行的js脚本
     */
    public static String inputValueJS(int sequence, String elementName, String value) {
        LogUtils.i(" inputValue " + elementName + " " + value);
        return "javascript: (function() {"
                + "    var element = document.querySelector(" + elementName + ");"
                + "    if (element) {"
                + "        element.value = " + "\"" + value + "\"" + ";"
                + "        JSBehavior.inputResult("+sequence+", " + Response.BEHAVIOR_SUCCESS + ");"
                + "    } else {"
                + "        JSBehavior.inputResult("+sequence+", " + Response.BEHAVIOR_FAILURE + ");"
                + "    }"
                + "})()";

    }
}
