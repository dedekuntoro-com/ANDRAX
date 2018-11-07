package com.thecrackertechnology.andrax;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Random;

public class MainFragment extends Fragment  implements View.OnClickListener{



    public MainFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);




        Button btnrun = (Button) rootView.findViewById(R.id.btnrun);
        Button btndonation = (Button) rootView.findViewById(R.id.btndonation);
        Button btnyoutube = (Button) rootView.findViewById(R.id.btnyoutube);
        Button btntelegram = (Button) rootView.findViewById(R.id.btntelegram);
        Button btnmanual = (Button) rootView.findViewById(R.id.btnmanual);

        ImageView imgmain = (ImageView) rootView.findViewById(R.id.imageViewmainbanner);

        imgmain.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                clickimagerefresh();
            }
        });

            btnrun.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    Intent intentstart = new Intent("andrax.axterminal.RUN_SCRIPT");

                    intentstart.addCategory(Intent.CATEGORY_DEFAULT);
                    intentstart.putExtra("andrax.axterminal.iInitialCommand", "andrax");
                    intentstart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intentstart);


                }
            });


        btndonation.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=NRF8B7W2VHGEJ"));
                startActivity(intent);
            }
        });

        btnyoutube.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/channel/UCbjj-0WFsITWSb_Xf9WE5EQ"));
                startActivity(intent);
            }
        });

        btntelegram.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/andraxofficial"));
                startActivity(intent);            }
        });

        btnmanual.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://andrax-pentest.org/documentation"));
                startActivity(intent);
            }
        });






        return rootView;



    }

    public void clickimagerefresh() {
        MainFragment fragment = new MainFragment();
        android.support.v4.app.FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
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

}
