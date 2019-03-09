package com.thecrackertechnology.andrax;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;

public class TOPTools extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_toptools);



        CardView cardviewnmap = findViewById(R.id.card_view_nmap);
        CardView cardviewmetasploit = findViewById(R.id.card_view_metasploit);
        CardView cardviewaircrack = findViewById(R.id.card_view_aircrack);
        CardView cardviewroutersploit = findViewById(R.id.card_view_routersploit);
        CardView cardviewbettercap = findViewById(R.id.card_view_bettercap);
        CardView cardviewmitmproxy = findViewById(R.id.card_view_mitmproxy);
        CardView cardviewgophish = findViewById(R.id.card_view_gophish);
        CardView cardviewrapidscan = findViewById(R.id.card_view_rapidscan);
        CardView cardviewscapy = findViewById(R.id.card_view_scapy);
        CardView cardviewevilginx2 = findViewById(R.id.card_view_evilginx2);



        cardviewnmap.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

                intentstart.addCategory(Intent.CATEGORY_DEFAULT);
                intentstart.putExtra("andrax.axterminal.iInitialCommand", "nmap");
                intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentstart);
            }
        });

        /**
         *
         * Help me, i'm dying...
         *
         **/

        cardviewmetasploit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

                intentstart.addCategory(Intent.CATEGORY_DEFAULT);
                intentstart.putExtra("andrax.axterminal.iInitialCommand", "msfconsole");
                intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentstart);
            }
        });

        cardviewaircrack.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

                intentstart.addCategory(Intent.CATEGORY_DEFAULT);
                intentstart.putExtra("andrax.axterminal.iInitialCommand", "aircrack-ng");
                intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentstart);
            }
        });

        cardviewroutersploit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

                intentstart.addCategory(Intent.CATEGORY_DEFAULT);
                intentstart.putExtra("andrax.axterminal.iInitialCommand", "rsf");
                intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentstart);
            }
        });

        cardviewbettercap.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

                intentstart.addCategory(Intent.CATEGORY_DEFAULT);
                intentstart.putExtra("andrax.axterminal.iInitialCommand", "bettercap");
                intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentstart);
            }
        });

        cardviewmitmproxy.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

                intentstart.addCategory(Intent.CATEGORY_DEFAULT);
                intentstart.putExtra("andrax.axterminal.iInitialCommand", "mitmproxy");
                intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentstart);
            }
        });

        cardviewgophish.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

                intentstart.addCategory(Intent.CATEGORY_DEFAULT);
                intentstart.putExtra("andrax.axterminal.iInitialCommand", "gophish");
                intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentstart);
            }
        });

        cardviewrapidscan.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

                intentstart.addCategory(Intent.CATEGORY_DEFAULT);
                intentstart.putExtra("andrax.axterminal.iInitialCommand", "rapidscan --help");
                intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentstart);
            }
        });

        cardviewscapy.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

                intentstart.addCategory(Intent.CATEGORY_DEFAULT);
                intentstart.putExtra("andrax.axterminal.iInitialCommand", "scapy");
                intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentstart);
            }
        });

        cardviewevilginx2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

                intentstart.addCategory(Intent.CATEGORY_DEFAULT);
                intentstart.putExtra("andrax.axterminal.iInitialCommand", "evilginx2");
                intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentstart);
            }
        });

    }



}
