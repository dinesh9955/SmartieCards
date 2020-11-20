package com.smartiecards.settings;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.smartiecards.R;
import com.smartiecards.storage.SavePref;
import com.smartiecards.util.Utility;

import java.util.ArrayList;

/**
 * Created by AnaadIT on 1/31/2018.
 */

public class WebFragment extends Fragment {
    ProgressBar progressBar;
    WebView webView;

    private static final String TAG = "WebActivity";
    String url = "";


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.web_fragment, container, false);

        webView = (WebView) view.findViewById( R.id.webView1 );
        progressBar = (ProgressBar) view.findViewById( R.id.progressBar1444);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build();
        StrictMode.setThreadPolicy(policy);


        Bundle bundle = getArguments();
        url = bundle.getString("url");

        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);

//        webView.setBackgroundColor(0x00000000);
//        webView.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null);

        webView.getSettings().setTextSize(WebSettings.TextSize.LARGEST);


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



        return view;
    }







}
