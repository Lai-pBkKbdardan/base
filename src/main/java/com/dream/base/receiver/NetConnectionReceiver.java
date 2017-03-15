package com.dream.base.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

import com.dream.base.BaseApplication;
import com.dream.base.common.NetWorkUtil;

/**
 * Description: 网络连接状态的监听
 * author: WangKunHui
 * date: 16/8/3 下午10:54
 * <p/>
 * Copyright©2016 by wang. All rights reserved.
 */
public class NetConnectionReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            int connectionType = NetWorkUtil.getConnectionType(context);

            /**
             * 更改网络状态
             */
            if (BaseApplication.getInstance() != null) {
                BaseApplication.getInstance().notifyNetObserver(connectionType);
            }
        }
    }
}
