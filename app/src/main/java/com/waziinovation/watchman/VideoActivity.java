package com.waziinovation.watchman;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.waziinovation.watchman.video.VideoContent;

public class VideoActivity extends AppCompatActivity {
    private TextView videoTitleTextView;
    private TextView videoDescTextView;
    private ImageView videoImageView;
    private VideoContent.VideoItem videoItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String[] strings = getIntent().getStringArrayExtra("videoItem");
        videoItem  = new VideoContent().createVideoItem(strings[0], strings[1], strings[2], strings[3]);

        setTitle(videoItem.getTitle());


        videoTitleTextView = (TextView)findViewById(R.id.watchman_video_title);
        videoDescTextView = (TextView)findViewById(R.id.watchman_video_detail);
        videoImageView = (ImageView)findViewById(R.id.watchman_video_thumbnail);
        videoTitleTextView.setText(videoItem.getTitle());
        videoDescTextView.setText(videoItem.getDescription());
        Picasso.with(this).load(videoItem.getThumbnail()).into(videoImageView);
        videoImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                watchYoutubeVideo(videoItem.getYoutubeID());
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

    public void watchYoutubeVideo(String id){
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
            startActivity(intent);
        } catch (ActivityNotFoundException ex) {
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://www.youtube.com/watch?v=" + id));
            startActivity(intent);
        }
    }

}
