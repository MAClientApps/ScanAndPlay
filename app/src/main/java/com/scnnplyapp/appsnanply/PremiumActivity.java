package com.scnnplyapp.appsnanply;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import com.scnnplyapp.appsnanply.utils.UtilitiesClass;

public class PremiumActivity extends AppCompatActivity {

    private WebView subscribeweb;
    LinearLayout ErrorLayout;
    Button trybtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_premium);
        try {
            getSupportActionBar().hide();
        } catch (Exception e) {
            e.printStackTrace();
        }
        setupView();
    }

    @SuppressLint("SetJavaScriptEnabled")
    public void setupView() {
        subscribeweb = findViewById(R.id.subscribewebview);
        ErrorLayout = findViewById(R.id.errorlayout);
        CookieManager.getInstance().setAcceptCookie(true);
        subscribeweb.getSettings().setJavaScriptEnabled(true);
        subscribeweb.getSettings().setUseWideViewPort(true);
        subscribeweb.getSettings().setLoadWithOverviewMode(true);
        subscribeweb.getSettings().setDomStorageEnabled(true);
        subscribeweb.getSettings().setPluginState(WebSettings.PluginState.ON);
        subscribeweb.setWebChromeClient(new WebChromeClient());
        subscribeweb.setVisibility(View.VISIBLE);

        subscribeweb.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @Override
            public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
                super.onReceivedHttpError(view, request, errorResponse);
                startActivity(new Intent(PremiumActivity.this, MainActivity.class));
                finish();
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                String url = request.getUrl().toString();
                if (url.startsWith("http")) {
                    startActivity(new Intent(PremiumActivity.this,
                            MainActivity.class));
                    finish();
                } else {
                    if (!url.startsWith(getString(R.string.app_scheme))) {
                        startActivity(new Intent(PremiumActivity.this,
                                MainActivity.class));
                    }
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                return super.shouldOverrideUrlLoading(view, url);
            }
        });

        callLoadView();
    }

    public void manageInternetCoon() {
        ErrorLayout.setVisibility(View.VISIBLE);
        trybtn = findViewById(R.id.Trybutton);
        trybtn.setOnClickListener(view -> {
            ErrorLayout.setVisibility(View.GONE);
            callLoadView();
        });
    }

    protected void callLoadView() {
        if (UtilitiesClass.isNetworkCheck(this)) {
            subscribeweb.loadUrl(UtilitiesClass.AppLinks(PremiumActivity.this));
        } else {
            manageInternetCoon();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        subscribeweb.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        subscribeweb.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        subscribeweb.loadUrl("about:blank");
    }
    @Override
    public void onBackPressed() {
    }
}
