package com.pinta.login_signup_gps.template.ui.activities.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;

import com.pinta.login_signup_gps.template.utils.L;

public abstract class BaseActivity extends AppCompatActivity implements BaseView {

    protected Context context;

    private MaterialDialog materialDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        initLayout();
        initViews();
        initListeners();
        initPresenter();
    }

    private void initLayout() {
        View v = getContentView();
        setContentView(v);
    }

    protected abstract View getContentView();

    protected void showProgress(boolean cancellable) {
        materialDialog = new MaterialDialog.Builder(context)
                .content("loading_message")
                .cancelable(cancellable)
                .progress(true, 0)
                .show();
    }

    public void hideProgress() {
        if (materialDialog != null) {
            materialDialog.cancel();
        }
    }

    protected void log(Object o) {
        L.d(o);
    }
}
