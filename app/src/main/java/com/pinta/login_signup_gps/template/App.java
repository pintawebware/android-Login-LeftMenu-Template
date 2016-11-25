package com.pinta.login_signup_gps.template;

import android.app.Application;

public class App extends Application {

    private static App instance;

    /**
     * Method to get app instance.
     *
     * @return app instance
     */
    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initLogger();
        initApplication();
    }

    /**
     * Initialize logs in app (Crashlytics, Flurry, etc).
     */
    private void initLogger() {
        if (!BuildConfig.DEBUG) {
            // your code here
        }
    }

    /**
     * Initialize app services.
     */
    private void initApplication() {
        instance = this;
    }
}
