package com.crazy.gy.ui.login;

import com.crazy.gy.base.BaseContract;
import com.crazy.gy.bean.User;

/**
 * 作者：Administrator
 * 时间：2019/1/21
 * 功能：
 */
public interface LoginContract {

    interface View extends BaseContract.BaseView {
        void loginSuccess(User user);

        void loginerror(String error);


    }

    interface Presenter extends BaseContract.Basepresenter<LoginContract.View> {
        void login(String username, String password);


    }
}
