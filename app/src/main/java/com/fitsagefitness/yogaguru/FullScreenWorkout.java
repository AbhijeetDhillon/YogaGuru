package com.fitsagefitness.yogaguru;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class FullScreenWorkout extends YouTubeBaseActivity {

    public ProgressBar barTimer;
    CountDownTimer countDownTimer;
    TextView textTimer, title, click;
    ImageView imagetimer;
    YouTubePlayerView youTubePlayerView;
    ImageView imageView;
    String[] youtubelink, asanaName;
    String  level,showMessage="true", getMessageStatus = "true";
    boolean isPause = false,ismChecked =false, isStress;
    int[] photos;
    int timerSeconds,position, max;
    double  progresstimer;
    RelativeLayout relativeLayout;
    private  YouTubePlayer mYouTubePlayer;
    private YouTubePlayer.OnInitializedListener mOnInitializedListener;
    long seconds;
    boolean isRunning = false;
    ImageView goLeft, goRight;
    SharedPreferences sharedPreferences;
    public static final String MyPREFERENCES = "NoMessage";
    boolean isInitializeYoutube = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_full_screen_workout);
        barTimer = findViewById(R.id.barTimer);
        textTimer = findViewById(R.id.textTimer);
        relativeLayout = findViewById(R.id.onclicktimer);
        timerSeconds =30;
        Bundle b=this.getIntent().getExtras();
        youtubelink =  b.getStringArray("youtube");
        photos = b.getIntArray("image");
        position = b.getInt("position");
        asanaName = b.getStringArray("asanaName");
        isStress = b.getBoolean("isStress");
        max = b.getInt("max");
        level = b.getString("Level");
        imageView = findViewById(R.id.workoutImage);
        youTubePlayerView =  (YouTubePlayerView) findViewById(R.id.player);
        imagetimer = findViewById(R.id.imagetimer);
        click = findViewById(R.id.click);
        title = findViewById(R.id.asanName);
        barTimer.setVisibility(View.GONE);
        progresstimer = (int) 4;
        goLeft = findViewById(R.id.goLeft);
        goRight = findViewById(R.id.goRight);
        isInitializeYoutube = false;
        if(!isStress) {
            if (level.equals("Intermediate")) {
                timerSeconds = 40;
                progresstimer = 3;
            } else if (level.equals("Advance")) {
                timerSeconds = 60;
                progresstimer = 2;
            }
        }
        if (position == max)
        {

            position++;
            title.setText("Corpse Pose");
            goRight.setVisibility(View.GONE);
        }

       else if(position == 0)
        {
            goLeft.setVisibility(View.GONE);
            title.setText(asanaName[position]);
        }

        else {
            title.setText(asanaName[position]);
        }
        sharedPreferences = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        getMessageStatus = sharedPreferences.getString(showMessage,"");


        if(getMessageStatus.contains("false")){}

        else {

            AlertDialog.Builder builder = new AlertDialog.Builder(FullScreenWorkout.this);
            builder.setTitle("Tips");
            builder.setMessage("Warm up before the routine by taking 10 deep breaths.\nTake a break of 30-40 secs after each Asana(Pose)");
            builder.setCancelable(false);
            builder.setPositiveButton("Okay",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //Do nothing here because we override this button later to change the close behaviour.
                            //However, we still need this because on older versions of Android unless we
                            //pass a handler the button doesn't get instantiated


                        }
                    });
            builder.setNegativeButton("Don't show it again",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                             SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString(showMessage, "false");
                            editor.apply();
                            dialog.cancel();

                        }
                    });
            final AlertDialog dialog = builder.create();

            dialog.setOnShowListener( new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface arg0) {
                    dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.colorPrimary));
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.colorPrimary));
                }
            });

            dialog.show();
        }
        ToggleButton toggle = (ToggleButton) findViewById(R.id.toggle);
        if(!isNetworkAvailable())
        {
           toggle.setEnabled(false);
        }

        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ismChecked = isChecked;
                if(!isNetworkAvailable() || mYouTubePlayer==null)
                {

                    isChecked = false;
                    ismChecked=false;
                }
                if (!isChecked && mYouTubePlayer!=null) {
                    imageView.setVisibility(View.GONE);
                    youTubePlayerView.setVisibility(View.VISIBLE);
                    if (position>=max) {
                        mYouTubePlayer.cueVideo("jkjAEQ7-UI4");
                    }
                    else
                    {
                        mYouTubePlayer.cueVideo(youtubelink[position]);

                    }
                } else {
                    youTubePlayerView.setVisibility(View.GONE);
                    imageView.setVisibility(View.VISIBLE);
                    if(position>=max)
                    {
                        imageView.setImageResource(R.drawable.shavasna);
                    }
                    else {

                        imageView.setImageResource(photos[position]);
                    }
                }
            }
        });

        display();

        }



    public void youtubePlay() {

        if (!isInitializeYoutube) {

            isInitializeYoutube = true;

            youTubePlayerView.setVisibility(View.VISIBLE);

            youTubePlayerView.initialize("AIzaSyC4I2mtcrckD6VB_BDR5UprEQJzlmj2br8",
                    mOnInitializedListener = new YouTubePlayer.OnInitializedListener() {
                        @Override
                        public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                            YouTubePlayer youTubePlayer, boolean b) {

                            // do any work here to cue video, play video, etc.
                            mYouTubePlayer = youTubePlayer;
                            if (position >= max) {
                                youTubePlayer.cueVideo("jkjAEQ7-UI4");
                            } else {
                                youTubePlayer.cueVideo(youtubelink[position]);

                            }
                        }

                        @Override
                        public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                            YouTubeInitializationResult youTubeInitializationResult) {

                        }
                    });
        }

        else
        {
            if (position >= max) {
                mYouTubePlayer.cueVideo("jkjAEQ7-UI4");
            } else {
                mYouTubePlayer.cueVideo(youtubelink[position]);

            }
        }
    }

    public void startWorkout(View V)
    {
        if(!isRunning) {
            startTimer(timerSeconds);
        }
        else
        {
            Toast.makeText(getBaseContext(), "Timer is running", Toast.LENGTH_LONG).show();
        }
    }

    private void startTimer(final int minuti) {

        barTimer.setVisibility(View.VISIBLE);

         countDownTimer = new CountDownTimer( minuti * 1000, 1000) {
            // 500 means, onTick function will be called at every 500 milliseconds
            int i = 1;
            @Override
            public void onTick(long leftTimeInMilliseconds) {
                seconds = leftTimeInMilliseconds / 1000;
                barTimer.setMax(timerSeconds);
                barTimer.setProgress((int) seconds);
                textTimer.setText((String.valueOf(timerSeconds - i)));
                i++;
                isRunning = true;
                imagetimer.setVisibility(View.GONE);
                click.setVisibility(View.GONE);
                // format the textview to show the easily readable format

            }
            @Override
            public void onFinish() {

                isRunning = false;
                imagetimer.setVisibility(View.VISIBLE);
                imagetimer.setImageResource(R.drawable.check);
                barTimer.setVisibility(View.GONE);
                if(textTimer.getText().equals("00")){
                    textTimer.setText("STOP");
                }
                else{
                    textTimer.setText("Done");
                    barTimer.setProgress(60*minuti);
                    relativeLayout.setClickable(false);
                    relativeLayout.setFocusable(false);

                }
            }


        }.start();

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    public void rightButton(View v)
    {
        barTimer.setVisibility(View.GONE);
        goRight.setVisibility(View.VISIBLE);
        goLeft.setVisibility(View.VISIBLE);
        timerreset();
        position++;
        if(position<max) {

                title.setText(asanaName[position]);
                if (isNetworkAvailable() && !ismChecked && mYouTubePlayer != null) {
                    mYouTubePlayer.cueVideo(youtubelink[position]);
                } else {
                    imageView.setImageResource(photos[position]);
                }
            }


        else if ((position >= max)) {
        position = max;
        goRight.setVisibility(View.GONE);
        title.setText("Corpse Pose");
            if (isNetworkAvailable() && mYouTubePlayer != null && !ismChecked) {
                mYouTubePlayer.cueVideo("jkjAEQ7-UI4");
            } else {
                imageView.setImageResource(R.drawable.shavasna);
            }


        }
    }

    public void leftButton(View v)
    {
        barTimer.setVisibility(View.GONE);
        goRight.setVisibility(View.VISIBLE);
        goLeft.setVisibility(View.VISIBLE);
        if(position>max)
        {
            position=position-2;
            title.setText("Corpse Pose");
            if (isNetworkAvailable() && mYouTubePlayer != null && !ismChecked) {
                mYouTubePlayer.cueVideo("jkjAEQ7-UI4");
            } else {
                imageView.setImageResource(R.drawable.shavasna);
            }
        }
        else if(position>0)
        {
            position--;
        }
         if(position==0) {
                goLeft.setVisibility(View.GONE);
            }
            timerreset();
            title.setText(asanaName[position]);
            if(isNetworkAvailable() && !ismChecked && mYouTubePlayer!=null) {
                mYouTubePlayer.cueVideo(youtubelink[position]);
            }
            else
            {
                imageView.setImageResource(photos[position]);
            }





    }

    public  void display()
    {
        if(isNetworkAvailable())
        {
            imageView.setVisibility(View.GONE);
            youtubePlay();
        }
        else
        {
            youTubePlayerView.setVisibility(View.GONE);
            Toast.makeText(getBaseContext(), "No Internet Connection, Can't Play Video", Toast.LENGTH_LONG).show();
            imageView.setImageResource(photos[position]);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        timerSeconds = (int) seconds;
        if(countDownTimer!=null) {
            countDownTimer.cancel();
            isPause = true;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(isPause) {
            startTimer(timerSeconds);
        }
        isPause = false;
    }

    public void timerreset()
    {
        isRunning = false;
        imagetimer.setVisibility(View.VISIBLE);
        imagetimer.setImageResource(R.drawable.timer);
        click.setVisibility(View.VISIBLE);
        if(countDownTimer!=null) {
            countDownTimer.cancel();
            textTimer.setText("Start");
            //barTimer.setProgress(100);
            relativeLayout.setClickable(true);
            relativeLayout.setFocusable(true);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(MainActivity.mInterstitialAd!=null && MainActivity.mInterstitialAd.isAdLoaded() && !MainActivity.isAdFree){

            MainActivity.mInterstitialAd.show();
        }
    }
}
