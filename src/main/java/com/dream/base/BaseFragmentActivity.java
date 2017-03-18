package com.dream.base;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.dream.base.common.DialogUtil;
import com.dream.base.interfaze.NetConnectionObserver;
import com.dream.base.widget.CommonTitleView;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import dream.base.R;

/**
 * Description: App's activity should extend this class
 * author: WangKunHui
 * date: 11/28/16 10:36 AM
 * <p>
 * Copyright©2016 by wang. All rights reserved.
 */
public abstract class BaseFragmentActivity extends FragmentActivity implements View.OnClickListener, NetConnectionObserver {

    protected String TAG = "BaseActivity";

    /**
     * Activity instance
     */
    protected Activity instance;

    private LinearLayout rootLayout;

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

    protected CommonTitleView commonTitleView;

    private boolean isShowTitle = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TAG = this.getClass().getSimpleName();
        instance = this;

        setStatusBarColor();
        setStatusBar(isChangeStatusBarColor, colorRes);

        isRegisterNetObserver = isRegisterNetObserver();
        registerNetObserver(isRegisterNetObserver);
        getExtraData();

        isShowTitle = isShowTitleView();

        initView();
        findViews();

        extraAction(savedInstanceState);

        initData();
        setListeners();
    }

    protected boolean isShowTitleView() {
        return false;
    }

    protected void extraAction(Bundle savedInstanceState) {

    }

    private void registerNetObserver(boolean isRegisterNetObserver) {
        if (isRegisterNetObserver) {
            BaseApplication.getInstance().addNetObserver(this);
        }
    }

    protected void setStatusBarColor() {
        isChangeStatusBarColor = true;
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
        rootLayout = (LinearLayout) findViewById(R.id.base_root);

        commonTitleView = (CommonTitleView) findViewById(R.id.common_title);

        if (isShowTitle) {
            commonTitleView.setVisibility(View.VISIBLE);
            findViewById(R.id.common_title_divider).setVisibility(View.VISIBLE);
            initTitleView();
        }

        int subclassLayoutId = setLayoutRes();
        View inflate = LayoutInflater.from(this).inflate(subclassLayoutId, null);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        inflate.setLayoutParams(params);
        rootLayout.addView(inflate);
    }

    protected void initTitleView() {
        commonTitleView.setOnLiftClick(new CommonTitleView.OnTitleClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
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

        initSystemBar(activity, R.color.app_default_status_color);

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
