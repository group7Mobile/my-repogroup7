package com.example.artemis;

import android.inputmethodservice.Keyboard;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.URLUtil;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private EditText addressBar;
    private ImageButton refresh;
    private ImageButton go;
    private ImageButton cancel;
    private WebView viewer;
    private String tempUrl;
    private String url;
    private String protocol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addressBar = findViewById(R.id.addressBar);
        refresh = findViewById(R.id.refresh);
        go = findViewById(R.id.go);
        cancel = findViewById(R.id.cancel);
        viewer = findViewById(R.id.viewer);
        WebSettings vSettings = viewer.getSettings();
        viewer.setWebViewClient(new WebViewClient());
        // block pop-ups
        vSettings.setSupportMultipleWindows(false);
        // Allows javascript (vulnerable to XSS, will be fixed)
        vSettings.setJavaScriptEnabled(true);
    }

    public void go(View v) {
        filterUrl();
        url = tempUrl;
        viewer.loadUrl(url);
        Keyboard.hide()
    }

    public void goBackPage(View v) {
        if (viewer.canGoBack()) {
            viewer.goBack();
        }
    }

    public void refresh(View v) {
        url = viewer.getUrl();
        viewer.loadUrl(url);
    }

    public void cancel(View v) {
        viewer.stopLoading();
    }

    public void favorites(View v) {

    }

    public int countUrl() {
        int count = 0;
        for (int i = 0; i < tempUrl.length(); i++) {
            Character ch = tempUrl.charAt(i);
            if ( ch.toString().equals(".")) {
                count++;
            }
            if (ch.toString().equals(" ")) {
                count = 0;
                break;
            }
        }
        return count;
    }

    public void filterUrl() {
        tempUrl = addressBar.getText().toString();
        if (countUrl() < 1) {
            tempUrl = "https://www.google.com/search?q=" + tempUrl;
        } else {
            if (tempUrl.toLowerCase().startsWith("http://")) {
                tempUrl = tempUrl.substring(7);
            }
            if (tempUrl.toLowerCase().startsWith("https://")) {
                tempUrl = tempUrl.substring(8);
            }
            if (!(tempUrl.toLowerCase().startsWith("http://")) &&
                    !(tempUrl.toLowerCase().startsWith("https://")) &&
                    !(tempUrl.toLowerCase().startsWith("www")
                    )) {
                tempUrl = "www." + tempUrl;
            }
            tempUrl = "http://" + tempUrl;
        }
    }
}
