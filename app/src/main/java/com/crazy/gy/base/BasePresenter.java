package com.crazy.gy.base;

/**
 * 作者：Administrator
 * 时间：2019/1/21
 * 功能：
 */
public class BasePresenter<T extends BaseContract.BaseView> implements BaseContract.Basepresenter<T> {
    public T mView;

    @Override
    public void attachview(T view) {
        this.mView = view;
    }

    @Override
    public void destroyview() {
        if (mView != null ) {
            mView = null;
        }
    }
}
