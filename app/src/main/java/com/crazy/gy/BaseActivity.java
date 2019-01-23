package com.crazy.gy;

import android.support.v4.app.SupportActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.crazy.gy.base.BaseContract;
import com.crazy.gy.util.DialogText;
import com.trello.rxlifecycle2.LifecycleTransformer;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity<T extends BaseContract.Basepresenter> extends com.crazy.gy.SupportActivity implements BaseContract.BaseView {

    public T mPresenter;
    public Unbinder unbinder;

    public abstract int getLayoutId();
    public abstract void initInjector();
    public abstract void initView();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        initInjector();
        attachView();
        unbinder = ButterKnife.bind(this);
        initView();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showSuccess(String message) {

    }

    @Override
    public void showFaild(String message) {

    }


    @Override
    public <T> LifecycleTransformer<T> bindToLife() {
        return this.bindToLifecycle();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        destroyView();
    }

    /**
     * 贴上view
     */
    private void attachView() {
        if (mPresenter != null) {
            mPresenter.attachview(this);
        }
    }

    /**
     * 分离view
     */
    private void destroyView() {
        if (mPresenter != null) {
            mPresenter.destroyview();
        }
    }
}
