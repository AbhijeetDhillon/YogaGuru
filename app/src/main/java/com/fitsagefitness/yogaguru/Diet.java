package com.fitsagefitness.yogaguru;

import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewDebug;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdIconView;
import com.facebook.ads.AdOptionsView;
import com.facebook.ads.NativeAdLayout;
import com.facebook.ads.NativeAdListener;
import com.facebook.ads.NativeBannerAd;

import java.util.ArrayList;
import java.util.List;

public class Diet extends AppCompatActivity {

    RadioGroup radioGroup, radioGroup2;
    RadioButton genderradioButton,VegradioButton ;
    Boolean goal = false, foodType = false;


    private NativeAdLayout nativeAdLayout;
    private LinearLayout adView;
    static NativeBannerAd nativeBannerAd;
    private final String TAG = NativeBannerAdActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet);
        radioGroup=(RadioGroup)findViewById(R.id.radioGroup);
        radioGroup2=(RadioGroup)findViewById(R.id.vegnv);


        nativeBannerAd = new NativeBannerAd(this, "663119437543573_663120400876810");
        nativeBannerAd.setAdListener(new NativeAdListener() {
            @Override
            public void onMediaDownloaded(Ad ad) {
                // Native ad finished downloading all assets
                Log.e(TAG, "Native ad finished downloading all assets.");
            }

            @Override
            public void onError(Ad ad, AdError adError) {
                // Native ad failed to load
                Log.e(TAG, "Native ad failed to load: " + adError.getErrorMessage());
            }

            @Override
            public void onAdLoaded(Ad ad) {
                if (nativeBannerAd == null || nativeBannerAd != ad) {
                    return;
                }
                // Inflate Native Banner Ad into Container
                inflateAd(nativeBannerAd);
            }


            @Override
            public void onAdClicked(Ad ad) {
                // Native ad clicked
                Log.d(TAG, "Native ad clicked!");
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Native ad impression
                Log.d(TAG, "Native ad impression logged!");
            }
        });
        // load the ad
        nativeBannerAd.loadAd();
    }

    public void View(View v)
    {
        int selectedId = radioGroup.getCheckedRadioButtonId();
        genderradioButton = (RadioButton) findViewById(selectedId);
        if(selectedId==-1){
            Toast.makeText(Diet.this,"Please Select Your Goal", Toast.LENGTH_SHORT).show();
        }

        else
        {
            goal = true;
        }


        int foodchoice = radioGroup2.getCheckedRadioButtonId();
        VegradioButton = (RadioButton) findViewById(foodchoice);

        if(foodchoice==-1){
            Toast.makeText(Diet.this,"Please Select Food Type", Toast.LENGTH_SHORT).show();
        }

        else
        {
            foodType = true;
        }



        if(goal && foodType)
        {
            if(MainActivity.mInterstitialAd!=null && MainActivity.mInterstitialAd.isAdLoaded() && !MainActivity.isAdFree){
                MainActivity.mInterstitialAd.show();
            }

            Intent intent = new Intent(Diet.this, ViewDiet.class);
            intent.putExtra("Category", genderradioButton.getText());
            intent.putExtra("Type", VegradioButton.getText());
            startActivity(intent);
        }





    }


    private void inflateAd(NativeBannerAd nativeBannerAd) {
        // Unregister last ad
        nativeBannerAd.unregisterView();

        // Add the Ad view into the ad container.
        nativeAdLayout = findViewById(R.id.native_banner_ad_container);
        LayoutInflater inflater = LayoutInflater.from(Diet.this);
        // Inflate the Ad view.  The layout referenced is the one you created in the last step.
        adView = (LinearLayout) inflater.inflate(R.layout.native_banner_ad_unit, nativeAdLayout, false);
        nativeAdLayout.addView(adView);

        // Add the AdChoices icon
        RelativeLayout adChoicesContainer = adView.findViewById(R.id.ad_choices_container);
        AdOptionsView adOptionsView = new AdOptionsView(Diet.this, nativeBannerAd, nativeAdLayout);
        adChoicesContainer.removeAllViews();
        adChoicesContainer.addView(adOptionsView, 0);

        // Create native UI using the ad metadata.
        TextView nativeAdTitle = adView.findViewById(R.id.native_ad_title);
        TextView nativeAdSocialContext = adView.findViewById(R.id.native_ad_social_context);
        TextView sponsoredLabel = adView.findViewById(R.id.native_ad_sponsored_label);
        AdIconView nativeAdIconView = adView.findViewById(R.id.native_icon_view);
        Button nativeAdCallToAction = adView.findViewById(R.id.native_ad_call_to_action);

        // Set the Text.
        nativeAdCallToAction.setText(nativeBannerAd.getAdCallToAction());
        nativeAdCallToAction.setVisibility(
                nativeBannerAd.hasCallToAction() ? View.VISIBLE : View.INVISIBLE);
        nativeAdTitle.setText(nativeBannerAd.getAdvertiserName());
        nativeAdSocialContext.setText(nativeBannerAd.getAdSocialContext());
        sponsoredLabel.setText(nativeBannerAd.getSponsoredTranslation());

        // Register the Title and CTA button to listen for clicks.
        List<View> clickableViews = new ArrayList<>();
        clickableViews.add(nativeAdTitle);
        clickableViews.add(nativeAdCallToAction);
        nativeBannerAd.registerViewForInteraction(adView, nativeAdIconView, clickableViews);
    }

}
