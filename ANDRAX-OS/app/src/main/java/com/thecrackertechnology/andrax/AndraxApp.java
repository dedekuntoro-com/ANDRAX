package com.thecrackertechnology.andrax;

import android.app.Application;
import android.content.Context;

import com.onesignal.OneSignal;

import org.acra.ACRA;
import org.acra.annotation.*;
import org.acra.config.CoreConfigurationBuilder;
import org.acra.data.StringFormat;

import java.io.IOException;

@AcraCore(buildConfigClass = BuildConfig.class)
@AcraMailSender(mailTo = "contact@andrax-pentest.org")
@AcraDialog(resText = R.string.acra_dialog_text, resCommentPrompt = R.string.acra_dialog_comment, resTheme = R.style.AppCompatCrashDialog, resIcon = R.drawable.andraxicon, resTitle = R.string.acra_dialod_title)

public class AndraxApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        OneSignal.startInit(this).inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification).unsubscribeWhenNotificationsAreDisabled(false).init();

        // Call syncHashedEmail anywhere in your app if you have the user's email.
        // This improves the effectiveness of OneSignal's "best-time" notification scheduling feature.
        // OneSignal.syncHashedEmail(userEmail);

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

        // The following line triggers the initialization of ACRA
        ACRA.init(this);
    }
}