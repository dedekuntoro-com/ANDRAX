package com.thecrackertechnology.andrax;

import android.app.Application;
import android.content.Context;

import com.onesignal.OneSignal;

import org.acra.ACRA;
import org.acra.annotation.*;

@AcraCore(buildConfigClass = BuildConfig.class)
@AcraMailSender(mailTo = "contact@andrax-pentest.org")
@AcraDialog(resText = R.string.acra_dialog_text, resCommentPrompt = R.string.acra_dialog_comment, resTheme = R.style.AppCompatCrashDialog, resIcon = R.drawable.andraxicon, resTitle = R.string.acra_dialod_title)

public class AndraxApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        OneSignal.startInit(this).inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification).unsubscribeWhenNotificationsAreDisabled(false).init();


    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);


        ACRA.init(this);
    }
}
