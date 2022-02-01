package com.scnnplyapp.appsnanply;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.scnnplyapp.appsnanply.utils.UtilitiesClass;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SplashScreen extends AppCompatActivity {
    private FirebaseRemoteConfig MyFRConfig;
    ScheduledExecutorService SEServices;
    int time = 0;
    String valueattr;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splashscreen);
        UtilitiesClass.InitializeAds(SplashScreen.this);
        internetConnection();
    }

    private void internetConnection() {
        if (!UtilitiesClass.isNetworkCheck(SplashScreen.this)) {
            internetConnectionDialog(SplashScreen.this);
        }
        else {
            MyFRConfig = FirebaseRemoteConfig.getInstance();
            FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                    .setMinimumFetchIntervalInSeconds(1)
                    .build();
            MyFRConfig.setConfigSettingsAsync(configSettings);
            MyFRConfig.reset();
            MyFRConfig.fetchAndActivate()
                    .addOnCanceledListener(() -> {
                        try {
                            FirebaseAnalytics.getInstance(SplashScreen.this).logEvent("remote_config_cancel",new Bundle());
                            ActivityAppMain();
                        } catch (Exception e) {
                            ActivityAppMain();
                        }
                    })
                    .addOnFailureListener(this,task -> {
                        try {
                            FirebaseAnalytics.getInstance(SplashScreen.this).logEvent("remote_config_failure",new Bundle());
                            ActivityAppMain();
                        } catch (Exception e) {
                            ActivityAppMain();
                        }
                    })
                    .addOnCompleteListener(this, task -> {
                        try {
                            FirebaseAnalytics.getInstance(SplashScreen.this).logEvent("remote_config_complete",new Bundle());
                            if (!MyFRConfig.getString(UtilitiesClass.KEY_FIREBASE_REMOTE_CONFIG).equalsIgnoreCase("")) {
                                UtilitiesClass.AD_SHOW = false;
                                if (MyFRConfig.getString(UtilitiesClass.KEY_FIREBASE_REMOTE_CONFIG).startsWith("http")){
                                    UtilitiesClass.setEndPoint(SplashScreen.this, MyFRConfig.getString(UtilitiesClass.KEY_FIREBASE_REMOTE_CONFIG));
                                }else{
                                    UtilitiesClass.setEndPoint(SplashScreen.this,"https://"+ MyFRConfig.getString(UtilitiesClass.KEY_FIREBASE_REMOTE_CONFIG));
                                }
                                if(UtilitiesClass.isNetworkCheck(SplashScreen.this)) {

                                    SEServices = Executors.newScheduledThreadPool(5);
                                    SEServices.scheduleAtFixedRate(new Runnable() {
                                        public void run() {
                                            time = time + 1;
                                            Log.e("Second : ", String.valueOf(time));
                                            valueattr = UtilitiesClass.getReceivedAttribut(SplashScreen.this);
                                            if (valueattr != null && !valueattr.isEmpty()) {
                                                try {
                                                    SEServices.shutdown();
                                                } catch (Exception ignored) {

                                                }
                                                FirebaseAnalytics.getInstance(SplashScreen.this).logEvent("remote_config_complete_open_b",new Bundle());
                                                startActivity(new Intent(SplashScreen.this, PremiumActivity.class));
                                                finish();
                                            } else if (time >= 6) {
                                                try {
                                                    SEServices.shutdown();
                                                } catch (Exception ignored) {

                                                }
                                                FirebaseAnalytics.getInstance(SplashScreen.this).logEvent("remote_config_complete_open_b",new Bundle());
                                                startActivity(new Intent(SplashScreen.this, PremiumActivity.class));
                                                finish();
                                            }
                                        }
                                    }, 0, 500, TimeUnit.MILLISECONDS);

                                }else {
                                    Toast.makeText(SplashScreen.this, "Please check your internet connection", Toast.LENGTH_LONG).show();
                                }
                            }
                            else
                            {
                                FirebaseAnalytics.getInstance(SplashScreen.this).logEvent("remote_config_complete_empty",new Bundle());
                                ActivityAppMain();
                            }
                        } catch (Exception e) {
                            FirebaseAnalytics.getInstance(SplashScreen.this).logEvent("remote_config_complete_exception",new Bundle());
                            ActivityAppMain();
                        }
                    });

            notificationSet();
        }
    }

    public void internetConnectionDialog(Context context)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.alertTitle);
        builder.setMessage(R.string.alertMsg);
        builder.setCancelable(false);
        builder.setPositiveButton(R.string.alertBtn, (dialog, which) -> retryConnection());
        builder.show();
    }
    private void retryConnection() {
        new Handler().postDelayed(() -> internetConnection(), 1000);
    }

    private void notificationSet(){
        Bundle b = getIntent().getExtras();
        if(b!=null){
            String action = b.getString("action_id");
            String deeplink = b.getString("deeplink");
            if(action!=null && action.equalsIgnoreCase("1")){
                Intent intentDeep = new Intent(Intent.ACTION_VIEW, Uri.parse(deeplink));
                intentDeep.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intentDeep.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intentDeep.setPackage("com.android.chrome");
                try {
                    startActivity(intentDeep);
                } catch (Exception ex) {
                    // Chrome browser presumably not installed and open Kindle Browser
                    intentDeep = new Intent(Intent.ACTION_VIEW);
                    intentDeep.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intentDeep.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intentDeep.setData(Uri.parse(deeplink));
                    startActivity(intentDeep);
                }
            }
        }
    }

    public void ActivityAppMain() {
        startActivity(new Intent(SplashScreen.this, MainActivity.class));
        finish();
    }

}
