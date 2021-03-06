package com.black.multi.videosample.base.baseadapter;

import android.view.View;

/**
 * Created by wei.
 * Date: 2020/5/24 下午6:04
 * Description:
 */
public interface IRecycleViewCallback<T> {
    void onModelClicked(T bean, View view);

    default void onModelClicked(T bean, View view,int position){}

    default boolean onModelLongClicked(T model, View sharedView) {
        return true;
    }
}

