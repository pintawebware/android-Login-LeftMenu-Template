package com.pinta.login_signup_gps.template.ui.activities.signup;

interface SignupInteractor {

    void doSignup(String emailStr, String phoneStr, String passwordStr, final OnSignupListener listener);

    interface OnSignupListener {
        void onSignupResult(boolean status, String error);
    }
}
