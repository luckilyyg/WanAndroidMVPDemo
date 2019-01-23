package com.crazy.gy.ui.register;

import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.blankj.utilcode.util.StringUtils;
import com.crazy.gy.BaseActivity;
import com.crazy.gy.R;
import com.crazy.gy.bean.User;

import com.crazy.gy.ui.login.LoginActivity;
import com.crazy.gy.util.DialogText;
import com.hjq.bar.TitleBar;

import butterknife.BindView;

public class RegisterActivity extends BaseActivity<RegisterPresenter> implements RegisterContract.View {

    @BindView(R.id.tb_register_bar)
    TitleBar tbRegisterBar;
    @BindView(R.id.et_register_phone)
    EditText etRegisterPhone;
    @BindView(R.id.et_register_password1)
    EditText etRegisterPassword1;
    @BindView(R.id.et_register_password2)
    EditText etRegisterPassword2;
    @BindView(R.id.btn_register_commit)
    Button btnRegisterCommit;
    RegisterPresenter presenter;
    public DialogText mDialog;
    @Override
    public int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    public void initInjector() {
        presenter = new RegisterPresenter();
        mDialog = new DialogText(RegisterActivity.this, R.style.MyDialog);
    }

    @Override
    public void initView() {
        btnRegisterCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = etRegisterPhone.getText().toString();
                String password = etRegisterPassword1.getText().toString();
                if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {

                    mDialog.show();
                    showInfo("账号或密码不能为空", 0);
                    return;
                }
                mPresenter.register(username, password,password);
            }
        });
    }


    @Override
    public void registerSuccess(User user) {
        mDialog.show();
        showInfo("注册成功", 1);
    }

    @Override
    public void registerError(String error) {
        mDialog.show();
        showInfo("注册失败", 0);
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
                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                    }
                }
            }, 1000);
        }
    }

}
