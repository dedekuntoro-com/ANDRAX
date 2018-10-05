package com.thecrackertechnology.andrax;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class Dco_Information_Gathering extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

//Remove notification bar
        //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.dco_information_gathering);

        CardView cardviewwhois = findViewById(R.id.card_view_whois);
        CardView cardviewdig = findViewById(R.id.card_view_dig);
        CardView cardviewdnsrecon = findViewById(R.id.card_view_dnsrecon);
        CardView cardviewraccoon = findViewById(R.id.card_view_raccoon);
        CardView cardviewdnscracker = findViewById(R.id.card_view_dnscracker);
        CardView cardviewfirewalk = findViewById(R.id.card_view_firewalk);

        cardviewwhois.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

                intentstart.addCategory(Intent.CATEGORY_DEFAULT);
                intentstart.putExtra("andrax.axterminal.iInitialCommand", "whois");
                intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentstart);
            }
        });

        cardviewdig.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

                intentstart.addCategory(Intent.CATEGORY_DEFAULT);
                intentstart.putExtra("andrax.axterminal.iInitialCommand", "dig -h");
                intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentstart);
            }
        });

        cardviewdnsrecon.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

                intentstart.addCategory(Intent.CATEGORY_DEFAULT);
                intentstart.putExtra("andrax.axterminal.iInitialCommand", "dnsrecon");
                intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentstart);
            }
        });

        cardviewraccoon.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

                intentstart.addCategory(Intent.CATEGORY_DEFAULT);
                intentstart.putExtra("andrax.axterminal.iInitialCommand", "raccoon --help");
                intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentstart);
            }
        });

        cardviewdnscracker.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

                intentstart.addCategory(Intent.CATEGORY_DEFAULT);
                intentstart.putExtra("andrax.axterminal.iInitialCommand", "dns-cracker");
                intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentstart);
            }
        });

        cardviewfirewalk.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

                intentstart.addCategory(Intent.CATEGORY_DEFAULT);
                intentstart.putExtra("andrax.axterminal.iInitialCommand", "firewalk");
                intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentstart);
            }
        });


    }

    @Override
    public void onPause() {
        super.onPause();
        finish();
        finish();
        finish();
    }

}
