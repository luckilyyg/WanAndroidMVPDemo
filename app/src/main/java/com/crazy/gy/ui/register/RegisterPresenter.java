package com.crazy.gy.ui.register;

import com.crazy.gy.base.BasePresenter;
import com.crazy.gy.bean.DataResponse;
import com.crazy.gy.bean.User;
import com.crazy.gy.net.ApiServer;
import com.crazy.gy.net.RetrofitManager;
import com.crazy.gy.net.RxSchedulers;

import io.reactivex.functions.Consumer;

/**
 * 作者：Administrator
 * 时间：2019/1/22
 * 功能：
 */
public class RegisterPresenter extends BasePresenter<RegisterContract.View> implements RegisterContract.Presenter {

    public RegisterPresenter() {

    }

    @Override
    public void register(String username, String password, String repassword) {
        RetrofitManager.create(ApiServer.class)
                .register(username, password,repassword)
                .compose(RxSchedulers.<DataResponse<User>>applySchedulers())
                .compose(mView.<DataResponse<User>>bindToLife())
                .subscribe(new Consumer<DataResponse<User>>() {
                    @Override
                    public void accept(DataResponse<User> userDataResponse) throws Exception {
                        if (userDataResponse.getErrorCode() == 0) {
                            mView.registerSuccess(userDataResponse.getData());
                        } else {
                            mView.registerError(String.valueOf(userDataResponse.getErrorMsg()));
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.registerError(throwable.getMessage());
                    }
                });
    }
}
