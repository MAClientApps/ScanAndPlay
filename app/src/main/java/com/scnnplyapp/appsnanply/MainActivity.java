package com.scnnplyapp.appsnanply;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.appodeal.ads.Appodeal;
import com.appodeal.ads.RewardedVideoCallbacks;
import com.scnnplyapp.appsnanply.utils.UtilitiesClass;

public class MainActivity extends AppCompatActivity implements RewardedVideoCallbacks {

    Button videobutton,gamebutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        videobutton=findViewById(R.id.btnvideo);
        videobutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(MainActivity.this,VideoDataActivity.class);
                startActivity(it);
            }
        });

        gamebutton=findViewById(R.id.btnGame);
        gamebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(MainActivity.this,GameDataActivity.class);
                startActivity(it);
            }
        });
        if (UtilitiesClass.isNetworkCheck(this)) {
            UtilitiesClass.InitializeAds(MainActivity.this);
            UtilitiesClass.BannerAdsShow(MainActivity.this, R.id.Ad_banner);
            Appodeal.setRewardedVideoCallbacks(this);
        }

        if (isInternetConnection()) {
        } else {
            Toast.makeText(this, getResources().getString(R.string.error_internet), Toast.LENGTH_SHORT).show();
        }
        if (ContextCompat.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE") != 0) {
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, 1);
        }

    }
    private boolean isInternetConnection() {
        return ((ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE)).getActiveNetworkInfo() != null;
    }

    @Override
    public void onRewardedVideoLoaded(boolean isPrecache) {
        UtilitiesClass.VideoAdsShow(MainActivity.this);
    }

    @Override
    public void onRewardedVideoFailedToLoad() {

    }

    @Override
    public void onRewardedVideoShown() {

    }

    @Override
    public void onRewardedVideoShowFailed() {

    }

    @Override
    public void onRewardedVideoFinished(double amount, String name) {

    }

    @Override
    public void onRewardedVideoClosed(boolean finished) {

    }

    @Override
    public void onRewardedVideoExpired() {

    }

    @Override
    public void onRewardedVideoClicked() {

    }
}