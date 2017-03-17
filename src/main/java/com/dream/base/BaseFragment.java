package com.dream.base;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dream.base.common.DialogUtil;

/**
 * 各个模块的基础Fragment
 *
 * @author Administrator
 */
public abstract class BaseFragment extends Fragment {

    protected static String TAG = "BaseFragment";

    protected Activity instance;

    private View viewRoot;

    private Dialog mLoadingDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (viewRoot == null) {

            viewRoot = inflater.inflate(setViewLayout(), container, false);

            instance = getActivity();

            findViews(viewRoot);

            initViews();

            setListener();

        } else {
            ViewGroup parent = (ViewGroup) viewRoot.getParent();
            if (parent != null) {
                parent.removeView(viewRoot);
            }
        }

        return viewRoot;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    protected abstract int setViewLayout();

    protected abstract void findViews(View view);

    protected abstract void initViews();

    protected void setListener() {
    }

    protected void showLoadingDialog(int resId) {
        if (mLoadingDialog == null) {
            mLoadingDialog = DialogUtil.getLoadingDialog(getActivity(), resId);
        }
        mLoadingDialog.show();
    }

    protected void showLoadingDialog() {
        if (mLoadingDialog == null) {
            mLoadingDialog = DialogUtil.getLoadingDialog(getActivity());
        }
        mLoadingDialog.show();
    }

    protected void dismissLoadingDialog() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
    }

}
