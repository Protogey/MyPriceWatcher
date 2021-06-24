package edu.utep.cs.cs4330.mypricewatcher;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

//Class that updates the webview to display the string inputted by the user
public class WebViewActivity extends AppCompatActivity {
    private WebView wv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_page);

        Intent intent = getIntent();
        String URL = intent.getStringExtra("url");
        Toast.makeText(getApplicationContext(), URL, Toast.LENGTH_SHORT).show();
        wv = (WebView)findViewById(R.id.webView);
        wv.setWebViewClient(new WebViewClient());
        WebSettings webSettings = wv.getSettings();
        webSettings.setJavaScriptEnabled(true);
        wv.loadUrl(URL);
    }
}