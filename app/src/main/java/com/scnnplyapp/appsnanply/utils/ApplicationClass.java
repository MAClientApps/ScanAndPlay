package com.scnnplyapp.appsnanply.utils;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.adjust.sdk.Adjust;
import com.adjust.sdk.AdjustAttribution;
import com.adjust.sdk.AdjustConfig;
import com.adjust.sdk.AdjustEvent;
import com.adjust.sdk.OnAttributionChangedListener;
import com.google.firebase.analytics.FirebaseAnalytics;

public class ApplicationClass extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        AdjustConfig config = new AdjustConfig(this, UtilitiesClass.KEY_ADJUST_TOKEN,
                AdjustConfig.ENVIRONMENT_PRODUCTION);
        Adjust.addSessionCallbackParameter("user_uuid", UtilitiesClass.generateUserUUID(getApplicationContext()));

        try {
            FirebaseAnalytics.getInstance(this).getAppInstanceId().addOnCompleteListener(task -> {
                AdjustEvent adjustEvent = new AdjustEvent(UtilitiesClass.KEY_FIREBASE_INSTANCE_ID);
                adjustEvent.addCallbackParameter("eventValue",
                        task.getResult());
                adjustEvent.addCallbackParameter("user_uuid",
                        UtilitiesClass.generateUserUUID(getApplicationContext()));
                Adjust.trackEvent(adjustEvent);
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
        config.setOnAttributionChangedListener(new OnAttributionChangedListener() {
            @Override
            public void onAttributionChanged(AdjustAttribution attribution) {
                UtilitiesClass.setReceivedAttribut(getApplicationContext(),attribution.toString());
            }
        });
        Adjust.onCreate(config);

        registerActivityLifecycleCallbacks(new AdjustLifecycleCallbacks());

    }

    private static final class AdjustLifecycleCallbacks implements ActivityLifecycleCallbacks {
        @Override
        public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {

        }

        @Override
        public void onActivityStarted(@NonNull Activity activity) {

        }

        @Override
        public void onActivityResumed(Activity activity) {
            Adjust.onResume();
        }

        @Override
        public void onActivityPaused(Activity activity) {
            Adjust.onPause();
        }

        @Override
        public void onActivityStopped(@NonNull Activity activity) {

        }

        @Override
        public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

        }

        @Override
        public void onActivityDestroyed(@NonNull Activity activity) {

        }
    }

}
