package com.thecrackertechnology.andrax;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);


        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {



            }
        }, 2000);


        try {

            Process chmodhack = Runtime.getRuntime().exec("su -h");
            chmodhack.waitFor();

            if(isInstalledOnSdCard()) {

                Process createsymlinkmnt = Runtime.getRuntime().exec("su -c ln -s " + SplashActivity.this.getApplicationInfo().dataDir + " /data/data/com.thecrackertechnology.andrax");
                createsymlinkmnt.waitFor();

                //Toast.makeText(SplashActivity.this, "Installed: " + SplashActivity.this.getApplicationInfo().dataDir, Toast.LENGTH_LONG).show();

                Process rmdoublelink = Runtime.getRuntime().exec("su -c rm " + "/data/data/com.thecrackertechnology.andrax/com.thecrackertechnology.andrax");
                rmdoublelink.waitFor();

            }


        } catch (InterruptedException | IOException e) {

            e.printStackTrace();

        } finally {

            new chmodandraxroot().execute();

        }



    }


    @Override
    protected void onPause() {

        super.onPause();

    }


    class chmodandraxroot extends AsyncTask<Integer, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showDialog(0);
        }

        @Override
        protected String doInBackground(Integer... progress) {


            try {


                //SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

                //if (sharedPref.getString("TWOTimeOpen", "").equals("none")) {

                //} else {
                //    SharedPreferences.Editor editor = sharedPref.edit();
                //    editor.clear().apply();

                //    editor.putString("TWOTimeOpen", "none");
                //    editor.apply();
                //}


                Process process = Runtime.getRuntime().exec("su -c mkdir /data/data/com.thecrackertechnology.andrax/ANDRAX");
                process.waitFor();

                Process checkifhavepermissions = Runtime.getRuntime().exec("su -c if [[ $(/system/xbin/busybox stat -c \"%a\" /data/data/com.thecrackertechnology.andrax/ANDRAX/bin) == \"777\" ]];then return 0;else return 1;fi");
                checkifhavepermissions.waitFor();

                int checkifhavepermissionsexitvaleu = checkifhavepermissions.exitValue();

                if (checkifhavepermissionsexitvaleu != 0) {

                    Process chmodhack = Runtime.getRuntime().exec("su -c chmod -R 777 /data/data/com.thecrackertechnology.andrax/ANDRAX/");
                    chmodhack.waitFor();

                }

                Process process03 = Runtime.getRuntime().exec("su -c mount -o rw,remount /system /system");
                process03.waitFor();

                int mount01exit = process03.exitValue();

                if (mount01exit != 0) {

                    Process processforceremount01 = Runtime.getRuntime().exec("su -c mount -o rw,remount /system");
                    processforceremount01.waitFor();

                }

                Process checksusymb = Runtime.getRuntime().exec("su -c if [[ -L \"/system/xbin/su\" ]];then rm /system/xbin/su;else echo \"Not to be done!\";return 0;fi");
                checksusymb.waitFor();


                Process startbootloader = Runtime.getRuntime().exec("su -c /data/data/com.thecrackertechnology.andrax/ANDRAX/bin/bootloader");
                startbootloader.waitFor();

                Process startbootloader02 = Runtime.getRuntime().exec("su -c /data/data/com.thecrackertechnology.andrax/ANDRAX/bin/bootloader");
                startbootloader02.waitFor();

                Process checkandraxshell = Runtime.getRuntime().exec("su -c /system/xbin/andraxshell --version");
                checkandraxshell.waitFor();

                int checkshellresult = checkandraxshell.exitValue();

                if (checkshellresult != 0) {

                    Process remountsystem02 = Runtime.getRuntime().exec("su -c mount -o rw,remount /system /system");
                    remountsystem02.waitFor();

                    int mount02exit = remountsystem02.exitValue();

                    if (mount02exit != 0) {

                        Process processforceremount02 = Runtime.getRuntime().exec("su -c mount -o rw,remount /system");
                        processforceremount02.waitFor();

                    }

                    Process installshell = Runtime.getRuntime().exec("su -c cp -Rf /data/data/com.thecrackertechnology.andrax/ANDRAX/bash/bin/bash /system/xbin/andraxshell");
                    installshell.waitFor();

                    Process chmodshell = Runtime.getRuntime().exec("su -c chmod 777 /system/xbin/andraxshell");
                    chmodshell.waitFor();

                }

            } catch (IOException e) {

                e.printStackTrace();

            } catch (InterruptedException e) {

                e.printStackTrace();

            }

            return null;
        }



        protected void onProgressUpdate(String... progress) {

        }

        @Override
        protected void onPostExecute(String simpleovv) {

            dismissDialog(0);

            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);

            finish();
            finish();

        }
    }


    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case 0:
                ProgressDialog progressDialog = new ProgressDialog(this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
                progressDialog.setProgressStyle(R.style.AppCompatAlertDialogStyle);
                progressDialog.setMessage("Configuring ANDRAX file system...");
                progressDialog.setIndeterminate(true);
                progressDialog.setCancelable(false);
                progressDialog.show();
                return progressDialog;
            default:
                return null;
        }
    }


    public boolean isInstalledOnSdCard() {

        Context context =  getApplicationContext();

        try {
            String filesDir = SplashActivity.this.getApplicationInfo().dataDir;
            if (filesDir.startsWith("/data/")) {
                return false;
            } else if (filesDir.contains("/mnt/") || filesDir.contains("/sdcard/")) {
                return true;
            }
        } catch (Throwable e) {

        }

        return false;
    }

}
