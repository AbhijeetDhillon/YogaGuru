package com.fitsagefitness.yogaguru;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.InterstitialAd;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements RewardedVideoAdListener,NavigationView.OnNavigationItemSelectedListener {

    Typeface typeface;
    public static InterstitialAd mInterstitialAd;
    static RewardedVideoAd mRewardedVideoAd;
    static SharedPreferences sharedPreferences;
    public static final String MyPREFERENCES = "DailyFree";
    static Boolean isFailedToLoad = true;
    static Boolean isAdLoaded = false;
    static Boolean isWatched1 = false;
    static Boolean isWatched2 = false;
    static int adCount = 0;
    static Boolean isAdFree = false;
    Calendar calender;
    static String currentDay;
    static String sharedcDay;
    int cDay,cHour,sharedcHour=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isFailedToLoad = true;
        isAdLoaded = false;
        adCount = 0;
        ImageView weightLoss = findViewById(R.id.weightloss);
        ImageView weightGain = findViewById(R.id.weightgain);
        ImageView tonedBody = findViewById(R.id.tonnedbody);
        ImageView fitness = findViewById(R.id.fitnesss);
        ImageView flexibility = findViewById(R.id.flexibility);
        ImageView stress = findViewById(R.id.stressrelief);



        // Use an activity context to get the rewarded video instance.
        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        mRewardedVideoAd.setRewardedVideoAdListener((RewardedVideoAdListener) this);


        //Store today's date for making the app ad-free

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date c = Calendar.getInstance().getTime();
        currentDay = sdf.format(c);
        sharedPreferences = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        isAdFreeValid();


        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        LinearLayout.LayoutParams cd_params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        cd_params.height = (int) (0.60 * width);
        cd_params.width = (int) (0.88 * width);
        weightLoss.setLayoutParams(cd_params);
        fitness.setLayoutParams(cd_params);
        stress.setLayoutParams(cd_params);
        weightGain.setLayoutParams(cd_params);
        flexibility.setLayoutParams(cd_params);
        tonedBody.setLayoutParams(cd_params);


        //Navigation Drawer
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);



        // Interstitial

        AudienceNetworkAds.initialize(this);
        mInterstitialAd = new com.facebook.ads.InterstitialAd(this, "1313592302127751_1320807618072886");
        mInterstitialAd.loadAd();



    }
    public static void loadRewardedVideoAd() {


        if(isAdFree)
        {
            CustomDialog.progressBar1.setVisibility(View.GONE);
            CustomDialog.watched1stAd.setVisibility(View.VISIBLE);
            CustomDialog.ad1loading.setVisibility(View.VISIBLE);
            CustomDialog.ad1loading.setText("You've Watched the 1st Ad!!!");
            CustomDialog.progressBar2.setVisibility(View.GONE);
            CustomDialog.watched2ndAd.setVisibility(View.VISIBLE);
            CustomDialog.ad2loading.setVisibility(View.VISIBLE);
            CustomDialog.ad2loading.setText("You've Watched the 2nd Ad!!!");
            CustomDialog.watchAd2.setVisibility(View.GONE);
            CustomDialog.isSucess.setVisibility(View.VISIBLE);
            CustomDialog.yes.setEnabled(true);
            CustomDialog.yes.setFocusable(true);
            CustomDialog.no.setFocusable(false);
            CustomDialog.no.setEnabled(false);
            CustomDialog.yes.setBackgroundColor(CustomDialog.no.getContext().getResources().getColor(R.color.colorPrimary));
            CustomDialog.no.setBackgroundColor(CustomDialog.no.getContext().getResources().getColor(R.color.disable));
        }

        else {
            mRewardedVideoAd.loadAd("ca-app-pub-8178002946812610/9469665608",
                    new AdRequest.Builder().build());

        }
        if (isWatched1)
        {
            CustomDialog.watchAd1.setVisibility(View.GONE);
            CustomDialog.progressBar1.setVisibility(View.GONE);
            CustomDialog.watched1stAd.setVisibility(View.VISIBLE);
            CustomDialog.ad1loading.setVisibility(View.VISIBLE);
            CustomDialog.ad1loading.setText("Watched the 1st Ad!!!");
        }


    }


    public void weightLoss(View view)
    {
        Intent intent = new Intent(MainActivity.this,Level.class);
        intent.putExtra("Program", "Weight Loss");
        startActivity(intent);

    }
    public void flexibility(View view)
    {
        Intent intent = new Intent(MainActivity.this,Level.class);
        intent.putExtra("Program", "Flexibility and Strength");
        startActivity(intent);

    }
    public void stress(View view)
    {
        Intent intent = new Intent(MainActivity.this,Workouts.class);
        intent.putExtra("Stress", "Stress and Anxiety");
        intent.putExtra("Program", "Stress and Anxiety");
        startActivity(intent);

    }
    public void weightGain(View view)
    {
        Intent intent = new Intent(MainActivity.this,Level.class);
        intent.putExtra("Program", "Weight Gain");
        startActivity(intent);

    }
    public void fitness(View view)
    {
        Intent intent = new Intent(MainActivity.this,Level.class);
        intent.putExtra("Program", "Fitness and Endurance");
        startActivity(intent);

    }
    public void tonnedBody(View view)
    {
        Intent intent = new Intent(MainActivity.this,Level.class);
        intent.putExtra("Program", "Toned Body");
        startActivity(intent);

    }

    protected void sendEmail() {
        Log.i("Send email", "");

        String[] TO = {"fitsagefitness@gmail.com"};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");


        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Yoga Guru - Query");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Please type your message below along with your Smart Phone model and name. Thank You!");

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
            Log.i("Finished sending email", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(MainActivity.this,
                    "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }

    public void displayDialog()
    {
        CustomDialog cdd=new CustomDialog(MainActivity.this);
        cdd.show();
    }

    @Override
    public void onRewardedVideoAdLoaded() {

        sharedPreferences = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        isWatched1 = sharedPreferences.getBoolean("isWatched1",false);
        isWatched2 = sharedPreferences.getBoolean("isWatched2",false);

        if(!MainActivity.isWatched1) {
            //editor.putBoolean("isWatched1",true);
            CustomDialog.watchAd1.setVisibility(View.VISIBLE);
            CustomDialog.watched1stAd.setVisibility(View.GONE);
            CustomDialog.ad1loading.setVisibility(View.GONE);
            CustomDialog.progressBar1.setVisibility(View.GONE);
            CustomDialog.watchAd1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (MainActivity.mRewardedVideoAd.isLoaded()) {
                        MainActivity.mRewardedVideoAd.show();

                    }
                }
            });
        }
            else if (!MainActivity.isWatched2)
            {
//                editor.putBoolean("isWatched2", true);
//                editor.putBoolean("isWatched1",true);
                CustomDialog.watchAd2.setVisibility(View.VISIBLE);
                CustomDialog.watched2ndAd.setVisibility(View.GONE);
                CustomDialog.ad2loading.setVisibility(View.GONE);
                CustomDialog.progressBar2.setVisibility(View.GONE);
                CustomDialog.watchAd2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (MainActivity.mRewardedVideoAd.isLoaded()) {
                            MainActivity.mRewardedVideoAd.show();
                        }
                    }
                });








            }
            isAdLoaded = true;
            isFailedToLoad = false;
        editor.apply();
    }

    @Override
    public void onRewardedVideoAdOpened() {

    }

    @Override
    public void onRewardedVideoStarted() {

    }

    @Override
    public void onRewardedVideoAdClosed() {


            loadRewardedVideoAd();

    }

    @Override
    public void onRewarded(RewardItem rewardItem) {


        //CountDownTimer();

        if(!isWatched1)
        {
            CustomDialog.ad1loading.setVisibility(View.VISIBLE);
            CustomDialog.progressBar1.setVisibility(View.VISIBLE);
            CustomDialog.ad2loading.setVisibility(View.VISIBLE);
            CustomDialog.progressBar2.setVisibility(View.VISIBLE);
        }

        else if(!isWatched2)
        {
            CustomDialog.progressBar1.setVisibility(View.GONE);
            CustomDialog.ad2loading.setVisibility(View.VISIBLE);
            CustomDialog.progressBar2.setVisibility(View.VISIBLE);
        }

        else
        {
            CustomDialog.progressBar1.setVisibility(View.GONE);
            CustomDialog.progressBar2.setVisibility(View.GONE);
        }





    }

    @Override
    public void onRewardedVideoAdLeftApplication() {

    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {
        if(isWatched1)
        {
            CustomDialog.watchAd1.setVisibility(View.GONE);
            CustomDialog.progressBar1.setVisibility(View.GONE);
            CustomDialog.watched1stAd.setVisibility(View.VISIBLE);
            CustomDialog.ad1loading.setVisibility(View.VISIBLE);
            CustomDialog.ad1loading.setText("Watched the 1st Ad!!!");
            CustomDialog.isSucess.setVisibility(View.VISIBLE);
            CustomDialog.isSucess.setText("Ad Failed to load, Please Try Again Later");
            CustomDialog.progressBar2.setVisibility(View.GONE);
            CustomDialog.watched2ndAd.setVisibility(View.GONE);
            CustomDialog.ad2loading.setVisibility(View.GONE);
        }
        else {
            CustomDialog.progressBar1.setVisibility(View.GONE);
            CustomDialog.progressBar2.setVisibility(View.GONE);
            CustomDialog.isSucess.setVisibility(View.VISIBLE);
            CustomDialog.isSucess.setText("Ad Failed to load, Please Try Again Later");
            CustomDialog.watched2ndAd.setVisibility(View.GONE);
            CustomDialog.ad2loading.setVisibility(View.GONE);
            CustomDialog.watched1stAd.setVisibility(View.GONE);
            CustomDialog.ad1loading.setVisibility(View.GONE);
        }
        isFailedToLoad = true;
    }

    @Override
    public void onRewardedVideoCompleted() {

        sharedPreferences = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        adCount ++;
        if(adCount==1)
        {
            isWatched1 = true;
            editor.putBoolean("isWatched1",true);
            CustomDialog.watchAd1.setVisibility(View.GONE);
            CustomDialog.progressBar1.setVisibility(View.GONE);
            CustomDialog.watched1stAd.setVisibility(View.VISIBLE);
            CustomDialog.ad1loading.setVisibility(View.VISIBLE);
            CustomDialog.ad1loading.setText("Watched the 1st Ad!!!");
        }
        else if(adCount==2) {
            isWatched2 = true;
            editor.putBoolean("isWatched2", true);
            editor.putBoolean("isWatched1",true);
            CustomDialog.progressBar2.setVisibility(View.GONE);
            CustomDialog.watched2ndAd.setVisibility(View.VISIBLE);
            CustomDialog.ad2loading.setVisibility(View.VISIBLE);
            CustomDialog.ad2loading.setText("Watched the 2nd Ad!!!");
            CustomDialog.watchAd2.setVisibility(View.GONE);
            CustomDialog.isSucess.setVisibility(View.VISIBLE);
            editor.putString("currentDay", currentDay);
            editor.apply();
            isAdFreeValid();
        }

        editor.apply();
    }

    public static void isAdFreeValid()
    {

        sharedcDay = sharedPreferences.getString("currentDay","");


        if(sharedcDay.equals(currentDay))
        {
            isAdFree = true;
        }

        else{


            isAdFree = false;
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("isWatched1",false);
            editor.putBoolean("isWatched2", false);
            editor.apply();
        }

    }

    public void CountDownTimer()
    {
        new CountDownTimer( 15 * 1000, 1000) {
            // 500 means, onTick function will be called at every 500 milliseconds

            @Override
            public void onTick(long leftTimeInMilliseconds) {


            }
            @Override
            public void onFinish() {



            }


        }.start();
    }


    private Boolean exit = false;
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (exit) {
                finish(); // finish activity
            } else {
                Toast.makeText(this, "Press Back again to Exit.",
                        Toast.LENGTH_SHORT).show();
                exit = true;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        exit = false;
                    }
                }, 3 * 1000);

            }
        }
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.diet) {
            Intent intent = new Intent(MainActivity.this, Diet.class);
            startActivity(intent);
            return true;
        }
//        else if (id == R.id.myyoga) {
//
//        }
//        else if (id == R.id.noads) {
//            displayDialog();
//            return true;
//
//        }
//
        else if (id == R.id.credits) {

            Intent intent = new Intent(MainActivity.this, credits.class);
            startActivity(intent);
            return true;

        } else if (id == R.id.nav_share) {

            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=com.fitsagefitness.yogaguru");
            sendIntent.setType("text/plain");
            startActivity(sendIntent);

        } else if (id == R.id.nav_send) {
            sendEmail();
            return true;

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
