package com.thecrackertechnology.andrax;



import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import java.io.IOException;

public class AircrackFragment extends Fragment  implements View.OnClickListener{

    Switch mySwitch;

    public String interfaceaircrack;



    public AircrackFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.aircrack_fragment, container, false);

        mySwitch = (Switch) rootView.findViewById(R.id.switchaircrack);

        Button btnairodump = (Button) rootView.findViewById(R.id.btnstartairodump);

        final EditText editinteraircrack = (EditText) rootView.findViewById(R.id.edititerfaceaircrack);



        mySwitch.setChecked(false);

        mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    interfaceaircrack = editinteraircrack.getText().toString();

                    String deviceCommands = "su -c /data/data/com.thecrackertechnology.andrax/ANDRAX/bin/iwconfig " + interfaceaircrack + " mode monitor";
                    try {
                        Process process = Runtime.getRuntime().exec(deviceCommands);

                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(getContext(), "Erro! Interface don't supported", Toast.LENGTH_LONG).show();
                    }

                    /**
                     *
                     * Help me, i'm dying...
                     *
                     **/


                } else {

                    interfaceaircrack = editinteraircrack.getText().toString();

                    String cmd = "su -c /data/data/com.thecrackertechnology.andrax/ANDRAX/bin/iwconfig " + interfaceaircrack + " mode managed";
                    try {
                        Process process = Runtime.getRuntime().exec(cmd);

                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(getContext(), "Erro! Interface don't supported", Toast.LENGTH_LONG).show();
                    }
                }

            }
        });


        if(mySwitch.isChecked()){

            interfaceaircrack = editinteraircrack.getText().toString();

            String cmdtest = "su -c /data/data/com.thecrackertechnology.andrax/ANDRAX/bin/iwconfig " + interfaceaircrack + " mode monitor";
            try {
                Process process = Runtime.getRuntime().exec(cmdtest);

            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(getContext(), "Erro! Interface don't supported", Toast.LENGTH_LONG).show();
            }

        } else {

            interfaceaircrack = editinteraircrack.getText().toString();

            String cmd02 = "su -c /data/data/com.thecrackertechnology.andrax/ANDRAX/bin/iwconfig " + interfaceaircrack + " mode managed";
            try {
                Process process = Runtime.getRuntime().exec(cmd02);

            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(getContext(), "Erro! Interface don't supported", Toast.LENGTH_LONG).show();
            }
        }

        btnairodump.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                    Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

                    intentstart.addCategory(Intent.CATEGORY_DEFAULT);
                    intentstart.putExtra("andrax.axterminal.iInitialCommand", "airodump-ng " + interfaceaircrack);
                    intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intentstart);

            }
        });



        return rootView;



    }



    public void onClick(View v) {

    }



    @Override
    public void onPause() {

        super.onPause();
    }


    @Override
    public void onResume() {

        super.onResume();

    }


    @Override
    public void onDestroy() {

        super.onDestroy();
    }

}
