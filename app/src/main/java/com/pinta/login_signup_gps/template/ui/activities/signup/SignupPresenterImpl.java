package com.pinta.login_signup_gps.template.ui.activities.signup;

class SignupPresenterImpl implements SignupPresenter,
        SignupInteractor.OnSignupListener {

    private SignupView mView;
    private SignupInteractorImpl mInteractor;

    SignupPresenterImpl(SignupView mView) {
        this.mView = mView;
        mInteractor = new SignupInteractorImpl();
    }

    @Override
    public void doSignup(String email, String phone, String password) {
        mInteractor.doSignup(email, phone, password, this);
    }

    @Override
    public void onDestroy() {
        mView = null;
    }

    @Override
    public void onSignupResult(boolean status, String error) {
        // send login result
        if (mView != null) {
            mView.signupResult(status, error);
        }
    }
}
