package com.example.yasmeen.timer;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class MainActivity extends AppCompatActivity {

    ProgressBar mProgressBar;
    CountDownTimer mCountdownTimer ;
    Button mStartBtn ;
    TextView mRemainingTime ;
    EditText mStartTime;
    int i =0;
    int hours=0 , minites =0, seconds=0;
    static  int SATIC_HOURS_TO_MILLI = 60*60*1000;
    static  int SATIC_MINITES_TO_MILLI =60*1000;
    SimpleDateFormat simpleDateFormat;
    @SuppressLint("SimpleDateFormat")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mProgressBar = findViewById(R.id.progressBar);
        mStartBtn = findViewById(R.id.resetBtn);
        mRemainingTime = findViewById(R.id.remainninghour);
        mStartTime = findViewById(R.id.timestart);
        mProgressBar.setProgress(i);
        simpleDateFormat = new SimpleDateFormat("hh:mm:ss");

      /*  mCountdownTimer = new CountDownTimer(5000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                i++;
                mProgressBar.setProgress((int) i*100/(5000/1000));
            }

            @Override
            public void onFinish() {
                i++;
                mProgressBar.setProgress(100);

            }
        };*/
        mStartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date = mStartTime.getText().toString();
                if (!TextUtils.isEmpty(date)){
                    try {
                        java.util.Date paresdate_ = simpleDateFormat.parse(date);
                        String times[] = date.split(":");
                        long x = 0;
                        x = (long) Integer.parseInt(times[0])* SATIC_HOURS_TO_MILLI + (long) Integer.parseInt(times[1])*SATIC_MINITES_TO_MILLI+
                                (long) Integer.parseInt(times[2])*1000 ;
                        hours = Integer.parseInt(times[0]);
                        minites = Integer.parseInt(times[1]);
                        seconds = Integer.parseInt(times[2]);
                        if (checkFormat(hours,minites,seconds))
                            beginTimer(x);
                        else
                            Log.e("Format", "onClick: Format Error" );

                    } catch (ParseException e) {
                        e.printStackTrace();
                        Log.e("Format", "onClick: Format Error" );
                    }
                }
                else {
                    Log.e("Empty String", "onClick: empty string" );
                }
            }
        });
       // mCountdownTimer.start();
    }

    private boolean checkFormat(int hours, int minites, int seconds) {
        if (hours >  24 && minites > 60 && seconds > 60)
            return  true;
        else
            return false;
    }

    void beginTimer (final long x)
    {
        i=0;
        mProgressBar.setProgress(i);
          mCountdownTimer = new CountDownTimer(x,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int y =
                i++;
                if (seconds == 00 && minites == 00)
                {
                    seconds = 59 ;
                    minites = 59 ;
                    hours--;
                }
                else if (seconds ==00)
                {
                    seconds = 59 ;
                    minites--;
                }
                else {
                    seconds--;
                }

                mRemainingTime.setText(String.format("%02d",hours)+":"+String.format("%02d",minites)+":"+String.format("%02d",seconds));
                mProgressBar.setProgress((int) ((int) i*100/(x/1000)));
            }

            @Override
            public void onFinish() {
                i++;
                mProgressBar.setProgress(100);
                mRemainingTime.setText("00:00:00");

            }
        };
        mCountdownTimer.start();
    }
}
