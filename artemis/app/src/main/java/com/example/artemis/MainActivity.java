package com.example.artemis;

import android.content.Context;
import android.hardware.input.InputManager;
import android.inputmethodservice.Keyboard;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.URLUtil;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;

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
    private TextView clearButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addressBar = findViewById(R.id.addressBar);
        refresh = findViewById(R.id.refresh);
        go = findViewById(R.id.go);
        cancel = findViewById(R.id.cancel);
        viewer = findViewById(R.id.viewer);
        clearButton = findViewById(R.id.clearBarText);
        WebSettings vSettings = viewer.getSettings();
        viewer.setWebViewClient(new WebViewClient());
        viewer.getSettings().setLoadWithOverviewMode(true);
        viewer.getSettings().setUseWideViewPort(true);
        clearButton.animate().alpha(0.0f).setDuration(0);
        // block pop-ups
        vSettings.setSupportMultipleWindows(false);
        // Allows javascript (vulnerable to XSS, will be fixed)
        vSettings.setJavaScriptEnabled(true);
    }

    public void go(View v) {
        filterUrl();
        url = tempUrl;
        viewer.loadUrl(url);
        InputMethodManager inp = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inp.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
        makeBarInvisible();
    }

    public void goBackPage(View v) {
        if (viewer.canGoBack()) {
            viewer.goBack();
        }
        makeBarInvisible();
    }

    public void refresh(View v) {
        url = viewer.getUrl();
        viewer.loadUrl(url);
        makeBarInvisible();
    }

    public void cancel(View v) {
        viewer.stopLoading();
        makeBarInvisible();
    }

    public void favorites(View v) {

    }

    public void makeBarVisible(View view) {
        clearButton.animate().alpha(1.0f).setDuration(0);
    }

    public void makeBarInvisible() {
        clearButton.animate().alpha(0.0f).setDuration(0);
    }

    public void makeBarInvisibleView(View v) {
        clearButton.animate().alpha(0.0f).setDuration(0);
    }

    public void clearBar(View v) {
        addressBar.getText().clear();
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