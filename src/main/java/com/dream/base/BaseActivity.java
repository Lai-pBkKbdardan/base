package com.dream.base;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.dream.base.interfaze.NetConnectionObserver;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import dream.base.R;

/**
 * Description: App's activity should extend this class
 * author: WangKunHui
 * date: 11/28/16 10:36 AM
 * <p>
 * Copyright©2016 by wang. All rights reserved.
 */
public abstract class BaseActivity extends Activity implements View.OnClickListener, NetConnectionObserver {

    protected String TAG = "BaseActivity";

    /**
     * Activity instance
     */
    protected Activity instance;

    private RelativeLayout rootLayout;

    /**
     * Is transform status bar color? default = true
     */
    protected boolean isChangeStatusBarColor = true;

    /**
     * status bar transform color resource id
     */
    protected int colorRes = 0;

    /**
     * is register net observer
     */
    private boolean isRegisterNetObserver = false;

    /**
     * default loading dialog
     */
    private Dialog defaultLoadingDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TAG = this.getClass().getSimpleName();
        instance = this;

        setStatusBar(isChangeStatusBarColor, colorRes);

        isRegisterNetObserver = isRegisterNetObserver();
        registerNetObserver(isRegisterNetObserver);

        getExtraData();
        initView();

        findViews();
        initData();
        setListeners();
    }

    private void registerNetObserver(boolean isRegisterNetObserver) {
        if (isRegisterNetObserver) {
            BaseApplication.getInstance().addNetObserver(this);
        }
    }

    private boolean isRegisterNetObserver() {
        return true;
    }

    protected abstract void findViews();

    protected abstract void initData();

    protected abstract void setListeners();

    /**
     * @param isChangeStatusBarColor is change color
     * @param colorRes               color res
     */
    private void setStatusBar(boolean isChangeStatusBarColor, int colorRes) {
        if (isChangeStatusBarColor) {
            if (colorRes == 0) {
                initSystemBar(this);
            } else {
                initSystemBar(this, colorRes);
            }
        }
    }

    /**
     * Initialization view
     */
    private void initView() {
        setContentView(R.layout.activity_base_layout);
        rootLayout = (RelativeLayout) findViewById(R.id.base_root);
        int subclassLayoutId = setLayoutRes();
        View inflate = LayoutInflater.from(this).inflate(subclassLayoutId, null);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        inflate.setLayoutParams(params);
        rootLayout.addView(inflate);
    }

    /**
     * set subclass's layout resource id
     *
     * @return resID
     */
    protected abstract int setLayoutRes();

    /**
     * Fetch data form Intent and execute extra options
     */
    protected abstract void getExtraData();

    @Override
    public void onClick(View v) {

    }

    @Override
    public void updateNetStatus(int type) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        BaseApplication.getInstance().removeNetObserver(this);
    }

    protected void showLoadingDialog() {
        showLoadingDialog(0);
    }

    protected void showLoadingDialog(int textRes) {
        if (defaultLoadingDialog == null) {
            defaultLoadingDialog = DialogUtil.getLoadingDialog(instance, textRes);
        }

        defaultLoadingDialog.show();
    }

    protected void dismissLoadingDialog() {
        DialogUtil.dismissDialog(defaultLoadingDialog);
    }

    private void initSystemBar(Activity activity) {

        initSystemBar(activity, R.color.app_default_status_bar_color);

    }

    private void initSystemBar(Activity activity, int colorRes) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(activity, true);
        }
        SystemBarTintManager tintManager = new SystemBarTintManager(activity);
        tintManager.setStatusBarTintEnabled(true);
        // 使用颜色资源
        tintManager.setStatusBarTintResource(colorRes);

    }

    @TargetApi(19)
    private void setTranslucentStatus(Activity activity, boolean on) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }
}
