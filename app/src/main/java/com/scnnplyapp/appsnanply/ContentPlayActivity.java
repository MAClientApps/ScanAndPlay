package com.scnnplyapp.appsnanply;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.scnnplyapp.appsnanply.utils.UtilitiesClass;


public class ContentPlayActivity extends AppCompatActivity {
    private WebView contentplayWeb;
    private String link=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_contentplay);

        try {
            getSupportActionBar().hide();
        }catch (Exception e){

        }
        UtilitiesClass.BannerAdsShow(ContentPlayActivity.this, R.id.Ad_banner);

        readExtra();
        initVGPlusWebView();
    }

    private void readExtra() {
        if(getIntent().hasExtra("link") && getIntent().getExtras().getString("link")!=null){
            link=getIntent().getExtras().getString("link");
        }

    }

    public void initVGPlusWebView() {
        contentplayWeb = (WebView)findViewById(R.id.contentWebview);
        CookieManager.getInstance().setAcceptCookie(true);
        contentplayWeb.getSettings().setJavaScriptEnabled(true);
        contentplayWeb.getSettings().setUseWideViewPort(true);
        contentplayWeb.getSettings().setLoadWithOverviewMode(true);
        contentplayWeb.getSettings().setDomStorageEnabled(true);
        contentplayWeb.getSettings().setPluginState(WebSettings.PluginState.ON);
        contentplayWeb.setWebChromeClient(new WebChromeClient());
        contentplayWeb.setVisibility(View.VISIBLE);
        contentplayWeb.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {

                super.onPageFinished(view, url);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                return super.shouldOverrideUrlLoading(view, url);
            }
        });

        contentplayWeb.loadUrl(link);

    }

    @Override
    public void onResume() {
        super.onResume();
        contentplayWeb.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        contentplayWeb.onPause();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (contentplayWeb.canGoBack()) {
                        contentplayWeb.goBack();
                    } else {
                        finish();
                    }
                    return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        contentplayWeb.loadUrl("about:blank");
    }

}