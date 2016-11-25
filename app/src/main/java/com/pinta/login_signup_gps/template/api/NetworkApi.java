package com.pinta.login_signup_gps.template.api;

import com.pinta.login_signup_gps.template.models.LoginModel;
import com.pinta.login_signup_gps.template.models.PasswordResetModel;
import com.pinta.login_signup_gps.template.models.SignupModel;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

public interface NetworkApi {

    String EMAIL = "email";
    String PHONE = "phone";
    String PASSWORD = "password";

    @FormUrlEncoded
    @POST("auth/register")
    Observable<LoginModel> postAuth(@Field(EMAIL) String email,
                                    @Field(PASSWORD) String password);

    @FormUrlEncoded
    @POST("auth/register")
    Observable<PasswordResetModel> postPasswordReset(@Field(EMAIL) String email);

    @FormUrlEncoded
    @POST("auth/register")
    Observable<SignupModel> postSignup(@Field(EMAIL) String email,
                                       @Field(PHONE) String phone,
                                       @Field(PASSWORD) String password);
}
