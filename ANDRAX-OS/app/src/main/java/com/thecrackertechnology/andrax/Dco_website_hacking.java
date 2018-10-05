package com.thecrackertechnology.andrax;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.Window;

public class Dco_website_hacking extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

//Remove notification bar
        //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.dco_website_hacking);

        CardView cardviewodin = findViewById(R.id.card_view_odin);
        CardView cardviewmitmproxy = findViewById(R.id.card_view_mitmproxy);
        CardView cardviewwapiti = findViewById(R.id.card_view_wapiti);
        CardView cardviewreconng = findViewById(R.id.card_view_reconng);
        CardView cardviewevilginx2 = findViewById(R.id.card_view_evilginx2);
        CardView cardviewphpsploit = findViewById(R.id.card_view_phpsploit);
        CardView cardviewphoton = findViewById(R.id.card_view_photon);
        CardView cardviewxsser = findViewById(R.id.card_view_xsser);
        CardView cardviewcommix = findViewById(R.id.card_view_commix);
        CardView cardviewsqlmap = findViewById(R.id.card_view_sqlmap);
        CardView cardviewpayloadmask = findViewById(R.id.card_view_payloadmask);
        CardView cardviewabernathy = findViewById(R.id.card_view_abernathy);

        cardviewodin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

                intentstart.addCategory(Intent.CATEGORY_DEFAULT);
                intentstart.putExtra("andrax.axterminal.iInitialCommand", "0d1n");
                intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentstart);
            }
        });

        cardviewmitmproxy.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

                intentstart.addCategory(Intent.CATEGORY_DEFAULT);
                intentstart.putExtra("andrax.axterminal.iInitialCommand", "sudo mitmproxy");
                intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentstart);
            }
        });

        cardviewwapiti.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

                intentstart.addCategory(Intent.CATEGORY_DEFAULT);
                intentstart.putExtra("andrax.axterminal.iInitialCommand", "wapiti");
                intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentstart);
            }
        });

        cardviewreconng.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

                intentstart.addCategory(Intent.CATEGORY_DEFAULT);
                intentstart.putExtra("andrax.axterminal.iInitialCommand", "recon-ng");
                intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentstart);
            }
        });

        cardviewevilginx2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

                intentstart.addCategory(Intent.CATEGORY_DEFAULT);
                intentstart.putExtra("andrax.axterminal.iInitialCommand", "sudo evilginx2");
                intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentstart);
            }
        });

        cardviewphpsploit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

                intentstart.addCategory(Intent.CATEGORY_DEFAULT);
                intentstart.putExtra("andrax.axterminal.iInitialCommand", "phpsploit");
                intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentstart);
            }
        });

        cardviewphoton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

                intentstart.addCategory(Intent.CATEGORY_DEFAULT);
                intentstart.putExtra("andrax.axterminal.iInitialCommand", "photon");
                intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentstart);
            }
        });

        cardviewxsser.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

                intentstart.addCategory(Intent.CATEGORY_DEFAULT);
                intentstart.putExtra("andrax.axterminal.iInitialCommand", "xsser");
                intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentstart);
            }
        });

        cardviewcommix.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

                intentstart.addCategory(Intent.CATEGORY_DEFAULT);
                intentstart.putExtra("andrax.axterminal.iInitialCommand", "commix");
                intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentstart);
            }
        });

        cardviewsqlmap.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

                intentstart.addCategory(Intent.CATEGORY_DEFAULT);
                intentstart.putExtra("andrax.axterminal.iInitialCommand", "sqlmap");
                intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentstart);
            }
        });

        cardviewpayloadmask.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

                intentstart.addCategory(Intent.CATEGORY_DEFAULT);
                intentstart.putExtra("andrax.axterminal.iInitialCommand", "payloadmask");
                intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentstart);
            }
        });

        cardviewabernathy.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

                intentstart.addCategory(Intent.CATEGORY_DEFAULT);
                intentstart.putExtra("andrax.axterminal.iInitialCommand", "abernathy");
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