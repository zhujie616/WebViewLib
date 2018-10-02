package com.xingen.androidjslib.utils;

import java.util.Random;

/**
 * Author by {xinGen}
 * Date on 2018/9/6 10:01
 */
public class ScreenUtils {
    /**
     *
     * @param pointPixel 点的px值
     * @param screenPixel 页面的px值
     * @param phonePixel 屏幕的px值
     * @return  转换后屏幕上的px值
     */
    public static int converseWebScreenPixel(int pointPixel, int screenPixel, int phonePixel) {
        return (int) (pointPixel * 1f / screenPixel * phonePixel);
    }

    /**
     *
     * @param max 最大值
     * @return  随机值
     */
    public static int random(int max) {
        if (max < 0) {
            return 0;
        }
        Random random = new Random();
        return random.nextInt(max);
    }

}
