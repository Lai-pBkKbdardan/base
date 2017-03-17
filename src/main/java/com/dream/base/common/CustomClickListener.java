package com.dream.base.common;

import android.view.View;

/**
 * Description: TODO
 * author: Wang
 * date: 3/17/17 14:33
 * email:life_artist@163.com
 * Copyright©2017 by wang. All rights reserved.
 */
public abstract class CustomClickListener implements View.OnClickListener {

    @Override
    public void onClick(View view) {
        onCustomClick(view);
    }

    public abstract void onCustomClick(View view);

}
