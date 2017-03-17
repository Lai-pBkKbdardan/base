package com.dream.base.common;

import android.content.Context;
import android.widget.Toast;

/**
 * 自定义Toast
 * 
 * Copyright: 版权所有 (c) 2014 Company: 北京开拓明天科技有限公司
 * 
 * @author wangyang
 * @2014-2-25 上午11:26:29
 */
public class ToastUtil {
	/**
	 * 全局唯一的Toast
	 */
	private static Toast mToast;

	/**
	 * 弹出Toast,该方法防止了重复弹出Toast的情况
	 * 
	 * @param context
	 * @param text
	 * @author wangyang
	 * @2014-2-25 上午11:27:36
	 */
	public static void show(Context context, String text, int length) {
		if (mToast == null) {
			mToast = Toast.makeText(context, text, length);
		} else {
			mToast.setText(text);
			mToast.setDuration(length);
		}
		mToast.show();
	}

	public static void showShort(Context context, String text) {
		show(context, text, Toast.LENGTH_SHORT);
	}

	public static void showLong(Context context, String text) {
		show(context, text, Toast.LENGTH_LONG);
	}
}
