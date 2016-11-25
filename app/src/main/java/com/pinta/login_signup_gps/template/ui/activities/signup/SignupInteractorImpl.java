package com.pinta.login_signup_gps.template.ui.activities.signup;

import com.pinta.login_signup_gps.template.api.NetworkApi;
import com.pinta.login_signup_gps.template.api.RetrofitUtils;
import com.pinta.login_signup_gps.template.models.SignupModel;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

class SignupInteractorImpl implements SignupInteractor {

    @Override
    public void doSignup(String email, String phone, String password, final OnSignupListener onSignupListener) {
        NetworkApi exploreListService = RetrofitUtils.createApi(NetworkApi.class);
        exploreListService.postSignup(email, phone, password)
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .doOnNext(new Action1<SignupModel>() {
                    @Override
                    public void call(SignupModel model) {

                    }
                }) // cache
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<SignupModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        // work with error
                        onSignupListener.onSignupResult(false, "");
                    }

                    @Override
                    public void onNext(SignupModel loginModel) {
                        if (loginModel.isStatus()) {
                            // store data if needed
                            onSignupListener.onSignupResult(true, "");
                        } else {
                            // work with error
                            onSignupListener.onSignupResult(false, "");
                        }
                    }
                });
    }
}
