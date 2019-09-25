package test.practical.com.androidprojectsstructuredemo.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import test.practical.com.androidprojectsstructuredemo.R;

public class SplashActivity extends AppCompatActivity {

    @BindView(R.id.videoview)
    VideoView videoview;

    int SPLASH_DISPLAY_LENGTH = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);


        String uriPath = "android.resource://"+ getPackageName() + "/" + R.raw.splash_video;
        Uri uri = Uri.parse(uriPath);
        videoview.setVideoURI(uri);
        videoview.requestFocus();
        videoview.start();
//  videoview.setVideoPath("android.resource://com.myapplication/" + R.drawable.vidio);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent = new Intent(SplashActivity.this, DashboardActivity.class);
                startActivity(mainIntent);
                finish();
            }
        }, SPLASH_DISPLAY_LENGTH);

    }
}
