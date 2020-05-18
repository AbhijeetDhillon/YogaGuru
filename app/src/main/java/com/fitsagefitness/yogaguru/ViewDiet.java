package com.fitsagefitness.yogaguru;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdIconView;
import com.facebook.ads.AdOptionsView;
import com.facebook.ads.NativeAdLayout;
import com.facebook.ads.NativeAdListener;
import com.facebook.ads.NativeBannerAd;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class ViewDiet extends AppCompatActivity {


    private NativeAdLayout nativeAdLayout;
    private LinearLayout adView;
    static NativeBannerAd nativeBannerAd;
    ProgressBar progressBar;
    private final String TAG = NativeBannerAdActivity.class.getSimpleName();
    String link;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_diet);
        String dietCategory = getIntent().getStringExtra("Category");
        String foodType = getIntent().getStringExtra("Type");
       // PDFView pdfView = findViewById(R.id.pdfView);
        setTitle(dietCategory);





        WebView mywebview = (WebView) findViewById(R.id.webView);
        progressBar = findViewById(R.id.progress_bar);
        mywebview.setWebViewClient(new WebViewController(progressBar));
        mywebview.getSettings().setJavaScriptEnabled(true);
        mywebview.getSettings().setLoadWithOverviewMode(true);
        mywebview.getSettings().setUseWideViewPort(true);
        boolean isNetworkAvailable = isNetworkAvailable();







        if(foodType.equals("Veg"))

        {

            switch (dietCategory) {

                case "Weight Gain / Bulk":
                    link="https://drive.google.com/file/d/1GjUm3xQGmQJBBWw84-u6URZYoVZa5z6-/view";
                    break;
                case "Weight Loss":
                    link="https://drive.google.com/file/d/1RJyPOTpqL7XqqR4hEFcKP-_4ffduiyoL/view";
                    break;
                case "Fitness/Stress Relief/Flexibility":
                    link="https://drive.google.com/file/d/1mQeqYKY9qJCIbmOtqNAqaXUf1Yb0i87D/view";
                    break;
                case "Shredded Body":
                    link="https://drive.google.com/file/d/1NtubRdNlfbgsv2O3wN5gC-YL79uUmykJ/view";
                    break;

            }
        }

        else

        {
            switch (dietCategory) {

                case "Weight Gain / Bulk":
                    link="https://drive.google.com/file/d/1FJ-8-RVS7NvJ-G7H9s8x3saBszF4Yg6K/view";
                    break;
                case "Weight Loss":
                    link="https://drive.google.com/file/d/1rafz6IH4lYEPA_ACPBFfSiwLpidAPgfK/view";
                    break;
                case "Fitness/Stress Relief/Flexibility":
                    link="https://drive.google.com/file/d/1BCWFk3w7JMQbk2jThaDlatnQ4zIq6HEP/view";
                    break;
                case "Shredded Body":
                    link = "https://drive.google.com/file/d/1EtvsZzD6pHxJarffRs_rTpxMSs88_PSU/view";
                    break;

            }
        }





        if (isNetworkAvailable) {
            mywebview.loadUrl(link);
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(ViewDiet.this);
            builder.setTitle("No Internet Connection");
            builder.setMessage("Please make sure you have an active internet connection");
            builder.setPositiveButton("Retry",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog,
                                            int which) {
                            finish();
                            startActivity(getIntent());
                        }
                    });
            builder.setNegativeButton("Cancel",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog,
                                            int which) {
                            dialog.dismiss();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }







}

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


}






