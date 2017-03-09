package com.dream.base;

import android.app.Application;

import com.dream.base.common.NetWorkUtil;
import com.dream.base.interfaze.NetConnectionObserver;
import com.dream.base.interfaze.NetConnectionSubject;

import java.util.ArrayList;
import java.util.List;


/**
 * Description: App's application should extend this class
 * author: WangKunHui
 * date: 11/28/16 10:34 AM
 * <p>
 * Copyright©2016 by wang. All rights reserved.
 */
public abstract class BaseApplication extends Application implements NetConnectionSubject {

    protected static BaseApplication instance;

    private int currentNetType = -1;

    private List<NetConnectionObserver> observers = new ArrayList<>();

//    private OkHttpClient httpCline;

    public static BaseApplication getInstance() {
        return instance;
    }

    /**
     * current net connection type
     *
     * @return
     */
    public int getCurrentNetType() {
        return currentNetType;
    }

//    public OkHttpClient getHttpCline() {
//        return httpCline;
//    }

    /**
     * current net connection status
     *
     * @return
     */
    public boolean isNetConnection() {
        return currentNetType == NetWorkUtil.NET_NO_CONNECTION ? false : true;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
//        ImageLoader.getInstance().init(getConfig(this));

        initHttp();

        currentNetType = NetWorkUtil.getConnectionType(this);

    }

    protected void initHttp() {
//        httpCline = new OkHttpClient();
//        /**
//         * 设置5秒的链接超时
//         */
//        httpCline.newBuilder().connectTimeout(HttpConfig.httpTimeOut, TimeUnit.MILLISECONDS)
//                .readTimeout(HttpConfig.httpTimeOut, TimeUnit.MILLISECONDS)//设置读取超时时间
//                .writeTimeout(HttpConfig.httpTimeOut, TimeUnit.MILLISECONDS);//设置写的超时时间
    }

    @Override
    public void addNetObserver(NetConnectionObserver observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    @Override
    public void removeNetObserver(NetConnectionObserver observer) {
        if (observers != null && observers.contains(observer)) {
            observers.remove(observer);
        }
    }

    @Override
    public void notifyNetObserver(int type) {

        /**
         * 避免多次发送相同的网络状态
         */
        if (currentNetType == type) {
            return;
        } else {
            currentNetType = type;
            if (observers != null && observers.size() > 0) {
                for (NetConnectionObserver observer : observers) {
                    observer.updateNetStatus(type);
                }
            }
        }
    }
}
