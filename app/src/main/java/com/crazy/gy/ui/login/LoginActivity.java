package com.crazy.gy.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.crazy.gy.BaseActivity;
import com.crazy.gy.MainActivity;
import com.crazy.gy.R;
import com.crazy.gy.bean.User;
import com.crazy.gy.ui.register.RegisterActivity;
import com.crazy.gy.util.Constant;
import com.crazy.gy.util.DialogText;
import com.crazy.gy.view.ClearEditText;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.View {
    @BindView(R.id.et_login_phone)
    ClearEditText etLoginPhone;
    @BindView(R.id.et_login_password)
    ClearEditText etLoginPassword;
    @BindView(R.id.btn_login_commit)
    Button btnLoginCommit;
    @BindView(R.id.tb_login_bar)
    TitleBar tbLoginBar;
    public DialogText mDialog;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initInjector() {
        mPresenter = new LoginPresenter();
        mDialog = new DialogText(LoginActivity.this, R.style.MyDialog);
    }

    @Override
    public void initView() {


        tbLoginBar.setOnTitleBarListener(new OnTitleBarListener() {
            @Override
            public void onLeftClick(View v) {

            }

            @Override
            public void onTitleClick(View v) {

            }

            @Override
            public void onRightClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
        //登录
        btnLoginCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = etLoginPhone.getText().toString();
                String password = etLoginPassword.getText().toString();
                if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
                    mDialog.show();
                    showInfo("账号或密码不能为空", 0);
                    return;
                }
                mPresenter.login(username, password);
            }
        });
    }


    @Override
    public void loginSuccess(User user) {
        SPUtils.getInstance(Constant.SPname).put(Constant.LOGIN, true);
        SPUtils.getInstance(Constant.SPname).put(Constant.USERNAME, user.getUsername());
        SPUtils.getInstance(Constant.SPname).put(Constant.PASSWORD, user.getPassword());
        SPUtils.getInstance(Constant.SPname).put(Constant.LOGINID,user.getId());
        mDialog.show();
        showInfo("登录成功", 1);
    }

    @Override
    public void loginerror(String error) {
        mDialog.show();
        showInfo("登录失败", 0);
    }


    //提示信息的表达
    public void showInfo(final String errorString, final int is) {
        String isString = "";
        if (is == 1) {
            isString = "success";
        }
        if (is == 0) {
            isString = "fail";
        }
        if (mDialog != null) {
            mDialog.setText(errorString, isString);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mDialog.dismiss();

                    if (is == 1) {
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    }
                }
            }, 1000);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
