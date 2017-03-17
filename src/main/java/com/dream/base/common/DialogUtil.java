package com.dream.base.common;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import dream.base.R;

/**
 * Creator : WangKH
 * Date : 2016/6/12.
 * Desc :
 */
public class DialogUtil {

    public static Dialog getLoadingDialog(Context context) {
        return getLoadingDialog(context, false, 0);
    }

    public static Dialog getLoadingDialog(Context context, int textRes) {
        return getLoadingDialog(context, false, textRes);
    }

    /**
     * 获取loading框
     *
     * @param context
     * @return
     */
    public static Dialog getLoadingDialog(Context context, boolean isCanCancel, int textRes) {

        Dialog mDialog = new Dialog(context, R.style.dialog);
        LayoutInflater mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View reNameView = mLayoutInflater.inflate(R.layout.dialog_loading, null);

        TextView show = (TextView) reNameView.findViewById(R.id.tv_loading_text);
        if (textRes == 0) {
            show.setVisibility(View.GONE);
        } else {
            show.setVisibility(View.VISIBLE);
            show.setText(textRes);
        }

        mDialog.setContentView(reNameView);
        mDialog.setCanceledOnTouchOutside(isCanCancel);
        mDialog.show();
        return mDialog;
    }


    public static void dismissDialog(Dialog dialog) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}
