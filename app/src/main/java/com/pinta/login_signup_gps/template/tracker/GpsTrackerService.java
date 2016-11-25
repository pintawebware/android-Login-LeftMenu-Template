package com.pinta.login_signup_gps.template.tracker;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;

import com.pinta.login_signup_gps.template.Consts;
import com.pinta.login_signup_gps.template.managers.Alarm;

public class GpsTrackerService extends Service implements LocationListener {

    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters
    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 60 * Consts.ONE_SECOND; // 1 minute
    private final IBinder mBinder = new LocalBinder();
    // Declaring a Location Manager
    private LocationManager mLocationManager;
    // flag for GPS status
    private boolean mIsGPSEnabled = false;
    // flag for network status
    private boolean mIsNetworkEnabled = false;
    // flag for GPS status
    private boolean mCanGetLocation = false;
    private double mLatitude;
    private double mLongitude;
    private Location mLocation;
    private OnGpsStatusListener mOnGpsStatusListener;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (mOnGpsStatusListener != null) {
            mOnGpsStatusListener.onLocationChanged(mLocation);
        }
        return START_NOT_STICKY;
    }

    public Location getLocation() {
        mLocationManager = (LocationManager)
                getSystemService(LOCATION_SERVICE);
        // getting GPS status
        mIsGPSEnabled = mLocationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER);
        // getting network status
        mIsNetworkEnabled = mLocationManager
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (!mIsGPSEnabled && !mIsNetworkEnabled) {
            // no network provider is enabled
        } else {
            mCanGetLocation = true;
            // First get mLocation from Network Provider
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return mLocation;
            }
            if (mIsNetworkEnabled) {
                mLocationManager.requestLocationUpdates(
                        LocationManager.NETWORK_PROVIDER,
                        MIN_TIME_BW_UPDATES,
                        MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                if (mLocationManager != null) {
                    mLocation = mLocationManager
                            .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    if (mLocation != null) {
                        mLatitude = mLocation.getLatitude();
                        mLongitude = mLocation.getLongitude();
                    }
                }
            }
            // if GPS Enabled get lat/long using GPS Services
            if (mIsGPSEnabled) {
                if (mLocation == null) {
                    mLocationManager.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

                    if (mLocationManager != null) {
                        mLocation = mLocationManager
                                .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        if (mLocation != null) {
                            mLatitude = mLocation.getLatitude();
                            mLongitude = mLocation.getLongitude();
                        }
                    }
                }
            }
        }
        return mLocation;
    }

    /**
     * Stop using GPS listener
     * Calling this function will stop using GPS in your app
     */
    public void stopUsingGPS() {
        if (mLocationManager != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            mLocationManager.removeUpdates(this);
        }
    }

    /**
     * Function to get mLatitude
     */
    public double getLatitude() {
        if (mLocation != null) {
            mLatitude = mLocation.getLatitude();
        } else {
            mLatitude = 0;
        }
        // return mLatitude
        return mLatitude;
    }

    /**
     * Function to get mLongitude
     */
    public double getLongitude() {
        if (mLocation != null) {
            mLongitude = mLocation.getLongitude();
        } else {
            mLongitude = 0;
        }
        // return mLongitude
        return mLongitude;
    }

    /**
     * Function to check GPS/wifi enabled
     *
     * @return boolean
     */
    public boolean canGetLocation() {
        return mCanGetLocation;
    }

    @Override
    public void onLocationChanged(Location location) {
        if (mOnGpsStatusListener != null) {
            mOnGpsStatusListener.onLocationChanged(location);
        }
        mLocation = location;
        mLatitude = location.getLatitude();
        mLongitude = location.getLongitude();
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public IBinder onBind(Intent arg0) {
        // TODO: Return the communication channel to the service.
        return mBinder;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        stopUsingGPS();
        Alarm.stopInfoAlarm(this);
    }

    public void setOnGpsStatusListener(OnGpsStatusListener onGpsStatusListener) {
        mOnGpsStatusListener = onGpsStatusListener;
    }

    public interface OnGpsStatusListener {
        void onLocationChanged(Location location);
    }

    public class LocalBinder extends Binder {
        public GpsTrackerService getService() {
            return GpsTrackerService.this;
        }
    }
}
