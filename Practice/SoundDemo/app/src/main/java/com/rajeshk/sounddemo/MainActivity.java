package com.rajeshk.sounddemo;

import android.content.Context;
import android.media.AudioManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.MediaController;
import android.view.*;
import android.widget.SeekBar;

import java.io.IOException;
import java.net.URI;
import java.security.PublicKey;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity {
    //MediaController mediacontroller ;
    MediaPlayer mplayer;
    AudioManager audioManager;

    public void playAudio(View view) {
        mplayer.start();
    }

    public void pauseAudio(View view) {
        mplayer.pause();
        //mplayer.setDataSource(this, URI);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mplayer = MediaPlayer.create(this, R.raw.dilgit);
        //mediacontroller = new MediaController(this);

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        int maxVolumne = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int currVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        SeekBar volumeControl = (SeekBar) findViewById(R.id.seekBar);
        volumeControl.setMax(maxVolumne);
        volumeControl.setProgress(currVolume);

        volumeControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.i("Seekbar Value", Integer.toString(progress));
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        final SeekBar audioControl = (SeekBar) findViewById(R.id.audioSeekBar);
        audioControl.setMax(mplayer.getDuration());
        audioControl.setProgress(mplayer.getCurrentPosition());
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                audioControl.setProgress(mplayer.getCurrentPosition());
            }
        }, 0, 1000);
        audioControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mplayer.seekTo(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                mplayer.pause();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mplayer.start();
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //mediacontroller.show();
        return false;
    }
}
