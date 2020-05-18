package com.fitsagefitness.yogaguru;

import android.app.Application;

import com.facebook.ads.AudienceNetworkAds;

/**
 * Created by Abhijeet on 8/31/2019.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // Initialize the Audience Network SDK
        AudienceNetworkAds.initialize(this);
    }
}
