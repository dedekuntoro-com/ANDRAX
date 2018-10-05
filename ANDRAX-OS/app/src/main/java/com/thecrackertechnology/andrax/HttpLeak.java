package com.thecrackertechnology.andrax;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

public class HttpLeak extends AppCompatActivity {

    String pattern;
    String replace;
    String port;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http_leak);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button btninject = (Button) findViewById(R.id.button_httpinject);
        final EditText editTextpattern = (EditText) findViewById(R.id.httpleakpattern);
        final EditText editTextreplace = (EditText) findViewById(R.id.httpleakreplace);
        final EditText editTextport = (EditText) findViewById(R.id.httpleakport);

        btninject.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                pattern = editTextpattern.getText().toString();
                replace = editTextreplace.getText().toString();
                port = editTextport.getText().toString();

                runOnUiThread(new Runnable() {
                    public void run()
                    {

                        try {
                            Process process = Runtime.getRuntime().exec("su -c iptables -t nat -A POSTROUTING -j MASQUERADE");
                            process.waitFor();
                            Process process02 = Runtime.getRuntime().exec("su -c iptables -t nat -A PREROUTING -p tcp --dport 80 -j REDIRECT --to-port " + port);
                            process02.waitFor();
                        } catch (IOException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "IPTABLES ERROR!!!", Toast.LENGTH_LONG).show();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }

                });

                Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

                intentstart.addCategory(Intent.CATEGORY_DEFAULT);
                intentstart.putExtra("andrax.axterminal.iInitialCommand", "http-leak " + port + " \"" + pattern + "\" \"" + replace + "\" ");
                intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentstart);


            }
        });

    }

}
