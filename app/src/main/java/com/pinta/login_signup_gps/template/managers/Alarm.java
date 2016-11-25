package com.pinta.login_signup_gps.template.managers;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

import com.pinta.login_signup_gps.template.Consts;
import com.pinta.login_signup_gps.template.tracker.GpsTrackerService;

public class Alarm {

    public static void startInfoAlarm(Context context) {
        long firstTime = SystemClock.elapsedRealtime();

        PendingIntent mAlarmSender = PendingIntent.getService(context,
                0, new Intent(context, GpsTrackerService.class), 0);

        // Schedule the alarm!
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        am.setRepeating(AlarmManager.RTC,
                firstTime, 60 * Consts.ONE_SECOND, mAlarmSender);
    }

    public static void stopInfoAlarm(Context context) {
        PendingIntent mAlarmSender = PendingIntent.getService(context,
                0, new Intent(context, GpsTrackerService.class), 0);
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        am.cancel(mAlarmSender);
    }
}
