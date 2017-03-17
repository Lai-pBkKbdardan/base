package com.dream.base.common;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class ScreenUtils {

    private static final String TAG = "ScreenUtils";

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue
     * @param pxValue （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     * @param spValue （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 获取设备状态栏高度
     *
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier(
                "status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 获取并设置屏幕尺寸
     *
     * @return
     */
    public static void setScreenSize(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);

//        Constant.screenWidth = dm.widthPixels;
//        Constant.screenHeight = dm.heightPixels;
//
//        Log.i(TAG, "screenWidth : " + Constant.screenWidth + "    screenHeight :" + Constant.screenHeight);

    }

    /**
     * 获取一个View在其父布局的中点的坐标
     *
     * @param view 要计算的view
     * @return
     */
    public static Point getViewMiddlePointInParent(View view) {

        int left = view.getLeft();
        int top = view.getTop();
        int right = view.getRight();
        int bottom = view.getBottom();

        int middleX = left + (right - left) / 2;
        int middleY = top + (bottom - top) / 2;

        return new Point(middleX, middleY);
    }

    /**
     * 获取一个View在屏幕中点的坐标
     *
     * @param view 要计算的view
     * @return
     */
    public static Point getViewMiddlePointInScreen(View view) {

        int[] location = new int[2];

        view.getLocationOnScreen(location);

        int left = view.getLeft();
        int top = view.getTop();
        int right = view.getRight();
        int bottom = view.getBottom();

        int middleX = location[0] + (right - left) / 2;
        int middleY = location[1] + (bottom - top) / 2;

        return new Point(middleX, middleY);
    }

    /**
     * 获取iew在屏幕中的坐标（左上角）已经长宽
     *
     * @param view
     * @return list size=4 依次为X，Y，Width，Height
     */
    public static List<Integer> getViewPositionAndSize(View view) {

        List<Integer> list = new ArrayList<Integer>();
        int[] location = new int[2];
        view.getLocationOnScreen(location);

        int left = view.getLeft();
        int top = view.getTop();
        int right = view.getRight();
        int bottom = view.getBottom();

        int width = right - left;
        int height = bottom - top;

        list.add(location[0]);
        list.add(location[1]);
        list.add(width);
        list.add(height);

        return list;

    }

}
