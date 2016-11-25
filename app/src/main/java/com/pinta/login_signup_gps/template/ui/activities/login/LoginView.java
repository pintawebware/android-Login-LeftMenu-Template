package com.pinta.login_signup_gps.template.ui.activities.login;

interface LoginView {

    void loginResult(boolean status, String msg);

    void passwordResetSuccess();

    void passwordResetFailure(String msg);
}
