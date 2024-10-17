package com.techvipin130524.stopwatchapp;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private int seconds = 0;
    private boolean running;
    private boolean wasRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        if (savedInstanceState != null) {

            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
            wasRunning = savedInstanceState.getBoolean("wasRunning");
        }

        runTimer();
    }

    public void onClickStart(View view) {
        running = true;
    }

    public void onClickStop(View view) {
        running = false;
    }

    public void onClickReset(View view) {
        running = false;
        seconds = 0;
    }

    public void runTimer() {

        final TextView timerView= findViewById(R.id.timeView);

        final Handler handler= new Handler();

        handler.post(new Runnable() {
            @Override
            public void run() {

                //code goes  to a runnable  object
                int minutes=seconds/60;
                int hours=seconds/3600;
                int secs=seconds%60;

                String time= String.format("%d:%02d:%02d",hours,minutes,secs);

                timerView.setText(time);

                if (running){seconds++;}

                handler.postDelayed(this,1);
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("seconds", seconds);
        savedInstanceState.putBoolean("running", running);
        savedInstanceState.putBoolean("wasRunning", wasRunning);
    }

    @Override
    public void onStop() {
        super.onStop();

        wasRunning = running;
        running = false;
    }

    public void onStart() {
        super.onStart();

        if (wasRunning) {
            running = true;
        }
    }

    public void onPause() {
        super.onPause();

        wasRunning = running;
        running = false;
    }

    public void onResume() {
        super.onResume();

        if (wasRunning) {
            running = true;
        }
    }

}