package com.thecrackertechnology.andrax;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
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
import android.support.v7.app.AppCompatDelegate;
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


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{


    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    String installchecker = "";

    String resulzsh = "";

    String versiondefault = "211";

    String urlcore = "http://download.thecrackertechnology.com/andrax/andrax.r2-beta1.tar.xz";

    String coreversion;

    StringBuilder postgresqldaemonresult = new StringBuilder();

    private static final int MY_PERMISSIONS_REQUEST_WRITE = 22;

    private ProgressDialog progressDialog;

    private ProgressDialog chmodprogressDialog;

    private ProgressDialog unpackprogressDialog;

    private ProgressDialog postgresqldaemonprogressDialog;

    public static final int progressType = 0;

    String ab;
    String abc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("shell", "/system/xbin/andraxzsh");
        editor.apply();

        new CheckVersion().execute("http://download.thecrackertechnology.com/andrax/version2");

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


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

            Process process04 = Runtime.getRuntime().exec("su -c " + MainActivity.this.getApplicationInfo().dataDir + "/ANDRAX/bin/checkinstall");

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process04.getInputStream()));
            int read;

            char[] buffer = new char[8000];
            StringBuffer output = new StringBuffer();
            while ((read = reader.read(buffer)) > 0) {

                output.append(buffer, 0, read );


            }

            process04.waitFor();

            Process checkandraxzsh = Runtime.getRuntime().exec("su -c if [ ! -f /system/xbin/andraxzsh ]; then echo -n \"ERR\"; else echo -n \"OK\"; fi");

            BufferedReader reader2 = new BufferedReader(
                    new InputStreamReader(checkandraxzsh.getInputStream()));
            int read2;

            char[] buffer2 = new char[8000];
            StringBuffer output2 = new StringBuffer();
            while ((read2 = reader2.read(buffer2)) > 0) {

                output2.append(buffer2, 0, read2 );


            }

            checkandraxzsh.waitFor();

            resulzsh = output2.toString();

            reader2.close();


            reader.close();

            installchecker = output.toString();




        } catch (IOException e) {
            e.printStackTrace();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }



        if(installchecker.equals("OK")) {

            if(resulzsh.equals("OK")) {

            } else {


                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("ANDRAX SHELL ERRO!!!");
                builder.setMessage("CHECKINSTALL Return \"OK\"\nbut ANDRAXSHELL is not Working!!!");
                builder.setIcon(R.mipmap.ic_launcher);

                String positiveText = getString(android.R.string.ok);
                builder.setPositiveButton(positiveText,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {


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

                dialog.show();

            }

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

            dialog.show();

        }


        installbusybox();



        // to test crash report
        //if(ab.equals(abc)) {

        //}

        //Toast.makeText(MainActivity.this, "PATH = " + MainActivity.this.getApplicationInfo().dataDir ,Toast.LENGTH_LONG).show();


    }



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
                //unpackprogressDialog.setMax(100);
                unpackprogressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                unpackprogressDialog.setCancelable(false);
                unpackprogressDialog.show();
                return unpackprogressDialog;

            case 6:
                postgresqldaemonprogressDialog = new ProgressDialog(this, android.app.AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
                postgresqldaemonprogressDialog.setProgressStyle(R.style.AppCompatAlertDialogStyle);
                postgresqldaemonprogressDialog.setMessage("Starting PostgreSQL daemon...");
                postgresqldaemonprogressDialog.setIndeterminate(true);
                //postgresqldaemonprogressDialog.setMax(0);
                postgresqldaemonprogressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                postgresqldaemonprogressDialog.setCancelable(false);
                postgresqldaemonprogressDialog.show();
                return postgresqldaemonprogressDialog;
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

            dialog.show();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_main) {

            MainFragment fragment = new MainFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();

        } else if (id == R.id.action_telegram) {

            Intent telegram = new Intent(Intent.ACTION_VIEW , Uri.parse("https://telegram.me/thecrackertechnology"));
            startActivity(telegram);

        } else if (id == R.id.action_installmanual) {


            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlcore));
            startActivity(intent);


        } else if (id == R.id.action_installbusybox) {

            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = sharedPref.edit();

            editor.putBoolean("INSTALLEDBUSYBOX", false);
            editor.apply();


            installbusybox();


        } else if (id == R.id.action_chmod) {

            new fixcore().execute("nothing");


        } /** else if (id == R.id.action_postgresqlstart) {

            new postgresqlstartdaemon().execute("FUCKYOU");

        }**/ else if(id == R.id.action_patchshell) {

            try {


                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("shell", "/system/xbin/andraxzsh -su");
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

            dialog.show();
        }


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_terminal) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "andrax");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);

        } else if (id == R.id.nav_hidattack) {

            Intent intent = new Intent(MainActivity.this,HIDAttack.class);
            startActivity(intent);

        } else if (id == R.id.nav_nmap) {

            NmapFragment fragment = new NmapFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_evilginx2) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "evilginx2");
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
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "scapy");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);

        } /** else if(id == R.id.nav_ax_webserver) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "ax-webserver");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);

        } **/ else if(id == R.id.nav_fakedns) {

            DnstoolFragment fragment = new DnstoolFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();

        } else if(id == R.id.nav_hostapd) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "hostapd -h");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);

        } else if(id == R.id.nav_hostapd_wpe) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "hostapd-wpe -h");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);

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

        } else if (id == R.id.nav_mdk3) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "mdk4 --help");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);

        } else if (id == R.id.nav_bully) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "bully");
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

        } else if (id == R.id.nav_goldeneye) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "goldeneye -h");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);


        } else if (id == R.id.nav_hping) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "hping3 -h");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);


        } else if (id == R.id.nav_nping) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "nping");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);


        } else if (id == R.id.nav_delorean) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "delorean");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);


        } else if (id == R.id.nav_hexinject) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "hexinject -h");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);

        } else if(id == R.id.nav_ncat) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "ncat -h");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);

        } else if(id == R.id.nav_netcat) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "netcat -h");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);

        } else if(id == R.id.nav_dig) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "dig -h");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);

        } else if(id == R.id.nav_dmitry) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "dmitry");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);

        } else if(id == R.id.nav_0trace) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "0trace.sh");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);

        } else if(id == R.id.nav_intrace) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "intrace");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);

        } else if(id == R.id.nav_braa) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "braa");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);

        } else if(id == R.id.nav_fierce) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "fierce --help");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);

        } else if(id == R.id.nav_automater) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "automater -h");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);

        } else if(id == R.id.nav_dhcping) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "dhcping");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);

        } else if(id == R.id.nav_iaxflood) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "iaxflood");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);

        } else if(id == R.id.nav_inviteflood) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "inviteflood");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);

        } else if(id == R.id.nav_rtpflood) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "rtpflood");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);

        } else if(id == R.id.nav_rtpbreak) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "rtpbreak");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);

        } else if(id == R.id.nav_enumiax) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "enumiax");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);

        } else if(id == R.id.nav_sipsak) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "sipsak");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);

        } else if(id == R.id.nav_fiked) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "fiked");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);

        } else if(id == R.id.nav_rtpinsertsound) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "rtpinsertsound");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);

        } else if(id == R.id.nav_rtpmixsound) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "rtpmixsound");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);

        } else if(id == R.id.nav_dhcpig) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "pig");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);

        } else if(id == R.id.nav_fragroute) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "fragroute");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);

        } else if(id == R.id.nav_dns2tcp) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "dns2tcpc");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);

        } else if(id == R.id.nav_udp2raw) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "udp2raw");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);

        } else if(id == R.id.nav_godoh) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "godoh");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);

        } else if(id == R.id.nav_hamster) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "hamster");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);

        } else if(id == R.id.nav_grabber) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "grabber -h");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);

        } else if(id == R.id.nav_fimap) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "fimap -h");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);

        } else if(id == R.id.nav_clusterd) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "clusterd");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);

        } else if(id == R.id.nav_dirb) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "dirb");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);

        } else if(id == R.id.nav_siege) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "siege");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);

        } else if(id == R.id.nav_jshell) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "jshell");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);

        } else if(id == R.id.nav_slowhttptest) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "slowhttptest");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);

        } else if(id == R.id.nav_httrack) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "httrack");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);

        } else if(id == R.id.nav_acccheck) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "acccheck");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);

        } else if(id == R.id.nav_massh_enum) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "massh-enum --help");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);

        } else if(id == R.id.nav_ssh_auditor) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "ssh-auditor");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);

        } else if(id == R.id.nav_intersect) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "intersect");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);

        } else if(id == R.id.nav_flasm) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "flasm");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);

        } else if(id == R.id.nav_aflfuzz) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "afl-fuzz");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);

        } else if(id == R.id.nav_sfuzz) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "sfuzz");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);

        } else if(id == R.id.nav_ocs) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "cisco-ocs");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);

        } else if(id == R.id.nav_apktool) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "apktool");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);

        } else if(id == R.id.nav_dnsrecon) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "dnsrecon");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);

        }  else if(id == R.id.nav_raccoon) {

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
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "amap");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);

        } else if(id == R.id.nav_firewalk) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "firewalk");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);


        } else if(id == R.id.nav_dnstool) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "dns-cracker");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);


        } else if(id == R.id.nav_odin) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "0d1n");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);

        } else if(id == R.id.nav_arjun) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "arjun -h");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);

        } else if(id == R.id.nav_mitmproxy) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "mitmproxy");
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
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "phpsploit");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);


        } else if(id == R.id.nav_xsstrike) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "xsstrike");
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
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "payloadmask");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);

        } else if(id == R.id.nav_commix) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "commix -h");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);

        } else if(id == R.id.nav_put2win) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "put2win -h");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);

        } else if(id == R.id.nav_sqlmap) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "sqlmap -hh");
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
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "arpspoof");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);

        } else if(id == R.id.nav_bettercap) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "bettercap");
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
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "tcpdump -v -X");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);

        } else if (id == R.id.nav_hydra) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "hydra -h");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);


        } else if (id == R.id.nav_ncrack) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "ncrack");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);


        } else if(id == R.id.nav_crunch) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "crunch -h");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);

        } else if(id == R.id.nav_john) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "john");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);

        } else if(id == R.id.nav_metasploit) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "msfconsole");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);

        } else if(id == R.id.nav_msfvenom) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "msfvenom -h");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);

        } else if(id == R.id.nav_metasmshell) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "metasm_shell");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);

        } else if(id == R.id.nav_patterncreator) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "pattern_create");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);

        } else if(id == R.id.nav_patternoffset) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "pattern_offset");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);

        } else if(id == R.id.nav_egghunter) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "egghunter -h");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);

        } else if(id == R.id.nav_find_badchars) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "find_badchars -h");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);

        } else if(id == R.id.nav_msfbinscan) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "msfbinscan -h");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);

        } else if(id == R.id.nav_msfelfscan) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "msfelfscan -h");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);

        } else if(id == R.id.nav_msfpescan) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "msfpescan -h");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);

        } else if(id == R.id.nav_msfmachscan) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "msfmachscan -h");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);

        } else if(id == R.id.nav_routersploit) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "rsf");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);

        } else if(id == R.id.nav_zsc) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "zsc");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);

        } else if(id == R.id.nav_roptool) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "rop-tool");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);


        } else if(id == R.id.nav_vault) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "vault");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);


        } else if(id == R.id.nav_gasmask) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "gasmask");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);


        } else if(id == R.id.nav_tldscanner) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "tld_scanner");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);


        } else if(id == R.id.nav_xray) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "xray");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);


        } else if(id == R.id.nav_eyes) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "eyes");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);


        } else if(id == R.id.nav_amass) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "amass");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);


        } else if(id == R.id.nav_amass) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "amass");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);


        } else if(id == R.id.nav_dnsmap) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "dnsmap");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);


        } else if(id == R.id.nav_chameleon) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "chameleon -h");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);


        } else if(id == R.id.nav_theharvester) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "theharvester");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);


        } else if(id == R.id.nav_cr3d0v3r) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "cr3d0v3r -h");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);


        } else if(id == R.id.nav_gophish) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "gophish");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);


        } else if(id == R.id.nav_reconspider) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "reconspider");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);


        } else if(id == R.id.nav_rapidscan) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "rapidscan --help");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);


        } else if(id == R.id.nav_a2sv) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "a2sv");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);


        } else if(id == R.id.nav_pysslscan) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "pysslscan -h");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);


        } else if(id == R.id.nav_snmpwn) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "snmpwn --help");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);


        } else if(id == R.id.nav_vsaudit) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "vsaudit");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);


        } else if(id == R.id.nav_dns2proxy) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "dns2proxy -h");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);


        } else if(id == R.id.nav_wascan) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "wascan");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);


        } else if(id == R.id.nav_golismero) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "golismero -h");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);


        } else if(id == R.id.nav_wafninja) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "wafninja -h");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);


        } else if(id == R.id.nav_wafw00f) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "wafw00f -h");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);


        } else if(id == R.id.nav_pyfilebuster) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "filebuster -h");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);


        } else if(id == R.id.nav_evilurl) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "evilurl");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);


        } else if(id == R.id.nav_fiesta) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "fiesta -h");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);


        } else if(id == R.id.nav_xsrfprobe) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "xsrfprobe");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);


        } else if(id == R.id.nav_whatweb) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "whatweb -h");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);


        } else if(id == R.id.nav_wpxf) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "wpxf");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);


        } else if(id == R.id.nav_sitebroker) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "sitebroker");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);


        } else if(id == R.id.nav_hydra_form) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "hydra-form");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);


        } else if(id == R.id.nav_bopscrk) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "bopscrk");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);


        } else if(id == R.id.nav_hwacha) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "hwacha");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);


        } else if(id == R.id.nav_autosploit) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "autosploit");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);


        } else if(id == R.id.nav_shellpop) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "shellpop -h");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);


        } else if(id == R.id.nav_pacu) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "pacu");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);


        } else if(id == R.id.nav_sharpshooter) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "sharpshooter -h");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);


        } else if(id == R.id.nav_gdog) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "gdog");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);


        } else if(id == R.id.nav_shellver) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "shellver -h");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);


        } else if(id == R.id.nav_mcreator) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "mcreator");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);


        } else if(id == R.id.nav_smap) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "smap -h");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);


        } else if(id == R.id.nav_encdecshellcode) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "encdecshellcode");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);


        } else if(id == R.id.nav_pocsuite) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "pocsuite");
            intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentstart);


        } else if(id == R.id.nav_brakeman) {

            Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

            intentstart.addCategory(Intent.CATEGORY_DEFAULT);
            intentstart.putExtra("andrax.axterminal.iInitialCommand", "brakeman -h");
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

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {

                    finish();

                }
                return;
            }

        }
    }



    class DownloadFromURL extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try{
                showDialog(progressType);
            } catch (IllegalArgumentException e) {

            }

        }

        @Override
        protected String doInBackground(String... fileUrl) {
            int count;
            try {
                URL url = new URL(fileUrl[0]);
                URLConnection urlConnection = url.openConnection();
                urlConnection.setConnectTimeout(10000);
                urlConnection.setReadTimeout(10000);
                urlConnection.connect();
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

                outputStream.flush();

                outputStream.close();
                inputStream.close();

            } catch (Exception e) {
                Log.e("Error DOWNLOAD: ", e.getMessage());


            }
            return null;
        }


        protected void onProgressUpdate(String... progress) {

            progressDialog.setProgress(Integer.parseInt(progress[0]));
        }

        @Override
        protected void onPostExecute(String file_url) {

            try{
                dismissDialog(progressType);
            } catch (IllegalArgumentException e) {

            }


            new unpackandinstall().execute(urlcore);


        }
    }


    class fixcore extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try {

                showDialog(2);

            } catch (IllegalArgumentException e) {

            }
        }

        @Override
        protected String doInBackground(String... fileUrl) {

            try {

                Process chmodentirecore = Runtime.getRuntime().exec("su -c chmod -R 777 " + MainActivity.this.getApplicationInfo().dataDir + "/ANDRAX");
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

            try {

                dismissDialog(2);

            } catch (IllegalArgumentException e) {

            }

            Intent intent = new Intent(MainActivity.this,SplashActivity.class);
            startActivity(intent);
            finish();


        }
    }



    class unpackandinstall extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try{
                showDialog(3);
            } catch (IllegalArgumentException e) {

            }
        }

        @Override
        protected String doInBackground(String... fileUrl) {

            try {

                Process removeoldcore = Runtime.getRuntime().exec("su -c rm -rf " + MainActivity.this.getApplicationInfo().dataDir + "/ANDRAX/*");
                removeoldcore.waitFor();

                Process unzipcore = Runtime.getRuntime().exec("su -c /system/xbin/busybox tar -xJf /sdcard/Download/andraxcore.tar.xz -C " + MainActivity.this.getApplicationInfo().dataDir + "/ANDRAX/");

                unzipcore.waitFor();

                Process cleanzipcore = Runtime.getRuntime().exec("su -c rm -R /sdcard/Download/andraxcore.tar.xz");

                cleanzipcore.waitFor();

                Process createversionfile = Runtime.getRuntime().exec("su -c echo " + versiondefault + "" + "> " + MainActivity.this.getApplicationInfo().dataDir + "/ANDRAX/version");

                createversionfile.waitFor();



            } catch (IOException e) {
                e.getMessage();


            } catch (InterruptedException e) {
                e.printStackTrace();

            }

            return null;
        }



        protected void onProgressUpdate(String... progress) {


        }

        @Override
        protected void onPostExecute(String file_url) {



            try {

                dismissDialog(3);

            } catch (IllegalArgumentException e) {

            }

            Intent intent = new Intent(MainActivity.this,SplashActivity.class);
            startActivity(intent);
            finish();
            finish();
            finish();


        }
    }



    public void installbusybox() {

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPref.edit();


        if(sharedPref.getBoolean("INSTALLEDBUSYBOX", false)) {

        } else {

            try {

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

                                    Intent intent = null;

                                    try {
                                        intent = new Intent(MainActivity.this, Class.forName("com.thecrackertechnology.busybox.MainActivity"));
                                    } catch (ClassNotFoundException e) {
                                        e.printStackTrace();
                                    }
                                    startActivity(intent);

                                    editor.putBoolean("INSTALLEDBUSYBOX", true);
                                    editor.apply();

                                    Intent intent2 = new Intent(MainActivity.this,SplashActivity.class);
                                    startActivity(intent2);
                                    finish();
                                    finish();


                                }
                            });

                    builder.setNegativeButton("FORCE INSTALL",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    editor.putBoolean("INSTALLEDBUSYBOX", true);
                                    editor.apply();

                                    Intent intent = null;

                                    try {
                                        intent = new Intent(MainActivity.this, Class.forName("com.thecrackertechnology.busybox.MainActivity"));
                                    } catch (ClassNotFoundException e) {
                                        e.printStackTrace();
                                    }
                                    startActivity(intent);

                                    Intent intent2 = new Intent(MainActivity.this,SplashActivity.class);
                                    startActivity(intent2);
                                    finish();
                                    finish();

                                }
                            });

                    AlertDialog dialog = builder.create();

                    dialog.show();



            } catch (NullPointerException e) {
                e.getMessage();



            }

        }




    }



    public void checkcoreversion() {


        try {

            Process checkcoreversioncmd = Runtime.getRuntime().exec("su -c cat " + MainActivity.this.getApplicationInfo().dataDir + "/ANDRAX/version");

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


        try {

            /* Ex: 123 #1 is version of ANDRAX, #2 is version of BUILD and #3 is version of core */
            if(coreversion.equals(versiondefault)) {

            } else if(coreversion.equals("")) {

            }

            else {


                new DownloadFromURL().execute(urlcore);


            }

        } catch (NullPointerException e) {

            new DownloadFromURL().execute(urlcore);

        }




    }



    int VersionFromServer = 0;

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
                urlConnection.setConnectTimeout(10000);
                urlConnection.setReadTimeout(10000);
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

        protected void onProgressUpdate(String... progress) {


        }

        @Override
        protected void onPostExecute(String file_url) {

            if(VersionFromServer > 211) {

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

                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://andrax.thecrackertechnology.com")));

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

                dialog.show();

            }

        }
    }


    class postgresqlstartdaemon extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try{
                showDialog(6);
            } catch (IllegalArgumentException e) {

            }
        }

        @Override
        protected String doInBackground(String... fileUrl) {

            getpostgreresult();

            return null;
        }



        protected void onProgressUpdate(String... progress) {

        }

        @Override
        protected void onPostExecute(String file_url) {

            try {

                dismissDialog(6);

            } catch (IllegalArgumentException e) {

            }


            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("PostgreSQL Daemon result");
            builder.setMessage(postgresqldaemonresult);
            builder.setIcon(R.mipmap.ic_launcher);
            builder.setCancelable(false);

            String positiveText = getString(android.R.string.ok);
            builder.setPositiveButton(positiveText,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

            AlertDialog dialog = builder.create();

            dialog.show();


        }
    }

    public void getpostgreresult() {

        try {


            Process startdb = Runtime.getRuntime().exec(MainActivity.this.getApplicationInfo().dataDir + "/ANDRAX/bin/service postgresql start");

            startdb.waitFor();

            BufferedReader postgrefuckbuffer = new BufferedReader(new InputStreamReader(startdb.getInputStream()));



            String tmpfuckline;
            while ((tmpfuckline = postgrefuckbuffer.readLine()) != null) {
                postgresqldaemonresult.append(tmpfuckline).append("\n");
            }

            postgrefuckbuffer.close();



        } catch (InterruptedException e1) {
            e1.printStackTrace();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

    }





}
