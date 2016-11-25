package com.pinta.login_signup_gps.template.ui.activities.signup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.pinta.login_signup_gps.template.R;
import com.pinta.login_signup_gps.template.ui.activities.base.BaseActivity;
import com.pinta.login_signup_gps.template.ui.activities.login.LoginActivity;
import com.pinta.login_signup_gps.template.utils.CommonUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignupActivity extends BaseActivity implements SignupView, View.OnClickListener {

    @BindView(R.id.et_email)
    EditText mEtEmail;
    @BindView(R.id.et_phone)
    EditText mEtPhone;
    @BindView(R.id.et_password)
    EditText mEtPassword;
    @BindView(R.id.tv_go_to_login)
    TextView mTvGoToLogin;
    @BindView(R.id.btn_signup)
    Button mBtnSignup;

    private SignupPresenterImpl mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this);
    }

    @Override
    public void initListeners() {
        mTvGoToLogin.setOnClickListener(this);
        mBtnSignup.setOnClickListener(this);
    }

    @Override
    public void initPresenter() {
        mPresenter = new SignupPresenterImpl(this);
    }

    @Override
    protected View getContentView() {
        return View.inflate(context, R.layout.activity_signup, null);
    }

    @Override
    public void signupResult(boolean status, String msg) {
        hideProgress(); // hide progress dialog
        if (status) {
            // signup success
        } else {
            // signup failed
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_go_to_login:
                startLogin();
                break;
            case R.id.btn_signup:
                if (CommonUtils.isValidEmail(mEtEmail.getText().toString())) {
                    mPresenter.doSignup(mEtEmail.getText().toString(), mEtPhone.getText().toString(), mEtPassword.getText().toString());
                    showProgress(false); // show progress dialog
                }
                break;
        }
    }

    /**
     * Go to Login screen.
     */
    private void startLogin() {
        startActivity(new Intent(context, LoginActivity.class));
        finish();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }
}
