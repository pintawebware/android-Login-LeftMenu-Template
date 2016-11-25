package com.pinta.login_signup_gps.template.ui.activities.signup;

import com.pinta.login_signup_gps.template.ui.activities.base.BasePresenter;

interface SignupPresenter extends BasePresenter {

    void doSignup(String email, String phone, String password);
}
