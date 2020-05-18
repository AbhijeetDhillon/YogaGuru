package com.fitsagefitness.yogaguru;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;



public class CustomDialog extends Dialog implements
        android.view.View.OnClickListener {

    public Activity c;
    public Dialog d;
    public static Button yes, no;

    public CustomDialog(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
    }

    static ProgressBar progressBar1;
    static ProgressBar progressBar2;
    static TextView ad1loading;
    static TextView ad2loading;
    static ImageView watched1stAd;
    static ImageView watched2ndAd;
    static Button watchAd1;
    static Button watchAd2;
    static TextView isSucess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog);
        yes = (Button) findViewById(R.id.done);
        no = (Button) findViewById(R.id.cancel);
        no.setBackgroundColor(no.getContext().getResources().getColor(R.color.colorPrimary));
        yes.setEnabled(false);
        yes.setFocusable(false);
        yes.setBackgroundColor(yes.getContext().getResources().getColor(R.color.disable));
        no.setFocusable(true);
        no.setEnabled(true);
        if(MainActivity.isAdFree)
        {
            yes.setEnabled(true);
            yes.setFocusable(true);
            no.setFocusable(false);
            no.setEnabled(false);
            yes.setBackgroundColor(no.getContext().getResources().getColor(R.color.colorPrimary));
            no.setBackgroundColor(no.getContext().getResources().getColor(R.color.disable));
        }
        yes.setOnClickListener(this);
        no.setOnClickListener(this);
        progressBar1 = findViewById(R.id.progressbar);
        progressBar2 = findViewById(R.id.progressbar2);
        ad1loading = findViewById(R.id.ad1loading);
        ad2loading = findViewById(R.id.ad2loading);
        watched1stAd = findViewById(R.id.watched1stAd);
        watched2ndAd = findViewById(R.id.watched2ndAd);
        watchAd1 = findViewById(R.id.watchAd1);
        watchAd2 = findViewById(R.id.watchAd2);
        isSucess = findViewById(R.id.sucessOrFail);
        MainActivity.loadRewardedVideoAd();


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.done:
                cancel();
                break;
            case R.id.cancel:
                cancel();
                break;
            default:
                break;
        }
        dismiss();
    }


}
