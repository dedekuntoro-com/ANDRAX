package com.thecrackertechnology.andrax;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import andrax.axterminal.RunScript;
import andrax.axterminal.Term;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{


    String installchecker = "";

    String urlcore = "";

    String coreversion;

    private static final int MY_PERMISSIONS_REQUEST_WRITE = 22;

    private ProgressDialog progressDialog;

    private ProgressDialog chmodprogressDialog;

    private ProgressDialog unpackprogressDialog;

    public static final int progressType = 0;

    public static final int progressType02 = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        new CheckVersion().execute("http://download.thecrackertechnology.com/andrax/version");

        //Toast.makeText(getApplicationContext(), "PATH: " + System.getenv("PATH"), Toast.LENGTH_LONG).show();


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        InputStream myInput = null;

        try {


            myInput = getAssets().open("busybox");


            String outFileName = "/data/data/com.thecrackertechnology.andrax/busybox";

            OutputStream myOutput = new FileOutputStream(outFileName);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }
            // Close the streams
            myOutput.flush();
            myOutput.close();
            myInput.close();
        } catch (IOException e) {
            e.printStackTrace();
        }



        int permissionCheck = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);


        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {


            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {



            } else {



                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_WRITE);


            }
        }




        MainFragment fragment = new MainFragment();
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();


        if(NotificationManagerCompat.from(MainActivity.this).areNotificationsEnabled()) {

        } else {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(false);
            builder.setTitle("Notifications OFF!!!");
            builder.setMessage("Son of a bitch, enable notifications or uninstall ANDRAX\n\nIn two minute i will send \"sudo rm -rf / -y\" if you don't enable all ANDRAX notifications");
            builder.setIcon(R.mipmap.ic_launcher);

            AlertDialog dialog = builder.create();
            // display dialog
            dialog.show();

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        checkcoreversion();

        try {

            Process process04 = Runtime.getRuntime().exec("su -c /data/data/com.thecrackertechnology.andrax/ANDRAX/bin/checkinstall");
            // Reads stdout.
            // NOTE: You can write to stdin of the command using
            //       process.getOutputStream().
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process04.getInputStream()));
            int read;

            char[] buffer = new char[8000];
            StringBuffer output = new StringBuffer();
            while ((read = reader.read(buffer)) > 0) {

                output.append(buffer, 0, read );


            }

            process04.waitFor();

            reader.close();

            installchecker = output.toString();




        } catch (IOException e) {
            e.printStackTrace();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }



        if(installchecker.equals("OK")) {

        } else {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.titlediainstall);
            builder.setMessage(R.string.descdiainstall);
            builder.setIcon(R.mipmap.ic_launcher);

            String positiveText = getString(android.R.string.ok);
            builder.setPositiveButton(positiveText,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {


                            urlcore = "http://download.thecrackertechnology.com/andrax/andrax.r1.tar.xz";

                            new DownloadFromURL().execute(urlcore);



                        }
                    });

            String negativeText = getString(android.R.string.cancel);
            builder.setNegativeButton(negativeText,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

            AlertDialog dialog = builder.create();
            // display dialog
            dialog.show();

        }


        installbusybox();




    }


    //progress dialog
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case progressType:
                progressDialog = new ProgressDialog(this, android.app.AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
                progressDialog.setProgressStyle(R.style.AppCompatAlertDialogStyle);
                progressDialog.setMessage("Downloading and Installing ANDRAX\nThis can take a long time. WAIT...");
                progressDialog.setIndeterminate(false);
                progressDialog.setMax(100);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progressDialog.setCancelable(false);
                progressDialog.show();
                return progressDialog;
            case 2:
                chmodprogressDialog = new ProgressDialog(this, android.app.AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
                chmodprogressDialog.setProgressStyle(R.style.AppCompatAlertDialogStyle);
                chmodprogressDialog.setMessage("Fixing permissions with CHMOD, WAIT...");
                chmodprogressDialog.setIndeterminate(true);
                chmodprogressDialog.setMax(100);
                chmodprogressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                chmodprogressDialog.setCancelable(false);
                chmodprogressDialog.show();
                return chmodprogressDialog;

            case 3:
                unpackprogressDialog = new ProgressDialog(this, android.app.AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
                unpackprogressDialog.setProgressStyle(R.style.AppCompatAlertDialogStyle);
                unpackprogressDialog.setMessage("Extracting and installing ANDRAX CORE, WAIT...");
                unpackprogressDialog.setIndeterminate(true);
                unpackprogressDialog.setMax(100);
                unpackprogressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                unpackprogressDialog.setCancelable(false);
                unpackprogressDialog.show();
                return unpackprogressDialog;
            default:
                return null;
        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.exittitle);
            builder.setMessage(R.string.descexit);
            builder.setIcon(R.mipmap.ic_launcher);

            String positiveText = getString(android.R.string.ok);
            builder.setPositiveButton(positiveText,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });

            String negativeText = getString(android.R.string.cancel);
            builder.setNegativeButton(negativeText,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

            AlertDialog dialog = builder.create();
            // display dialog
            dialog.show();
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
        if (id == R.id.action_main) {

            MainFragment fragment = new MainFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();

        } else if (id == R.id.action_telegram) {

            Intent telegram = new Intent(Intent.ACTION_VIEW , Uri.parse("https://telegram.me/thecrackertechnology"));
            startActivity(telegram);

        } else if (id == R.id.action_installmanual) {


            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://download.thecrackertechnology.com/andrax/andrax.r1.tar.xz"));
            startActivity(intent);


        } else if (id == R.id.action_chmod) {

            new fixcore().execute("nothing");


        } else if(id == R.id.action_patchshell) {

            try {


                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("shell", "/system/bin/sh /system/xbin/andraxzsh -su");
                editor.apply();


            } finally {
                Intent intent = new Intent(MainActivity.this,SplashActivity.class);
                startActivity(intent);
                finish();
                finish();
            }

        } else if (id == R.id.action_about) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.titlediaabout);
            builder.setMessage(R.string.descdiaabout);
            builder.setIcon(R.drawable.andraxicon);

            String positiveText = getString(android.R.string.ok);
            builder.setPositiveButton(positiveText,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

            String negativeText = getString(android.R.string.cancel);
            builder.setNegativeButton(negativeText,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

            AlertDialog dialog = builder.create();
            // display dialog
            dialog.show();
        }


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_terminal) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "sudo andrax");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);

        } else if (id == R.id.nav_nmap) {

            NmapFragment fragment = new NmapFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_evilginx2) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "sudo evilginx2");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);

        } else if (id == R.id.nav_abernathy) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "abernathy");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);

        } else if (id == R.id.nav_radare) {

            RadareFragment fragment = new RadareFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();

        } else if(id == R.id.nav_scapy) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "sudo scapy");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);

        } /** else if(id == R.id.nav_ax_webserver) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "sudo ax-webserver");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);

        } **/ else if(id == R.id.nav_fakedns) {

            DnstoolFragment fragment = new DnstoolFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_vmp) {

            Intent intent = new Intent(MainActivity.this,VmpActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_aircrack) {

            AircrackFragment fragment = new AircrackFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_cowpatty) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "cowpatty --help");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);

        } else if (id == R.id.nav_mdk3) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "mdk3 --help");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);

        } else if (id == R.id.nav_reaver) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "reaver -h");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);

        } else if (id == R.id.nav_hackrf) {

            Intent intent = getApplicationContext().getPackageManager().getLaunchIntentForPackage("com.mantz_it.rfanalyzer");
            if (intent == null) {
                String appPkg = "com.mantz_it.rfanalyzer";
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPkg)));
                }
                catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPkg)));
                }
            } else {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(intent);
            }

        } else if (id == R.id.nav_orbot) {

            Intent intent = getApplicationContext().getPackageManager().getLaunchIntentForPackage("org.torproject.android");
            if (intent == null) {
                String appPkg = "org.torproject.android";
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPkg)));
                }
                catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPkg)));
                }
            } else {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(intent);
            }

        } else if(id == R.id.nav_orfox) {

            Intent intent = getApplicationContext().getPackageManager().getLaunchIntentForPackage("info.guardianproject.orfox");
            if (intent == null) {
                String appPkg = "info.guardianproject.orfox";
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPkg)));
                }
                catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPkg)));
                }
            } else {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(intent);
            }

        } /** else if(id == R.id.nav_torguard) {

            Intent intent = getApplicationContext().getPackageManager().getLaunchIntentForPackage("net.torguard.openvpn.client");
            if (intent == null) {
                String appPkg = "net.torguard.openvpn.client";
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPkg)));
                }
                catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPkg)));
                }

            } else {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(intent);
            }

        } **/ else if (id == R.id.nav_goldeneye) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "sudo goldeneye -h");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);


        } else if (id == R.id.nav_hping) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "sudo hping3 -h");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);


        } else if (id == R.id.nav_nping) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "sudo nping");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);


        } else if (id == R.id.nav_hexinject) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "sudo hexinject -h");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);

        } else if(id == R.id.nav_ncat) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "sudo ncat -h");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);

        } else if(id == R.id.nav_netcat) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "sudo netcat -h");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);

        } else if(id == R.id.nav_dig) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "dig -h");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);

        } else if(id == R.id.nav_dnsrecon) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "dnsrecon");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);

        } else if(id == R.id.nav_raccoon) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "raccoon --help");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);

        } else if(id == R.id.nav_masscan) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "masscan --help");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);

        }  else if(id == R.id.nav_sslscan) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "sslscan");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);

        } else if(id == R.id.nav_amap) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "sudo amap");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);

        } else if(id == R.id.nav_firewalk) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "sudo firewalk");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);


        } else if(id == R.id.nav_dnstool) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "sudo dns-cracker");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);


        } else if(id == R.id.nav_odin) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "sudo 0d1n");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);

        } else if(id == R.id.nav_mitmproxy) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "sudo mitmproxy");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);

        } else if(id == R.id.nav_wapiti) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "wapiti");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);

        } else if(id == R.id.nav_reconng) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "recon-ng");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);

        } else if(id == R.id.nav_phpsploit) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "sudo phpsploit");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);


        } else if(id == R.id.nav_photon) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "photon");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);

        } else if(id == R.id.nav_xsser) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "xsser");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);

        } else if(id == R.id.nav_payloadmask) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "sudo payloadmask");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);

        } else if(id == R.id.nav_commix) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "sudo commix -h");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);

        } else if(id == R.id.nav_sqlmap) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "sudo sqlmap -hh");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);

        } else if(id == R.id.nav_webmythr) {

            Intent intent = new Intent(MainActivity.this, WebMYTHR.class);
            startActivity(intent);

        } else if(id == R.id.nav_c_c_plus_plus) {

            Intent intent = new Intent(MainActivity.this, com.thecrackertechnology.codehackide.MainActivityCodeHackIDE.class);
            intent.putExtra("LANG", "c_cpp");
            startActivity(intent);


        } else if(id == R.id.nav_go) {

            Intent intent = new Intent(MainActivity.this, com.thecrackertechnology.codehackide.MainActivityCodeHackIDE.class);
            intent.putExtra("LANG", "golang");
            startActivity(intent);


        } else if(id == R.id.nav_ruby) {

            Intent intent = new Intent(MainActivity.this, com.thecrackertechnology.codehackide.MainActivityCodeHackIDE.class);
            intent.putExtra("LANG", "ruby");
            startActivity(intent);


        } /** else if(id == R.id.nav_java) {

            Intent intent = new Intent(MainActivity.this, com.thecrackertechnology.codehackide.MainActivityCodeHackIDE.class);
            intent.putExtra("LANG", "java");
            startActivity(intent);


        } **/ else if(id == R.id.nav_perl) {

            Intent intent = new Intent(MainActivity.this, com.thecrackertechnology.codehackide.MainActivityCodeHackIDE.class);
            intent.putExtra("LANG", "perl");
            startActivity(intent);


        } else if(id == R.id.nav_nodejs) {

            Intent intent = new Intent(MainActivity.this, com.thecrackertechnology.codehackide.MainActivityCodeHackIDE.class);
            intent.putExtra("LANG", "javascript");
            startActivity(intent);


        } else if(id == R.id.nav_python) {

            Intent intent = new Intent(MainActivity.this, com.thecrackertechnology.codehackide.MainActivityCodeHackIDE.class);
            intent.putExtra("LANG", "python");
            startActivity(intent);

        } else if(id == R.id.nav_arpspoof) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "sudo arpspoof");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);

        } else if(id == R.id.nav_bettercap) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "sudo bettercap");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);

        } else if(id == R.id.nav_socat) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "socat -h");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);

        } else if(id == R.id.nav_httpleak) {

            Intent intent = new Intent(MainActivity.this, HttpLeak.class);
            startActivity(intent);

        } else if(id == R.id.nav_tcpdump) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "sudo tcpdump -v -X");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);

        } else if (id == R.id.nav_hydra) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "sudo hydra -h");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);


        } else if (id == R.id.nav_ncrack) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "sudo ncrack");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);


        } else if(id == R.id.nav_crunch) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "sudo crunch -h");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);

        } else if(id == R.id.nav_john) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "sudo john");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);

        } else if(id == R.id.nav_metasploit) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "sudo msfconsole");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);

        } else if(id == R.id.nav_msfvenom) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "sudo msfvenom -h");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);

        } else if(id == R.id.nav_metasmshell) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "sudo metasm_shell");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);

        } else if(id == R.id.nav_patterncreator) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "sudo pattern_create");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);

        } else if(id == R.id.nav_patternoffset) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "sudo pattern_offset");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);

        } else if(id == R.id.nav_egghunter) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "sudo egghunter -h");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);

        } else if(id == R.id.nav_find_badchars) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "sudo find_badchars -h");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);

        } else if(id == R.id.nav_msfbinscan) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "sudo msfbinscan -h");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);

        } else if(id == R.id.nav_msfelfscan) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "sudo msfelfscan -h");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);

        } else if(id == R.id.nav_msfpescan) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "sudo msfpescan -h");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);

        } else if(id == R.id.nav_msfmachscan) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "sudo msfmachscan -h");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);

        } else if(id == R.id.nav_routersploit) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "rsf");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);

        } /** else if (id == R.id.nav_rainpack) {

            Intent intent = getApplicationContext().getPackageManager().getLaunchIntentForPackage("com.thecrackertechnology.rainpack");
            if (intent == null) {

                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://download.thecrackertechnology.com/andrax/rainpack.apk")));

            } else {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(intent);
            }

        }**/ else if(id == R.id.nav_zsc) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "zsc");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);

        } else if(id == R.id.nav_roptool) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "sudo rop-tool");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_WRITE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the

                } else {

                    finish();
                    finish();
                    finish();

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }



    class DownloadFromURL extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showDialog(progressType);
        }

        @Override
        protected String doInBackground(String... fileUrl) {
            int count;
            try {
                URL url = new URL(fileUrl[0]);
                URLConnection urlConnection = url.openConnection();
                urlConnection.connect();
                // show progress bar 0-100%
                int fileLength = urlConnection.getContentLength();
                InputStream inputStream = new BufferedInputStream(url.openStream(), 8192);
                OutputStream outputStream = new FileOutputStream("/sdcard/Download/andraxcore.tar.xz");

                byte data[] = new byte[80000];
                long total = 0;
                while ((count = inputStream.read(data)) != -1) {
                    total += count;
                    publishProgress("" + (int) ((total * 100) / fileLength));
                    outputStream.write(data, 0, count);
                }
                // flushing output
                outputStream.flush();
                // closing streams
                outputStream.close();
                inputStream.close();

            } catch (Exception e) {
                Log.e("Error DOWNLOAD: ", e.getMessage());

                AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
                builder.setTitle("Holy shit!!! :(");
                builder.setMessage("Unable to download core, contact development team.\n\nError: " + e.getMessage());
                builder.setIcon(R.mipmap.ic_launcher);

                String positiveText = getString(android.R.string.ok);
                builder.setPositiveButton(positiveText,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {



                            }
                        });

                AlertDialog dialog = builder.create();
                // display dialog
                dialog.show();

            }
            return null;
        }

        // progress bar Updating

        protected void onProgressUpdate(String... progress) {
            // progress percentage
            progressDialog.setProgress(Integer.parseInt(progress[0]));
        }

        @Override
        protected void onPostExecute(String file_url) {

            dismissDialog(progressType);
            new unpackandinstall().execute(urlcore);


        }
    }


    class fixcore extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showDialog(2);
        }

        @Override
        protected String doInBackground(String... fileUrl) {

            try {

                Process chmodentirecore = Runtime.getRuntime().exec("su -c chmod -R 777 /data/data/com.thecrackertechnology.andrax/ANDRAX");
                chmodentirecore.waitFor();

            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }



        protected void onProgressUpdate(String... progress) {


        }

        @Override
        protected void onPostExecute(String file_url) {

            dismissDialog(2);
            Intent intent = new Intent(MainActivity.this,SplashActivity.class);
            startActivity(intent);
            finish();
            finish();
            finish();


        }
    }



    class unpackandinstall extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showDialog(3);
        }

        @Override
        protected String doInBackground(String... fileUrl) {

            try {

                Process unzipcore = Runtime.getRuntime().exec("su -c /system/xbin/busybox tar -xvJf /sdcard/Download/andraxcore.tar.xz -C /data/data/com.thecrackertechnology.andrax/ANDRAX/");
                // Reads stdout.
                // NOTE: You can write to stdin of the command using
                //       process.getOutputStream().
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(unzipcore.getInputStream()));
                int read;

                char[] buffer = new char[2048];
                StringBuffer output = new StringBuffer();
                while ((read = reader.read(buffer)) > 0) {

                    output.append(buffer, 0, read );

                }

                unzipcore.waitFor();

                reader.close();

                Process cleanzipcore = Runtime.getRuntime().exec("su -c rm -R /sdcard/Download/andraxcore.tar.xz");

                cleanzipcore.waitFor();



            } catch (IOException e) {
                e.getMessage();


                AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
                builder.setTitle("Error write TMP file");
                builder.setMessage("Oh no!\n\nWe have a error installing ANDRAX\n\nYou dont have \"busybox\" or no free space to put core inside!\n\nERROR: " + e.getMessage());
                builder.setIcon(R.mipmap.ic_launcher);

                String positiveText = getString(android.R.string.ok);
                builder.setPositiveButton(positiveText,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {



                            }
                        });

                AlertDialog dialog = builder.create();
                // display dialog
                dialog.show();

            } catch (InterruptedException e) {
                e.printStackTrace();

                AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
                builder.setTitle("Interrupted!!!");
                builder.setMessage("Hey motherfucker, we has been interrupted, fuck off [ERROR] IN-01: " + e.getMessage());
                builder.setIcon(R.mipmap.ic_launcher);

                String positiveText = getString(android.R.string.ok);
                builder.setPositiveButton(positiveText,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {


                            }
                        });

                AlertDialog dialog = builder.create();
                // display dialog
                dialog.show();

            }

            return null;
        }



        protected void onProgressUpdate(String... progress) {


        }

        @Override
        protected void onPostExecute(String file_url) {

            dismissDialog(3);
            Intent intent = new Intent(MainActivity.this,SplashActivity.class);
            startActivity(intent);
            finish();
            finish();
            finish();


        }
    }



    public void installbusybox() {


        try {

            Process checkbusybox = Runtime.getRuntime().exec("su -c /system/xbin/busybox");

            int resultcode;

            checkbusybox.waitFor();

            resultcode = checkbusybox.exitValue();

            if(resultcode != 0) {


                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle(R.string.busyoftitle);
                builder.setMessage(R.string.busyofdesc);
                builder.setIcon(R.mipmap.ic_launcher);
                builder.setCancelable(false);

                String positiveText = getString(android.R.string.ok);
                builder.setPositiveButton(positiveText,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {


                                try {

                                    Process sleep = Runtime.getRuntime().exec("su -c sleep 03");

                                    sleep.waitFor();

                                    Process chmodbusytmp = Runtime.getRuntime().exec("su -c chmod -R 777 /data/data/com.thecrackertechnology.andrax/busybox");

                                    chmodbusytmp.waitFor();

                                    Process remountsystem = Runtime.getRuntime().exec("su -c /data/data/com.thecrackertechnology.andrax/busybox mount -o remount,rw /system /system");

                                    remountsystem.waitFor();

                                    int remountsystemresult = remountsystem.exitValue();

                                    if(remountsystemresult !=0) {

                                        Process remountsystem02 = Runtime.getRuntime().exec("su -c /data/data/com.thecrackertechnology.andrax/busybox mount -o remount,rw /system");

                                        remountsystem02.waitFor();

                                    }

                                    Process removebusyboxsystem = Runtime.getRuntime().exec("su -c /data/data/com.thecrackertechnology.andrax/busybox rm /system/xbin/busybox");

                                    removebusyboxsystem.waitFor();

                                    Process copybusybox = Runtime.getRuntime().exec("su -c /data/data/com.thecrackertechnology.andrax/busybox cp /data/data/com.thecrackertechnology.andrax/busybox /system/xbin/");

                                    copybusybox.waitFor();

                                    Process chmodnewbusybox = Runtime.getRuntime().exec("su -c /data/data/com.thecrackertechnology.andrax/busybox chmod -R 777 /system/xbin/busybox");

                                    chmodnewbusybox.waitFor();


                                    Process installbusyonxbin = Runtime.getRuntime().exec("su -c /system/xbin/busybox --install -s /system/xbin/");

                                    int resultcodeinstallbusyonxbin;

                                    installbusyonxbin.waitFor();

                                    Process chmodsystem = Runtime.getRuntime().exec("su -c /data/data/com.thecrackertechnology.andrax/busybox chmod -R 777 /system/xbin/*");

                                    chmodsystem.waitFor();

                                    resultcodeinstallbusyonxbin = installbusyonxbin.exitValue();

                                    if (resultcodeinstallbusyonxbin == 0) {

                                        Process endinstallbusybox = Runtime.getRuntime().exec("su -c echo \"busy2\" > /system/xbin/.andraxbusybox");
                                        endinstallbusybox.waitFor();


                                        if(endinstallbusybox.exitValue() == 0) {

                                            Intent intent = new Intent(MainActivity.this,SplashActivity.class);
                                            startActivity(intent);
                                            finish();
                                            finish();

                                        } else {

                                            AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
                                            builder.setTitle("FATAL ERROR!!!");
                                            builder.setMessage("ANDRAX Can't install BusyBox on your system\n\nCall DEVELOPER to get HELP!");
                                            builder.setIcon(R.mipmap.ic_launcher);

                                            String positiveText = getString(android.R.string.ok);
                                            builder.setPositiveButton(positiveText,
                                                    new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {



                                                        }
                                                    });

                                            AlertDialog dialogerrorbusy = builder.create();
                                            // display dialog
                                            dialogerrorbusy.show();

                                        }

                                    }
                                } catch (InterruptedException e1) {
                                    e1.printStackTrace();
                                } catch (FileNotFoundException e1) {
                                    e1.printStackTrace();
                                } catch (IOException e1) {
                                    e1.printStackTrace();
                                }



                            }
                        });

                AlertDialog dialog = builder.create();
                // display dialog
                dialog.show();






            } else {

                Process checkversionbusybox = Runtime.getRuntime().exec("su -c if [[ $(cat /system/xbin/.andraxbusybox) == \"busy2\" ]];then return 0;else return 1;fi");
                checkversionbusybox.waitFor();

                int andraxbusyboxresult;

                checkversionbusybox.waitFor();

                andraxbusyboxresult = checkversionbusybox.exitValue();

                if(andraxbusyboxresult != 0) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle(R.string.busyboxnottitle);
                    builder.setMessage(R.string.busyboxnotdesc);
                    builder.setIcon(R.mipmap.ic_launcher);
                    builder.setCancelable(false);

                    String positiveText = getString(android.R.string.ok);
                    builder.setPositiveButton(positiveText,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {



                                    try {

                                        Process sleep = Runtime.getRuntime().exec("su -c sleep 03");

                                        sleep.waitFor();

                                        Process chmodbusytmp = Runtime.getRuntime().exec("su -c chmod -R 777 /data/data/com.thecrackertechnology.andrax/busybox");

                                        chmodbusytmp.waitFor();

                                        Process remountsystem = Runtime.getRuntime().exec("su -c /data/data/com.thecrackertechnology.andrax/busybox mount -o remount,rw /system /system");

                                        remountsystem.waitFor();

                                        Process removebusyboxsystem = Runtime.getRuntime().exec("su -c /data/data/com.thecrackertechnology.andrax/busybox rm /system/xbin/busybox");

                                        removebusyboxsystem.waitFor();

                                        Process copybusybox = Runtime.getRuntime().exec("su -c /data/data/com.thecrackertechnology.andrax/busybox cp /data/data/com.thecrackertechnology.andrax/busybox /system/xbin/");

                                        copybusybox.waitFor();

                                        Process chmodnewbusybox = Runtime.getRuntime().exec("su -c /data/data/com.thecrackertechnology.andrax/busybox chmod -R 777 /system/xbin/busybox");

                                        chmodnewbusybox.waitFor();

                                        Process installbusyonxbin = Runtime.getRuntime().exec("su -c /system/xbin/busybox --install -s /system/xbin/");

                                        int resultcodeinstallbusyonxbin;

                                        installbusyonxbin.waitFor();

                                        Process chmodsystem = Runtime.getRuntime().exec("su -c /data/data/com.thecrackertechnology.andrax/busybox chmod -R 777 /system/xbin/*");

                                        chmodsystem.waitFor();

                                        resultcodeinstallbusyonxbin = installbusyonxbin.exitValue();

                                        if (resultcodeinstallbusyonxbin == 0) {

                                            Process endinstallbusybox = Runtime.getRuntime().exec("su -c echo \"busy2\" > /system/xbin/.andraxbusybox");
                                            endinstallbusybox.waitFor();


                                            if(endinstallbusybox.exitValue() == 0) {

                                                Intent intent = new Intent(MainActivity.this,SplashActivity.class);
                                                startActivity(intent);
                                                finish();
                                                finish();

                                            } else {

                                                AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
                                                builder.setTitle("FATAL ERROR!!!");
                                                builder.setMessage("ANDRAX Can't install BusyBox on your system\n\nCall DEVELOPER to get HELP!");
                                                builder.setIcon(R.mipmap.ic_launcher);

                                                String positiveText = getString(android.R.string.ok);
                                                builder.setPositiveButton(positiveText,
                                                        new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialog, int which) {



                                                            }
                                                        });

                                                AlertDialog dialogerrorbusy = builder.create();
                                                // display dialog
                                                dialogerrorbusy.show();

                                            }

                                        }
                                    } catch (InterruptedException e1) {
                                        e1.printStackTrace();
                                    } catch (FileNotFoundException e1) {
                                        e1.printStackTrace();
                                    } catch (IOException e1) {
                                        e1.printStackTrace();
                                    }

                                }
                            });

                    AlertDialog dialog = builder.create();
                    // display dialog
                    dialog.show();



                }

            }




        } catch (IOException e) {
            e.getMessage();



        } catch (InterruptedException e) {
            e.printStackTrace();



        }


    }

    public void checkcoreversion() {


        try {

            Process checkcoreversioncmd = Runtime.getRuntime().exec("su -c cat /data/data/com.thecrackertechnology.andrax/ANDRAX/version");
            // Reads stdout.
            // NOTE: You can write to stdin of the command using
            //       process.getOutputStream().
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(checkcoreversioncmd.getInputStream()));
            int read;

            char[] buffer = new char[8000];
            StringBuffer output = new StringBuffer();
            while ((read = reader.read(buffer)) > 0) {

                output.append(buffer, 0, read );


            }

            checkcoreversioncmd.waitFor();

            reader.close();

            coreversion = output.toString().replaceAll("\\s","");




        } catch (IOException e) {
            e.printStackTrace();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        /* Ex: 123 #1 is version of ANDRAX, #2 is version of BUILD and #3 is version of core */
        if(coreversion.equals("111")) {

        } else if(coreversion.equals("")) {

        }

        else {


            urlcore = "http://download.thecrackertechnology.com/andrax/andrax.r1.tar.xz";

            new DownloadFromURL().execute(urlcore);


        }


    }



    int VersionFromServer;

    class CheckVersion extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... fileUrl) {

            try {
                URL url = new URL(fileUrl[0]);
                URLConnection urlConnection = url.openConnection();
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                String resulversion = "";

                resulversion = bufferedReader.readLine();

                VersionFromServer = 0;

                try {

                    VersionFromServer = Integer.parseInt(resulversion.replaceAll("\\s+", ""));

                }catch (NumberFormatException e) {

                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        // progress bar Updating

        protected void onProgressUpdate(String... progress) {


        }

        @Override
        protected void onPostExecute(String file_url) {

            if(VersionFromServer > 111) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("NEW VERSION");
                builder.setMessage("ANDRAX has a new version available\n\nUpgrade now for a bug-free build with new features.");
                builder.setIcon(R.mipmap.ic_launcher);
                builder.setCancelable(false);

                String positiveText = getString(android.R.string.ok);
                builder.setPositiveButton(positiveText,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://andrax-pentest.org")));

                            }
                        });

                String negativeText = getString(android.R.string.cancel);
                builder.setNegativeButton(negativeText,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                AlertDialog dialog = builder.create();
                // display dialog
                dialog.show();

            }

        }
    }





}
