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
     * 获取多元素中指定角标的元素的区域
     *
     * @param className
     * @param index
     * @return
     */
    public static String findIndexElementAreaJS(int sequence, String className, int index) {
        LogUtils.i(" findIndexElementArea " + className);
        return "javascript: (function() {"
                + "    function getTop(e) {"
                + "        var offset = e.offsetTop;"
                + "        if (e.offsetParent != null) {"
                + "            offset += getTop(e.offsetParent);"
                + "        }"
                + "        return offset;"
                + "    }"
                + "    function getLeft(e) {"
                + "        var offset = e.offsetLeft;"
                + "        if (e.offsetParent != null) {"
                + "            offset += getLeft(e.offsetParent);"
                + "        }"
                + "        return offset;"
                + "    }"
                + "    function findItem(e, i) {"
                + "        if (e) {"
                + "            if (e.length > i) {"
                + "                var item = e[i];"
                + "                if (item.clientWidth > 0 && item.clientHeight > 0) {"
                + "                    return item;"
                + "                }"
                + "            }"
                + "        }"
                + "        return null;"
                + "    }"
                + "    var elementArray = document.querySelectorAll(" + className + ");"
                + "    if (elementArray) {"
                + "        var element = findItem(elementArray, " + index + ");"
                + "        if (element) {"
                + "            JSBehavior.clickArea("+sequence+", "+Response.BEHAVIOR_SUCCESS+", getTop(element), getLeft(element), element.clientWidth, element.clientHeight);"
                +"        } else {"
                +"            JSBehavior.clickArea("+sequence+", "+Response.BEHAVIOR_FAILURE+", 0, 0, 0, 0);"
                +"        }"
                +"    } else {"
                +"        JSBehavior.clickArea("+sequence+", "+Response.BEHAVIOR_FAILURE+", 0, 0, 0, 0);"
                +"    }"
                + "})()";
    }

    /**
     * 往元素中设置值
     *
     * @param elementName
     * @param value
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
