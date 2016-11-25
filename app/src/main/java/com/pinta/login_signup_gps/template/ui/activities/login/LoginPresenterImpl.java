package com.pinta.login_signup_gps.template.ui.activities.login;

class LoginPresenterImpl implements LoginPresenter,
        LoginInteractor.OnLoginListener, LoginInteractor.OnResetPassword {

    private LoginView mView;
    private LoginInteractorImpl mInteractor;

    LoginPresenterImpl(LoginView mView) {
        this.mView = mView;
        mInteractor = new LoginInteractorImpl();
    }

    @Override
    public void doLogin(String email, String password) {
        mInteractor.doLogin(email, password, this);
    }

    @Override
    public void resetPassword(String email) {
        mInteractor.resetPassword(email, this);
    }

    @Override
    public void onDestroy() {
        mView = null;
    }

    @Override
    public void onLoginResult(boolean status, String error) {
        // send login result
        if (mView != null) {
            mView.loginResult(status, error);
        }
    }

    @Override
    public void onResetPasswordSuccess() {
        // reset password success
        if (mView != null) {
            mView.passwordResetSuccess();
        }
    }

    @Override
    public void onResetPasswordFailure(String msg) {
        // reset password failure
        if (mView != null) {
            mView.passwordResetFailure(msg);
        }
    }
}
