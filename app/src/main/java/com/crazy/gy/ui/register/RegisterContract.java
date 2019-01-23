package com.crazy.gy.ui.register;

import com.crazy.gy.base.BaseContract;
import com.crazy.gy.bean.User;

/**
 * 作者：Administrator
 * 时间：2019/1/22
 * 功能：
 */
public interface RegisterContract {

    interface View extends BaseContract.BaseView {
        void registerSuccess(User user);

        void registerError(String error);

    }

    interface Presenter extends BaseContract.Basepresenter<RegisterContract.View> {
        void register(String username, String password, String repassword);
    }
}
