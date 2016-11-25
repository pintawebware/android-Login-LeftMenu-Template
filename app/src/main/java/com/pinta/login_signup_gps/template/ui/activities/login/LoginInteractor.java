package com.pinta.login_signup_gps.template.ui.activities.login;

interface LoginInteractor {

    void doLogin(String email, String password, OnLoginListener onLoginListener);

    void resetPassword(String email, OnResetPassword onResetPasswordListener);

    interface OnLoginListener {
        void onLoginResult(boolean status, String error);
    }

    interface OnResetPassword {
        void onResetPasswordSuccess();

        void onResetPasswordFailure(String msg);
    }
}
