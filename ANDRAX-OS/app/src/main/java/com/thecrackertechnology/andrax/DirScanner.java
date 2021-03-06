package com.thecrackertechnology.andrax;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class DirScanner extends AppCompatActivity {

    ProgressDialog dialog;
    String bufferlinelist = "";

    TextView textresult;
    TextView textresult02;

    EditText editexclude;

    String site;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dir_scanner);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle extras = getIntent().getExtras();
        if(extras !=null){
            site = extras.getString("HOSTKEY");
        }

        dialog = ProgressDialog.show(DirScanner.this, null,"Scanning files and dir... This can take a long time.");
        dialog.setCancelable(false);
        dialog.setProgressStyle(R.style.AppCompatAlertDialogStyle);

        textresult = (TextView) findViewById(R.id.textViewdirresult);
        textresult02 = (TextView) findViewById(R.id.textViewdirresult02);

        editexclude = (EditText) findViewById(R.id.editTextdirscannercode);

        Button btnexclude = (Button) findViewById(R.id.buttondirexcludecode);

        textresult.setText("");

        btnexclude.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                textresult02.setText("");

                String codeexclude = editexclude.getText().toString();

                try {


                    Process process = Runtime.getRuntime().exec("su -c cat /data/data/com.thecrackertechnology.andrax/ANDRAX/dirscanner/output " + "| grep -v " + "\"" + codeexclude + "\"");
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(process.getInputStream()));
                    int read;

                    char[] buffer = new char[8000];
                    StringBuffer output = new StringBuffer();
                    while ((read = reader.read(buffer)) > 0) {
                        output.append(buffer, 0, read );

                        textresult02.append("DirScanner Resul:\n\n" + output.toString() + "\n");

                    }
                    reader.close();




                    process.waitFor();




                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }


            }
        });



        hackall2 task = new hackall2();
        task.execute();

    }


    private class hackall2 extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {


            ArrayList<String> listlines = new ArrayList<String>();
            try {

                Process process = Runtime.getRuntime().exec("su -c /data/data/com.thecrackertechnology.andrax/ANDRAX/bin/dirscanner " + "/data/data/com.thecrackertechnology.andrax/ANDRAX/dirscanner/'*.txt' " + site + " | grep CODE | tee /data/data/com.thecrackertechnology.andrax/ANDRAX/dirscanner/output");

                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(process.getInputStream()));



                while ((bufferlinelist = reader.readLine()) != null) {

                    listlines.add(bufferlinelist);
                }

                if((bufferlinelist = reader.readLine()) == null){



                }

                /**
                 *
                 * Help me, i'm dying...
                 *
                 **/


                reader.close();







                process.waitFor();




            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }



            return listlines.toString();
        }


        protected void onPostExecute(String result) {
            String outtext;
            String outtext02;

            outtext = result.replace(",", "\n");
            outtext02 = outtext.replace("[", "");

            textresult.append(outtext02.replace("]", ""));



            dialog.dismiss();

        }


    }

}
