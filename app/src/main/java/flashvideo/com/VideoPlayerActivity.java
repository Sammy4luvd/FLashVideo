package flashvideo.com;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.VideoView;

/**
 * Created by dalafiari on 12/18/16.
 */

public class VideoPlayerActivity extends AppCompatActivity {

    private VideoView videoPlayer;
    private MediaController mediaController;

    private ActionBar actionBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_player);


        Intent intent = this.getIntent();
        Bundle videoResource = intent.getExtras();

        String fileLink = videoResource.getString(VideoListFragment.VIDEO_EXTRA_TAG);
        String title = videoResource.getString(VideoListFragment.VIDEO_TITLE_TAG);

        mediaController = new MediaController(this);

        LinearLayout videoLayout = (LinearLayout) findViewById(R.id.videoLayout);
        videoLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (actionBar.isShowing()) {
                    actionBar.hide();
                    mediaController.hide();
                } else {
                    actionBar.show();
                    mediaController.show();
                }
            }
        });


        Toolbar toolbar = (Toolbar) findViewById(R.id.videoToolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
        actionBar.setTitle(title);

        videoPlayer = (VideoView) findViewById(R.id.videoView);



        videoPlayer.setVideoPath(fileLink);
        //videoPlayer.
        videoPlayer.setMediaController(mediaController);
        videoPlayer.requestFocus();
        videoPlayer.start();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {

            case android.R.id.home:
                videoPlayer.stopPlayback();
                finish();

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        videoPlayer.stopPlayback();
        finish();
    }
}
