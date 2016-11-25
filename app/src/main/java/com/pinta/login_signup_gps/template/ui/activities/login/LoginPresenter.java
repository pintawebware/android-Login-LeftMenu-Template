package com.pinta.login_signup_gps.template.ui.activities.login;

import com.pinta.login_signup_gps.template.ui.activities.base.BasePresenter;

interface LoginPresenter extends BasePresenter {

    void doLogin(String email, String password);

    void resetPassword(String email);
}
