package com.example.looperdraft;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    private MediaRecorder recorder;
    private MediaPlayer player;
    private int i;
    private ArrayList<String> audioFiles = new ArrayList<>();

    private Button recordBtn;
    private Button stopBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        i=1;

        //OUTPUT_FILE = Environment.getExternalStorageDirectory() + "/audiorecorder.3gpp";

        recordBtn = (Button) findViewById(R.id.recordBtn);
        stopBtn = (Button) findViewById(R.id.stopBtn);

        recordBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                buttonClicked(view);
            }
        });
        stopBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                buttonClicked(view);
            }
        });
    }

    public void buttonClicked(View view)
    {
        switch(view.getId())
        {
            case R.id.recordBtn:
                try
                {
                    beginRecording();
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
                break;
            case R.id.stopBtn:
                try
                {
                    stopRecording();
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
                break;
        }
    }

    private void ditchMediaRecorder()
    {
        if(recorder != null)
            recorder.release();
    }

    /*private void stopPlayback()
    {
        if(player != null)
        {
            player.stop();
        }
    }*/

    private void playRecording() throws Exception
    {
        ditchMediaPlayer();

        player = new MediaPlayer();

        //test if this works simultaneously or sequentially
        for(String fileName : audioFiles)
        {
            player.setDataSource(fileName);
        }
        player.prepare();
        player.start();
    }

    private void ditchMediaPlayer()
    {
        if(player !=  null)
        {
            try
            {
                player.release();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    private void stopRecording()
    {
        if(recorder != null)
            recorder.stop();
        if(player != null)
            player.stop();
    }

    private void beginRecording() throws Exception
    {
        ditchMediaRecorder();

        File outFile = new File(Environment.getExternalStorageDirectory() + "/audiorecorder1.3gpp");

        if(audioFiles.contains(outFile.toString()))
        {
            //need this to play every recorded track simultaneously
            playRecording();
        }

        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_WB);
        recorder.setOutputFile(Environment.getExternalStorageDirectory() + "/audiorecorder" + i + ".3gpp");

        //Figure out how to get a list of audiofiles and be able to choose which one you play on screen
        audioFiles.add(Environment.getExternalStorageDirectory() + "/audiorecorder" + i + ".3gpp");

        // increment file name
        i++;
        recorder.prepare();
        recorder.start();
    }
}
