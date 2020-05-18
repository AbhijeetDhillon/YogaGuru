package com.fitsagefitness.yogaguru;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Thread welcomeThread = new Thread() {

            @Override
            public void run() {
                try {

                    super.run();
                    sleep(1500);
                } catch (Exception e) {

                } finally {
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {



                              //  textView.setVisibility(View.GONE);


                        }
                    });


                    Intent i = new Intent(Splash.this,
                            MainActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        };
        welcomeThread.start();
    }


}
