package com.pinta.login_signup_gps.template.ui.activities.login;

import android.util.Log;

import com.pinta.login_signup_gps.template.api.NetworkApi;
import com.pinta.login_signup_gps.template.api.RetrofitUtils;
import com.pinta.login_signup_gps.template.models.LoginModel;
import com.pinta.login_signup_gps.template.models.PasswordResetModel;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

class LoginInteractorImpl implements LoginInteractor {

    @Override
    public void doLogin(String email, String password, final OnLoginListener onLoginListener) {
        NetworkApi exploreListService = RetrofitUtils.createApi(NetworkApi.class);
        exploreListService.postAuth(email, password)
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .onErrorReturn(new Func1<Throwable, LoginModel>() {
                    @Override
                    public LoginModel call(Throwable throwable) {
                        Log.d("tag", "onErrorReturn throwable = " + throwable);
                        return null;
                    }
                })
                .doOnNext(new Action1<LoginModel>() {
                    @Override
                    public void call(LoginModel model) {
                        Log.d("tag", "doOnNext");
                    }
                }) // cache
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<LoginModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        // work with error
                        onLoginListener.onLoginResult(false, "");
                    }

                    @Override
                    public void onNext(LoginModel loginModel) {
                        if (loginModel.isStatus()) {
                            // store data if needed
                            onLoginListener.onLoginResult(true, "");
                        } else {
                            // work with error
                            onLoginListener.onLoginResult(false, "");
                        }
                    }
                });
    }

    @Override
    public void resetPassword(String email, final OnResetPassword onResetPasswordListener) {
        NetworkApi exploreListService = RetrofitUtils.createApi(NetworkApi.class);
        exploreListService.postPasswordReset(email)
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .doOnNext(new Action1<PasswordResetModel>() {
                    @Override
                    public void call(PasswordResetModel model) {

                    }
                }) // cache
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<PasswordResetModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        // send error response
                        onResetPasswordListener.onResetPasswordFailure(e.getMessage());
                    }

                    @Override
                    public void onNext(PasswordResetModel loginModel) {
                        if (loginModel.isStatus()) {
                            // send success response
                            onResetPasswordListener.onResetPasswordSuccess();
                        } else {
                            // send error response
                            onResetPasswordListener.onResetPasswordFailure("");
                        }
                    }
                });
    }
}
