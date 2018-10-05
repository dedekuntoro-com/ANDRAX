package com.thecrackertechnology.andrax;

import android.app.Application;

import com.onesignal.OneSignal;

import java.io.IOException;

public class AndraxApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        OneSignal.startInit(this).inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification).unsubscribeWhenNotificationsAreDisabled(false).init();

        // Call syncHashedEmail anywhere in your app if you have the user's email.
        // This improves the effectiveness of OneSignal's "best-time" notification scheduling feature.
        // OneSignal.syncHashedEmail(userEmail);

    }
}