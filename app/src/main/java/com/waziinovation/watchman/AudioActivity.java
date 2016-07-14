package com.waziinovation.watchman;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.waziinovation.watchman.audio.AudioContent;
import com.waziinovation.watchman.audio.AudioItem;

import java.io.IOException;

public class AudioActivity extends AppCompatActivity implements Runnable,
        OnSeekBarChangeListener {

    SeekBar seek_bar;
    MediaPlayer player;
    ImageButton play_button, pause_button , stop_button;
    TextView desc, title;
    Handler seekHandler = new Handler();

    AudioItem item;
   String url = "http://brandug.com/sample.mp3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(0);

        String[] strings = getIntent().getStringArrayExtra("audioItem");
        item = new AudioContent().createAudioItem(strings[0],strings[1],strings[2],strings[3]);
        setTitle(item.getTitle());


        seek_bar = (SeekBar)findViewById(R.id.seek);
        play_button = (ImageButton)findViewById(R.id.watchman_player_play);
        pause_button = (ImageButton)findViewById(R.id.watchman_player_pause);
        stop_button = (ImageButton)findViewById(R.id.watchman_player_stop);

        title = (TextView)findViewById(R.id.watchman_audio_title);
        desc = (TextView)findViewById(R.id.watchman_audio_detail);

        title.setText(item.getTitle());
        desc.setText(item.getContent());

        stop_button.setEnabled(false);
        pause_button.setEnabled(false);

        play_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(player == null ){
                    player = new MediaPlayer();
                    player.setAudioStreamType(AudioManager.STREAM_MUSIC);

                    try {
                        player.setDataSource(url);
                    } catch (IllegalArgumentException e) {
                        Toast.makeText(getApplicationContext(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
                    }  catch (IllegalStateException e) {
                        Toast.makeText(getApplicationContext(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    try {
                        player.prepare();
                    } catch (IllegalStateException e) {
                        Toast.makeText(getApplicationContext(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        Toast.makeText(getApplicationContext(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
                    }

                }


                player.start();
                play_button.setEnabled(false);
                stop_button.setEnabled(true);
                pause_button.setEnabled(true);
                seek_bar.setMax(player.getDuration());
                new Thread(AudioActivity.this).start();


            }
        });



        pause_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if(player!=null && player.isPlaying()){
                    player.pause();
                    play_button.setEnabled(true);
                    stop_button.setEnabled(true);
                    pause_button.setEnabled(false);

                }

            }
        });
        stop_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (player != null && player.isPlaying()) {
                    player.stop();
                }
                else if(player != null && player.getDuration() > 0){
                    player.stop();
                }

                player.release();
                player = null;
                seek_bar.setProgress(0);
                play_button.setEnabled(true);
                stop_button.setEnabled(false);
                pause_button.setEnabled(false);

            }
        });





        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                //NavUtils.navigateUpFromSameTask(this);
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override

    public void run() {
        int currentPosition = player.getCurrentPosition();
        int total = player.getDuration();

        while (player != null && currentPosition < total) {
            try {
                Thread.sleep(1000);
                currentPosition = player.getCurrentPosition();
            } catch (InterruptedException e) {
                return;
            } catch (Exception e) {
                return;
            }
            seek_bar.setProgress(currentPosition);
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress,
                                  boolean fromUser) {
        try {
            if (player.isPlaying() || player != null) {
                if (fromUser)
                    player.seekTo(progress);
            } else if (player == null) {
                Toast.makeText(getApplicationContext(), "Media is not running",
                        Toast.LENGTH_SHORT).show();
                seekBar.setProgress(0);
            }
        } catch (Exception e) {
            seekBar.setEnabled(false);
        }
    }
    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        // TODO Auto-generated method stub

    }
}
