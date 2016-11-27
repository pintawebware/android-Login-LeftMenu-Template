package com.pinta.login_signup_gps.template.ui.activities.base;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.pinta.login_signup_gps.template.R;
import com.pinta.login_signup_gps.template.tracker.GpsTrackerService;
import com.pinta.login_signup_gps.template.utils.CommonUtils;
import com.pinta.login_signup_gps.template.utils.DialogUtils;
import com.pinta.login_signup_gps.template.utils.L;

public abstract class BaseMainActivity extends AppCompatActivity implements BaseView, GpsTrackerService.OnGpsStatusListener {

    protected static final int PERMISSION_REQUEST_CODE = 101;

    protected Context context;
    protected GpsTrackerService mGpsTrackerService;

    private final ServiceConnection mTrackerConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            // This is called when the connection with the service has been
            // established, giving us the service object we can use to
            // interact with the service. Because we have bound to a explicit
            // service that we know is running in our own process, we can
            // cast its IBinder to a concrete class and directly access it.
            mGpsTrackerService = ((GpsTrackerService.LocalBinder) service).getService();
            // Tell the user about this for our demo.
            onGpsTrackerBound();
        }

        @Override
        public void onServiceDisconnected(ComponentName className) {
            // This is called when the connection with the service has been
            // unexpectedly disconnected -- that is, its process crashed.
            // Because it is running in our same process, we should never
            // see this happen.
            mGpsTrackerService = null;
        }
    };
    private boolean mIsTrackerBound = false;
    private MaterialDialog materialDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        initLayout();
        initViews();
        initListeners();
        initPresenter();
        bindGpsTracker();
    }

    private void initLayout() {
        View v = getContentView();
        setContentView(v);
    }

    protected abstract View getContentView();

    protected void checkGpsPermissionIfNeeded() {
        if (CommonUtils.isMarshmallowOrHigher()) {
            requestPermission();
        }
    }

    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION) || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
            ((TextView)findViewById(R.id.tv_current_location)).setText(R.string.dlg_location_permission_denied);
            DialogUtils.show(context, "GPS permission allows us to access location data. Please allow in App Settings for additional functionality.");
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_CODE);
        }
    }

    private boolean checkPermission() {
        return ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    protected void onGpsTrackerBound() {
        if (mGpsTrackerService != null) {
            mGpsTrackerService.setOnGpsStatusListener(this);
            mGpsTrackerService.getLocation();
            if (checkPermission()) {
                onGpsBind();
            }
        }
    }

    private void bindGpsTracker() {
        // Establish a connection with the service. We use an explicit
        // class name because we want a specific service implementation that
        // we know will be running in our own process (and thus won't be
        // supporting component replacement by other applications).
        bindService(new Intent(context, GpsTrackerService.class),
                mTrackerConnection, Context.BIND_AUTO_CREATE);
        mIsTrackerBound = true;
    }

    private void unbindGpsTracker() {
        if (mIsTrackerBound) {
            // Detach our existing connection.
            unbindService(mTrackerConnection);
            mIsTrackerBound = false;
        }
    }

    protected abstract void onGpsBind();

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbindGpsTracker();
        mGpsTrackerService = null;
    }

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

    @Override
    public void initViews() {

    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void onLocationChanged(Location location) {
        if (mGpsTrackerService != null) {
            ((TextView) findViewById(R.id.tv_current_location)).setText(mGpsTrackerService.getLatitude() + ", " + mGpsTrackerService.getLongitude());
        }
    }

    protected void log(Object o) {
        L.d(o);
    }
}
