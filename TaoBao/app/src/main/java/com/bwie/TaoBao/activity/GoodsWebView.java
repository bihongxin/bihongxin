package com.bwie.TaoBao.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.bwie.TaoBao.R;

public class GoodsWebView extends AppCompatActivity {

    private WebView wv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_web_view);
        wv = (WebView) findViewById(R.id.wv);
        initWebview();
    }

    private void initWebview() {
        Intent intent=getIntent();
        String url = intent.getStringExtra("url");
        WebSettings settings = wv.getSettings();
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setJavaScriptEnabled(true);

        wv.setWebViewClient(new WebViewClient());
        wv.loadUrl(url);
    }
}
