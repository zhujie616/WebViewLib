package com.xingen.androidjslib.utils;

import java.util.Random;

/**
 * Author by {xinGen}
 * Date on 2018/9/6 10:01
 */
public class ScreenUtils {
    /**
     *
     * @param pointPixel
     * @param screenPixel
     * @param phonePixel
     * @return
     */
    public static int converseWebScreenPixel(int pointPixel, int screenPixel, int phonePixel) {
        return (int) (pointPixel * 1f / screenPixel * phonePixel);
    }

    /**
     *
     * @param max
     * @return
     */
    public static int random(int max) {
        if (max < 0) {
            return 0;
        }
        Random random = new Random();
        return random.nextInt(max);
    }

}
