/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package andrax.axterminal;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.*;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;
import android.app.Notification;
import android.app.PendingIntent;

import andrax.axterminal.emulatorview.TermSession;

import andrax.axterminal.compat.ServiceForegroundCompat;
import andrax.axterminal.util.SessionList;
import andrax.axterminal.util.TermSettings;
import axterminal.libtermexec.v1.ITerminal;

import java.io.FileDescriptor;
import java.io.IOException;
import java.lang.Process;
import java.util.UUID;

public class TermService extends Service implements TermSession.FinishCallback
{
    /* Parallels the value of START_STICKY on API Level >= 5 */
    private static final int COMPAT_START_STICKY = 1;

    private static final int RUNNING_NOTIFICATION = 1;
    private ServiceForegroundCompat compat;

    private SessionList mTermSessions;

    public class TSBinder extends Binder {
        TermService getService() {
            Log.i("TermService", "Activity binding to service");
            return TermService.this;
        }
    }
    private final IBinder mTSBinder = new TSBinder();

    @Override
    public void onStart(Intent intent, int flags) {
    }

    /* This should be @Override if building with API Level >=5 */
    @SuppressLint("WrongConstant")
    public int onStartCommand(Intent intent, int flags, int startId) {
        return COMPAT_START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        if (TermExec.SERVICE_ACTION_V1.equals(intent.getAction())) {
            Log.i("TermService", "Outside process called onBind()");

            return new RBinder();
        } else {
            Log.i("TermService", "Activity called onBind()");

            return mTSBinder;
        }
    }

    @Override
    public void onCreate() {

        // should really belong to the Application class, but we don't use one...
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = prefs.edit();
        String defValue = getDir("HOME", MODE_PRIVATE).getAbsolutePath();
        String homePath = prefs.getString("home_path", "/data/data/com.thecrackertechnology.andrax/ANDRAX");
        editor.putString("home_path", homePath);
        editor.commit();


        compat = new ServiceForegroundCompat(this);
        mTermSessions = new SessionList();

        /* Put the service in the foreground. */
        //Notification notification = new Notification(R.drawable.ic_stat_service_notification_icon, getText(R.string.service_notify_text), System.currentTimeMillis());
        //notification.flags |= Notification.FLAG_ONGOING_EVENT;
        //Intent notifyIntent = new Intent(this, Term.class);
        //notifyIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notifyIntent, 0);
        //notification.setLatestEventInfo(this, getText(R.string.application_terminal), getText(R.string.service_notify_text), pendingIntent);
        //compat.startForeground(RUNNING_NOTIFICATION, notification);


        Intent notificationIntent = new Intent(getApplicationContext(), andrax.axterminal.Term.class);
        //notificationIntent.setData(Uri.parse("http://www.wgn.com"));
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        int notificationId = 1;
        String channelId = "channel-01";
        String channelName = "AX-TERMINAL";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;

        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(
                    channelId, channelName, importance);
            notificationManager.createNotificationChannel(mChannel);
        }

        Notification notification = new NotificationCompat.Builder(this)
                .setCategory(Notification.CATEGORY_PROMO)
                .setContentTitle("AX-TERMINAL Running")
                .setContentText("Running in background")
                .setSubText("Close all windows to stop")
                .setChannelId(channelId)
                .setPriority(importance)
                .setSmallIcon(R.drawable.axterminalnotification)
                .setAutoCancel(false)
                .setOngoing(true)
                .setContentIntent(contentIntent)
                .build();
        //NotificationManager notificationManager =
          //      (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(1, notification);

        startForeground(1, notification);

        //SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        //SharedPreferences.Editor editorhack = sharedPref.edit();
        //editorhack.clear().apply();

        try {

            //Process changedata01 = Runtime.getRuntime().exec("su -c /system/xbin/busybox chmod 777 /data/");
            //changedata01.waitFor();

            //Process changedata02 = Runtime.getRuntime().exec("su -c /system/xbin/busybox chmod 777 /data/data/");
            //changedata02.waitFor();

            //Process changedata03 = Runtime.getRuntime().exec("su -c /system/xbin/busybox chmod 777 /data/data/com.thecrackertechnology.andrax/");
            //changedata03.waitFor();

            //Process changedata04 = Runtime.getRuntime().exec("su -c /system/xbin/busybox chmod 777 /data/data/com.thecrackertechnology.andrax/ANDRAX/");
            //changedata04.waitFor();

            Process mountreadwrite = Runtime.getRuntime().exec("su -c /system/xbin/busybox mount -o rw,remount /system /system");
            mountreadwrite.waitFor();

            int mountreadwriteresult = mountreadwrite.exitValue();

            if(mountreadwriteresult !=0) {

                Process mountreadwrite02 = Runtime.getRuntime().exec("su -c /system/xbin/busybox mount -o rw,remount /system");
                mountreadwrite02.waitFor();

            }

            Process installdns = Runtime.getRuntime().exec("su -c printf \"#Created by ANDRAX\\n\\nnameserver 209.244.0.3\\nnameserver 209.244.0.4\\n\" > /etc/resolv.conf");
            installdns.waitFor();

            Process chmoddns = Runtime.getRuntime().exec("su -c chmod -R 777 /etc/resolv.conf");
            chmoddns.waitFor();

            Process chmoddevnukk = Runtime.getRuntime().exec("su -c chmod -R 777 /dev/null");
            chmoddevnukk.waitFor();


            //Process sethostname = Runtime.getRuntime().exec("su -c hostname ANDRAX-Mobile-Pentest");
            //sethostname.waitFor();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.d(TermDebug.LOG_TAG, "TermService started");
        return;
    }

    @Override
    public void onDestroy() {

        CancelNotification(getApplicationContext(), 1);

        compat.stopForeground(true);
        for (TermSession session : mTermSessions) {
            /* Don't automatically remove from list of sessions -- we clear the
             * list below anyway and we could trigger
             * ConcurrentModificationException if we do */
            session.setFinishCallback(null);
            session.finish();
        }
        mTermSessions.clear();
        return;
    }



    public SessionList getSessions() {
        return mTermSessions;
    }

    public void onSessionFinish(TermSession session) {
        mTermSessions.remove(session);
    }

    @SuppressWarnings("deprecation")
    private final class RBinder extends ITerminal.Stub implements IBinder {
        @Override
        public IntentSender startSession(final ParcelFileDescriptor pseudoTerminalMultiplexerFd,
                                         final ResultReceiver callback) {
            final String sessionHandle = UUID.randomUUID().toString();

            // distinct Intent Uri and PendingIntent requestCode must be sufficient to avoid collisions
            final Intent switchIntent = new Intent(RemoteInterface.PRIVACT_OPEN_NEW_WINDOW)
                    .setData(Uri.parse(sessionHandle))
                    .addCategory(Intent.CATEGORY_DEFAULT)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    .putExtra(RemoteInterface.PRIVEXTRA_TARGET_WINDOW, sessionHandle);

            final PendingIntent result = PendingIntent.getActivity(getApplicationContext(), sessionHandle.hashCode(),
                    switchIntent, 0);

            final PackageManager pm = getPackageManager();
            final String[] pkgs = pm.getPackagesForUid(getCallingUid());
            if (pkgs == null || pkgs.length == 0)
                return null;

            for (String packageName:pkgs) {
                try {
                    final PackageInfo pkgInfo = pm.getPackageInfo(packageName, 0);

                    final ApplicationInfo appInfo = pkgInfo.applicationInfo;
                    if (appInfo == null)
                        continue;

                    final CharSequence label = pm.getApplicationLabel(appInfo);

                    if (!TextUtils.isEmpty(label)) {
                        final String niceName = label.toString();

                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                GenericTermSession session = null;
                                try {
                                    final TermSettings settings = new TermSettings(getResources(),
                                            PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));

                                    session = new BoundSession(pseudoTerminalMultiplexerFd, settings, niceName);

                                    mTermSessions.add(session);

                                    session.setHandle(sessionHandle);
                                    session.setFinishCallback(new RBinderCleanupCallback(result, callback));
                                    session.setTitle("");

                                    session.initializeEmulator(80, 24);
                                } catch (Exception whatWentWrong) {
                                    Log.e("TermService", "Failed to bootstrap AIDL session: "
                                            + whatWentWrong.getMessage());

                                    if (session != null)
                                        session.finish();
                                }
                            }
                        });

                        return result.getIntentSender();
                    }
                } catch (PackageManager.NameNotFoundException ignore) {}
            }

            return null;
        }

        @Override
        public String getInterfaceDescriptor() {
            return null;
        }

        @Override
        public boolean pingBinder() {
            return false;
        }

        @Override
        public boolean isBinderAlive() {
            return false;
        }

        @Override
        public IInterface queryLocalInterface(String descriptor) {
            return null;
        }

        @Override
        public void dump(FileDescriptor fd, String[] args) {

        }

        @Override
        public void dumpAsync(FileDescriptor fd, String[] args) {

        }

        //@Override
        //public boolean transact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
        //    return false;
        //}

        @Override
        public void linkToDeath(DeathRecipient recipient, int flags) {

        }

        @Override
        public boolean unlinkToDeath(DeathRecipient recipient, int flags) {
            return false;
        }
    }

    private final class RBinderCleanupCallback implements TermSession.FinishCallback {
        private final PendingIntent result;
        private final ResultReceiver callback;

        public RBinderCleanupCallback(PendingIntent result, ResultReceiver callback) {
            this.result = result;
            this.callback = callback;
        }

        @Override
        public void onSessionFinish(TermSession session) {
            result.cancel();

            callback.send(0, new Bundle());

            mTermSessions.remove(session);
        }
    }

    public static void CancelNotification(Context ctx, int notifyId) {
    String ns = Context.NOTIFICATION_SERVICE;
    NotificationManager nMgr = (NotificationManager) ctx
            .getSystemService(ns);
    nMgr.cancel(notifyId);
}

}
