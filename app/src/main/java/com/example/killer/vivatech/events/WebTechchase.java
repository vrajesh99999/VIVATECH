package com.example.killer.vivatech.events;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.killer.vivatech.R;

/**
 * Created by Divyesh on 23-01-2016.
 */
public class WebTechchase extends AppCompatActivity {
    WebView myWebView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_events);
        myWebView = (WebView) findViewById(R.id.webView);
        myWebView.loadUrl("http://vrajesh.pixub.com/vivawebsite/techchase-15.html");
        myWebView.setWebViewClient(new Myweb());
        myWebView.getSettings().setBuiltInZoomControls(true);
        myWebView.getSettings().setDisplayZoomControls(false);

    }
    private class Myweb extends WebViewClient{
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return false;
        }
    }

    @Override
    public void onBackPressed() {
        if (myWebView.canGoBack()) {
            myWebView.goBack();
        } else {
            finish();
        }

    }
}