package com.dream.base.interfaze;

/**
 * Description: observer
 * author: WangKunHui
 * date: 11/28/16 11:20 AM
 * <p>
 * Copyright©2016 by wang. All rights reserved.
 */
public interface NetConnectionObserver {

    /**
     * 通知观察者更改状态
     *
     * @param type
     */
    public void updateNetStatus(int type);
}
