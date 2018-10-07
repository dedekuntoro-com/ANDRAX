package com.thecrackertechnology.andrax;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class NmapFragment extends Fragment  implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    public String item;

    public String spinner_value;

    public String scanstr;

    public String spinneritem;


    public NmapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_nmap, container, false);



        Button btrscan = (Button) rootView.findViewById(R.id.btnscan);

        final EditText editText = (EditText) rootView.findViewById(R.id.editTexthostscan);



        // Spinner element
        Spinner spinner = (Spinner) rootView.findViewById(R.id.spinnernmap);



        // Spinner click listener
        spinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> scanmodes = new ArrayList<String>();
        scanmodes.add("Intense Steath: -sS -A -Pn");
        scanmodes.add("TCP SYN: -sS");
        scanmodes.add("TCP Connect: -sT");
        scanmodes.add("TCP ACK: -sA");
        scanmodes.add("UDP: -sU");
        scanmodes.add("ALL Ports: -p1-65535");
        scanmodes.add("ALL Ports SYN: -sS -p1-65535");
        scanmodes.add("ALL Ports Connect: -sT -p1-65535");
        scanmodes.add("Top 50 Ports: --top-ports 50");
        scanmodes.add("Steath Version: -sS -sV");
        scanmodes.add("Os fingerprint: -O");
        scanmodes.add("Os fingerprint SYN: -sS -O");


        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, scanmodes);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);



        btrscan.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                scanstr = editText.getText().toString();

                startscan(scanstr);

            }
        });


        return rootView;



    }





    public void onClick(View v) {

    }


    /**
     * Called when leaving the activity
     */
    @Override
    public void onPause() {

        super.onPause();
    }

    /**
     * Called when returning to the activity
     */
    @Override
    public void onResume() {

        super.onResume();

    }

    /**
     * Called before the activity is destroyed
     */
    @Override
    public void onDestroy() {

        super.onDestroy();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        String item = parent.getItemAtPosition(position).toString();

        spinneritem = item;

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "SCAN Mode: " + item, Toast.LENGTH_SHORT).show();


    }



    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void startscan(String scanstr) {
        String scanmode = "";

        switch (spinneritem) {
            case "Intense Steath: -sS -A -Pn":
                scanmode = "-sS -A -Pn";
                break;
            case "TCP SYN: -sS":
                scanmode = "-sS";
                break;
            case "TCP Connect: -sT":
                scanmode = "-sT";
                break;
            case "TCP ACK: -sA":
                scanmode = "-sA";
                break;
            case "UDP: -sU":
                scanmode = "-sU";
                break;
            case "ALL Ports: -p1-65535":
                scanmode = "-p1-65535";
                break;
            case "ALL Ports SYN: -sS -p1-65535":
                scanmode = "-sS -p1-65535";
                break;
            case "ALL Ports Connect: -sT -p1-65535":
                scanmode = "-sT -p1-65535";
                break;
            case "Top 50 Ports: --top-ports 50":
                scanmode = "--top-ports 50";
                break;
            case "Steath Version: -sS -sV":
                scanmode = "-sS -sV";
                break;
            case "Os fingerprint: -O":
                scanmode = "-O";
                break;
            case "Os fingerprint SYN: -sS -O":
                scanmode = "-sS -O";
                break;
        }

        Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

        intentstart.addCategory(Intent.CATEGORY_DEFAULT);
        intentstart.putExtra("andrax.axterminal.iInitialCommand", "sudo nmap " + scanstr + " " + scanmode + " " + "--system-dns");
        intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intentstart);


    }



}