package com.thecrackertechnology.andrax;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.Window;

public class Dco_network_hacking extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

//Remove notification bar
        //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.dco_network_hacking);

        CardView cardviewarpspoof = findViewById(R.id.card_view_arpspoof);
        CardView cardviewbettercap = findViewById(R.id.card_view_bettercap);
        CardView cardviewmitmproxy = findViewById(R.id.card_view_mitmproxy);
        CardView cardviewevilginx2 = findViewById(R.id.card_view_evilginx2);
        CardView cardviewsocat = findViewById(R.id.card_view_socat);
        CardView cardviewscapy = findViewById(R.id.card_view_scapy);

        cardviewarpspoof.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

                intentstart.addCategory(Intent.CATEGORY_DEFAULT);
                intentstart.putExtra("andrax.axterminal.iInitialCommand", "sudo arpspoof");
                intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentstart);
            }
        });

        cardviewbettercap.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

                intentstart.addCategory(Intent.CATEGORY_DEFAULT);
                intentstart.putExtra("andrax.axterminal.iInitialCommand", "sudo bettercap");
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

        cardviewsocat.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

                intentstart.addCategory(Intent.CATEGORY_DEFAULT);
                intentstart.putExtra("andrax.axterminal.iInitialCommand", "socat -h");
                intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentstart);
            }
        });

        cardviewscapy.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

                intentstart.addCategory(Intent.CATEGORY_DEFAULT);
                intentstart.putExtra("andrax.axterminal.iInitialCommand", "sudo scapy");
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
