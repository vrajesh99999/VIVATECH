package com.example.killer.vivatech;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class Staffsendpush extends AppCompatActivity {
    WebView mWebview;
    String url="https://pushbots.com/auth/login";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);
        mWebview = (WebView) findViewById(R.id.webView);

        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        toolbar.setTitle("Courses");
        // toolbar.setLogo(R.drawable.cources);
        final Activity activity = this;
        final ProgressDialog pd = ProgressDialog.show(Staffsendpush.this, "", "Please wait, loading...", true);



        // enable javascript

        mWebview.getSettings().setLoadWithOverviewMode(true);
        mWebview.getSettings().setUseWideViewPort(true);
        mWebview.getSettings().setSupportMultipleWindows(true);


        mWebview.setWebViewClient(new WebViewClient() {
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(activity, description, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                pd.show();
            }


            @Override
            public void onPageFinished(WebView view, String url) {
                pd.dismiss();


            }

        });
        mWebview .loadUrl(url);
    }
    @Override
    public void onBackPressed(){
        if(mWebview.canGoBack() == true){
            mWebview.goBack();
        }else{
            finish();
        }

    }

}


