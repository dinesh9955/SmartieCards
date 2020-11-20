package com.smartiecards.settings;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.smartiecards.BaseAppCompactActivity;
import com.smartiecards.R;

/**
 * Created by AnaadIT on 9/27/2017.
 */

public class WebActivity extends BaseAppCompactActivity {

    ProgressBar progressBar;
    WebView webView;

    private static final String TAG = "WebActivity";
    String url = "";


    @Override
    protected int getLayoutResource() {
        return R.layout.web_activity;
    }




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

     //   getWindow().setBackgroundDrawableResource(R.drawable.bg);

      //  setContentView(R.layout.web_activity);

        webView = (WebView) findViewById( R.id.webView1 );
        progressBar = (ProgressBar) findViewById( R.id.progressBar1444);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build();
        StrictMode.setThreadPolicy(policy);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView textViewTitleBar = (TextView) findViewById(R.id.textView_title);



        Bundle bundle = getIntent().getExtras();
        url = bundle.getString("url");
        String key = bundle.getString("key");


        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);

        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);

//        webView.setBackgroundColor(0x00000000);
//        webView.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null);

        webView.getSettings().setTextSize(WebSettings.TextSize.LARGEST);
            textViewTitleBar.setText(""+key);


//        if(key.equals("terms")){
//            webView.getSettings().setTextSize(WebSettings.TextSize.LARGEST);
//            textViewTitleBar.setText("");
//        }else if(key.equals("privacy")){
//            webView.getSettings().setTextSize(WebSettings.TextSize.LARGEST);
//            textViewTitleBar.setText("");
//        }else{
//
//        }

//        new HelpTask().execute(""+url);


        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return super.shouldOverrideUrlLoading(view, request);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                progressBar.setVisibility(View.INVISIBLE);
                webView.loadUrl("javascript:document.body.style.padding= \"2%\"; void 0");
//                webView.loadUrl(
//                        "javascript:document.body.style.setProperty(\"color\", \"white\");"
//                );
//                webview.loadUrl("javascript:document.body.style.color=\"white\";");
            }
        });


        webView.getSettings().setJavaScriptEnabled(true);
//        webView.loadUrl("javascript:document.body.style.backgroundColor ='red';");
//        webView.loadUrl("javascript:document.body.style.fontSize ='20pt'");
//        webView.loadUrl("javascript:document.body.style.color ='yellow';");

        webView.loadUrl(String.valueOf(Html.fromHtml(url)));

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        webView.goBack();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
