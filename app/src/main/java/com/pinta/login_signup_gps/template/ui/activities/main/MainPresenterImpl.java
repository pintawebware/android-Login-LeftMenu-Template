package com.pinta.login_signup_gps.template.ui.activities.main;

class MainPresenterImpl implements MainPresenter {

    private MainView mView;
    private MainInteractorImpl mInteractor;

    MainPresenterImpl(MainView mView) {
        this.mView = mView;
        mInteractor = new MainInteractorImpl();
    }

    @Override
    public void onDestroy() {
        mView = null;
    }
}
