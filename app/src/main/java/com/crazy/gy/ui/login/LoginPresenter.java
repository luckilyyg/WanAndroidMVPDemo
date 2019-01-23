package com.crazy.gy.ui.login;

import com.crazy.gy.base.BasePresenter;
import com.crazy.gy.bean.DataResponse;
import com.crazy.gy.bean.User;
import com.crazy.gy.net.ApiServer;
import com.crazy.gy.net.RetrofitManager;
import com.crazy.gy.net.RxSchedulers;

import io.reactivex.functions.Consumer;

/**
 * 作者：Administrator
 * 时间：2019/1/21
 * 功能：
 */
public class LoginPresenter extends BasePresenter<LoginContract.View> implements LoginContract.Presenter {

    public LoginPresenter() {

    }

    @Override
    public void login(String username, String password) {
        RetrofitManager.create(ApiServer.class)
                .login(username, password)
                .compose(RxSchedulers.<DataResponse<User>>applySchedulers())
                .compose(mView.<DataResponse<User>>bindToLife())
                .subscribe(new Consumer<DataResponse<User>>() {
                    @Override
                    public void accept(DataResponse<User> userDataResponse) throws Exception {
                        if (userDataResponse.getErrorCode() == 0) {
                            mView.loginSuccess(userDataResponse.getData());
                        } else {
                            mView.loginerror(String.valueOf(userDataResponse.getErrorMsg()));
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.loginerror(throwable.getMessage());
                    }
                });
    }


}
