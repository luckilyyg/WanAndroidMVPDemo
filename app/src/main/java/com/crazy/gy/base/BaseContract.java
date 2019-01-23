package com.crazy.gy.base;

import com.trello.rxlifecycle2.LifecycleTransformer;

/**
 * 作者：Administrator
 * 时间：2019/1/21
 * 功能：
 */
public interface BaseContract {

    interface Basepresenter<T extends BaseContract.BaseView> {
        void attachview(T view);

        void destroyview();
    }

    interface BaseView {
        //显示进度中
        void showLoading();

        //隐藏进度
        void hideLoading();

        //显示请求成功
        void showSuccess(String message);

        //失败重试
        void showFaild(String message);
        //绑定生命周期
        <V> LifecycleTransformer<V> bindToLife();
    }
}
