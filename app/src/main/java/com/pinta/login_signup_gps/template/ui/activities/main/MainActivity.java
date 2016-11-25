package com.pinta.login_signup_gps.template.ui.activities.main;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.pinta.login_signup_gps.template.R;
import com.pinta.login_signup_gps.template.managers.Alarm;
import com.pinta.login_signup_gps.template.ui.activities.base.BaseMainActivity;
import com.pinta.login_signup_gps.template.ui.activities.login.LoginActivity;
import com.pinta.login_signup_gps.template.utils.CommonUtils;
import com.pinta.login_signup_gps.template.utils.DialogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseMainActivity
        implements MainView, NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawer;
    private MainPresenterImpl mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        checkGpsPermissionIfNeeded();
        initDrawer();
    }

    @Override
    protected View getContentView() {
        return View.inflate(context, R.layout.activity_main, null);
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this);
    }

    @Override
    public void initListeners() {
        findViewById(R.id.fab_my_location).setOnClickListener(this);
    }

    @Override
    public void initPresenter() {
        mPresenter = new MainPresenterImpl(this);
    }

    @Override
    protected void onGpsBind() {
        Alarm.startInfoAlarm(context);
    }

    private void initDrawer() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    onGpsBind();
                    Snackbar.make(findViewById(R.id.tv_current_location), R.string.dlg_location_permission_granted, Snackbar.LENGTH_LONG).show();
                } else {
                    ((TextView) findViewById(R.id.tv_current_location)).setText(R.string.dlg_location_permission_denied);
                    Snackbar.make(findViewById(R.id.tv_current_location), R.string.dlg_location_permission_denied, Snackbar.LENGTH_LONG).show();
                }
                break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // check if location permission was allowed from Settings
        if (CommonUtils.isMarshmallowOrHigher()) {
            onGpsTrackerBound();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_settings) {
            startActivityForResult(new Intent(Settings.ACTION_SETTINGS), 0);
        } else if (id == R.id.nav_logout) {
            startLogin();
        }

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Go to Login screen.
     */
    private void startLogin() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab_my_location:
                DialogUtils.show(context, mGpsTrackerService.getLatitude() + ", " + mGpsTrackerService.getLongitude());
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }
}
