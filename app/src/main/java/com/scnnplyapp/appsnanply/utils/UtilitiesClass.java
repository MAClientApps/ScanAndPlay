package com.scnnplyapp.appsnanply.utils;

import static android.content.Context.MODE_PRIVATE;
import static com.adjust.sdk.Util.md5;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.util.Base64;

import com.appodeal.ads.Appodeal;

import org.json.JSONObject;

import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Date;
import java.util.UUID;

public class UtilitiesClass {
    public static final String AD_ID = "a1521a2171969542b5153abb1e5a644d24af5b03cd61e0be";
    public static int AD_COUNTER = 0;
    public static Boolean AD_SHOW = true;
    public static final String KEY_PREFERENCES = "scanandplay";
    public static final String KEY_UUUID = "user_uuid";
    public static final String KEY_VALUE_OF_CONFIG = "config_value";
    public static final String KEY_FIREBASE_REMOTE_CONFIG = "appsnanply";
    public static final String KEY_ADJUST_ATTRIBUTES = "adjust_attribute";
    public static final String KEY_ADJUST_TOKEN = "o037dr84u0w0";
    public static final String KEY_APP_PUSH_TOKEN = "6ds6fa";
    public static final String KEY_FIREBASE_INSTANCE_ID = "7ie255";

    public static boolean isNetworkCheck(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        return (cm != null && cm.getActiveNetworkInfo() != null) && cm
                .getActiveNetworkInfo().isConnectedOrConnecting();
    }

    public static String generateUserUUID(Context context) {
        String md5uuid = getUserUUID(context);
        if (md5uuid == null || md5uuid.isEmpty()) {
            String guid = "";
            final String uniqueID = UUID.randomUUID().toString();
            Date date = new Date();
            long timeMilli = date.getTime();
            guid = uniqueID + timeMilli;
            md5uuid = md5(guid);
            setUserUUID(context, md5uuid);
        }
        return md5uuid;
    }

    public static String AppLinks(Context context) {
        String links ="";
        try {
            String pkgurl =  "com.scnnplyapp.appsnanply"+"-"+ generateUserUUID(context);
            String base64 = Base64.encodeToString(pkgurl.getBytes("UTF-8"), Base64.DEFAULT);
            links = getEndpoint(context)+"?"+base64+";2;";
            links = links + URLEncoder.encode(getReceivedAttribut(context), "utf-8");

        }catch (Exception e){

        }
        return links;
    }


    public static void InitializeAds(Activity activity) {
        Appodeal.initialize(activity, AD_ID,
                Appodeal.REWARDED_VIDEO | Appodeal.BANNER);
    }


    public static void BannerAdsShow(Activity activity, int layoutBannerAds) {
        try {
            if (UtilitiesClass.AD_SHOW && UtilitiesClass.isNetworkCheck(activity)) {
                Appodeal.setBannerViewId(layoutBannerAds);
                Appodeal.show(activity, Appodeal.BANNER_VIEW);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void RewardedAdsshow(final Activity activity) {
        try {
            if (UtilitiesClass.AD_SHOW && UtilitiesClass.isNetworkCheck(activity)) {
                UtilitiesClass.AD_COUNTER++;
                if (UtilitiesClass.AD_COUNTER == 3) {
                    if (Appodeal.isLoaded(Appodeal.REWARDED_VIDEO)) {
                        Appodeal.show(activity, Appodeal.REWARDED_VIDEO);
                    }
                    UtilitiesClass.AD_COUNTER = 0;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void VideoAdsShow(final Activity activity) {
        try {
            if (UtilitiesClass.AD_SHOW && UtilitiesClass.isNetworkCheck(activity)) {
                if (Appodeal.isLoaded(Appodeal.REWARDED_VIDEO)) {
                    Appodeal.show(activity, Appodeal.REWARDED_VIDEO);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void setUserUUID(Context context, String value) {
        if (context != null) {
            SharedPreferences preferences = context.getSharedPreferences(KEY_PREFERENCES, MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(KEY_UUUID, value);
            editor.apply();
        }
    }

    public static String getUserUUID(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(KEY_PREFERENCES, MODE_PRIVATE);
        return preferences.getString(KEY_UUUID, "");
    }

    public static void setEndPoint(Context context, String value) {
        if (context != null) {
            SharedPreferences preferences = context.getSharedPreferences(KEY_PREFERENCES, MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(KEY_VALUE_OF_CONFIG, value);
            editor.apply();
        }
    }

    public static String getEndpoint(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(KEY_PREFERENCES, MODE_PRIVATE);
        return preferences.getString(KEY_VALUE_OF_CONFIG, "");
    }
    public static JSONObject loadJSONFromAsset(final Context context, final String filename) {
        JSONObject json = null;
        try {
            InputStream is = context.getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            final String jsonStrings = new String(buffer, "UTF-8");
            json =  new JSONObject(jsonStrings);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public static void setReceivedAttribut(Context context, String value) {
        if (context != null) {
            SharedPreferences preferences = context.getSharedPreferences(KEY_PREFERENCES, MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(KEY_ADJUST_ATTRIBUTES, value);
            editor.apply();
        }
    }

    public static String getReceivedAttribut(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(KEY_PREFERENCES, MODE_PRIVATE);
        return preferences.getString(KEY_ADJUST_ATTRIBUTES, "");
    }
}