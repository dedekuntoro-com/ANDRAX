package com.thecrackertechnology.andrax;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.Window;

public class Dco_Password_Hacking extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        this.requestWindowFeature(Window.FEATURE_NO_TITLE);




        setContentView(R.layout.dco_password_hacking);

        CardView cardviewhydra = findViewById(R.id.card_view_hydra);
        CardView cardviewncrack = findViewById(R.id.card_view_ncrack);
        CardView cardviewjohn = findViewById(R.id.card_view_john);
        CardView cardviewcrunch = findViewById(R.id.card_view_crunch);
        CardView cardviewmassh = findViewById(R.id.card_view_massh);
        CardView cardviewacccheck = findViewById(R.id.card_view_acccheck);
        CardView cardviewsshauditor = findViewById(R.id.card_view_ssh_auditor);
        CardView cardviewbopscrk = findViewById(R.id.card_view_bopscrk);


        cardviewhydra.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

                intentstart.addCategory(Intent.CATEGORY_DEFAULT);
                intentstart.putExtra("andrax.axterminal.iInitialCommand", "hydra");
                intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentstart);
            }
        });

        cardviewncrack.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

                intentstart.addCategory(Intent.CATEGORY_DEFAULT);
                intentstart.putExtra("andrax.axterminal.iInitialCommand", "ncrack");
                intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentstart);
            }
        });

        cardviewjohn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

                intentstart.addCategory(Intent.CATEGORY_DEFAULT);
                intentstart.putExtra("andrax.axterminal.iInitialCommand", "john");
                intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentstart);
            }
        });

        /**
         *
         * Help me, i'm dying...
         *
         **/

        cardviewcrunch.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

                intentstart.addCategory(Intent.CATEGORY_DEFAULT);
                intentstart.putExtra("andrax.axterminal.iInitialCommand", "crunch");
                intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentstart);
            }
        });

        cardviewmassh.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

                intentstart.addCategory(Intent.CATEGORY_DEFAULT);
                intentstart.putExtra("andrax.axterminal.iInitialCommand", "massh-enum --help");
                intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentstart);
            }
        });

        cardviewacccheck.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

                intentstart.addCategory(Intent.CATEGORY_DEFAULT);
                intentstart.putExtra("andrax.axterminal.iInitialCommand", "acccheck");
                intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentstart);
            }
        });


        cardviewsshauditor.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

                intentstart.addCategory(Intent.CATEGORY_DEFAULT);
                intentstart.putExtra("andrax.axterminal.iInitialCommand", "ssh-auditor");
                intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentstart);
            }
        });

        cardviewbopscrk.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

                intentstart.addCategory(Intent.CATEGORY_DEFAULT);
                intentstart.putExtra("andrax.axterminal.iInitialCommand", "bopscrk");
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
