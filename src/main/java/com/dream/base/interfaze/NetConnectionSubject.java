package com.dream.base.interfaze;

/**
 * Description: observer subject
 * author: WangKunHui
 * date: 11/28/16 11:19 AM
 * <p>
 * Copyright©2016 by wang. All rights reserved.
 */
public interface NetConnectionSubject {

    /**
     * 注册观察者
     *
     * @param observer
     */
    public void addNetObserver(NetConnectionObserver observer);

    /**
     * 移除观察者
     *
     * @param observer
     */
    public void removeNetObserver(NetConnectionObserver observer);

    /**
     * 状态更新通知
     *
     * @param type
     */
    public void notifyNetObserver(int type);
}
