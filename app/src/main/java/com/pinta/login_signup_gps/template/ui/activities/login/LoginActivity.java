package com.pinta.login_signup_gps.template.ui.activities.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.pinta.login_signup_gps.template.R;
import com.pinta.login_signup_gps.template.ui.activities.base.BaseActivity;
import com.pinta.login_signup_gps.template.ui.activities.main.MainActivity;
import com.pinta.login_signup_gps.template.ui.activities.signup.SignupActivity;
import com.pinta.login_signup_gps.template.utils.CommonUtils;
import com.pinta.login_signup_gps.template.utils.DialogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends BaseActivity implements LoginView, View.OnClickListener {

    @BindView(R.id.et_email)
    EditText mEtEmail;
    @BindView(R.id.et_password)
    EditText mEtPassword;
    @BindView(R.id.tv_password_reset)
    TextView mTvPasswordReset;
    @BindView(R.id.btn_login)
    Button mBtnLogin;
    @BindView(R.id.tv_go_to_signup)
    TextView mTvGoToSignup;
    private LoginPresenterImpl mPresenter;

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
        mTvPasswordReset.setOnClickListener(this);
        mBtnLogin.setOnClickListener(this);
        mTvGoToSignup.setOnClickListener(this);
    }

    @Override
    public void initPresenter() {
        mPresenter = new LoginPresenterImpl(this);
    }

    @Override
    protected View getContentView() {
        return View.inflate(context, R.layout.activity_login, null);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_password_reset:
                showPasswordResetDialog();
                break;
            case R.id.btn_login:
//                if (CommonUtils.isValidEmail(mEtEmail.getText().toString())) {
//                    mPresenter.doLogin(mEtEmail.getText().toString(), mEtPassword.getText().toString());
//                    showProgress(false); // show progress dialog
//                }
                startMain();
                break;
            case R.id.tv_go_to_signup:
                startSignup();
                break;
        }
    }

    /**
     * Show input dialog to enter a password to reset.
     */
    private void showPasswordResetDialog() {
        DialogUtils.createInputDialog(context,
                R.string.dlg_password_reset,
                null,
                InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS,
                true,
                "",
                "",
                new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                        if (CommonUtils.isValidEmail(input.toString())) {
                            mPresenter.resetPassword(input.toString());
                        }
                    }
                })
                .show();
    }

    /**
     * Go to Signup screen.
     */
    private void startSignup() {
        startActivity(new Intent(context, SignupActivity.class));
        finish();
    }

    @Override
    public void loginResult(boolean status, String msg) {
        hideProgress(); // hide progress dialog
        if (status) {
            // login success
            startMain();
        } else {
            // login failed
        }
    }

    /**
     * Go to Main screen.
     */
    private void startMain() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void passwordResetSuccess() {
        // password was successfully reset
    }

    @Override
    public void passwordResetFailure(String msg) {
        // password reset was failed
        DialogUtils.show(context, msg);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }
}
