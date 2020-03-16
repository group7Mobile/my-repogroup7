package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.Toast;

import background.FavDatabaseHelper;
import background.FavDialog;
import background.HPDatabaseHelper;

public class MainActivity extends AppCompatActivity {

    private EditText addressBar;
    private WebView viewer;
    private String tempUrl;
    private String home;
    private FavDialog favDialog;
    FavDatabaseHelper favDatabaseHelper;
    HPDatabaseHelper hpDatabaseHelper;
    static final String savedUrl = "url";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addressBar = findViewById(R.id.addressBar);
        viewer = findViewById(R.id.viewer);
        favDatabaseHelper = new FavDatabaseHelper(this);
        hpDatabaseHelper = new HPDatabaseHelper(this);
        viewer.setWebViewClient(new WebViewClient());
        viewer.getSettings().setUseWideViewPort(true);
        viewer.getSettings().setJavaScriptEnabled(true);
        if (savedInstanceState != null) {
            home = savedInstanceState.getString(savedUrl);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString(savedUrl, home);
        super.onSaveInstanceState(savedInstanceState);
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        home = savedInstanceState.getString(savedUrl);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        SharedPreferences savedHp;
        savedHp = getSharedPreferences("SAVED_URL", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = savedHp.edit();
        editor.putString("MEM-URL", home);
        editor.commit();
    }

    public void openDialog() {
        favDialog = new FavDialog();
        refresh(null);
        favDialog.setUrl(viewer.getUrl());
        viewer.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView v, String url) {
                MainActivity.this.favDialog.setTitleBox(viewer.getTitle());
            }
        });
        favDialog.show(getSupportFragmentManager(), "fav dialog");
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                String tlt;
                if (favDialog.isHp()) {
                    try {
                        hpDatabaseHelper.rePopulate(viewer.getUrl());
                        int repd = hpDatabaseHelper.rePopulate(viewer.getUrl());
                    } catch (Exception e) {
                        hpDatabaseHelper.addData(1, viewer.getUrl());
                        hpDatabaseHelper.addData(1, viewer.getUrl());
                    }
                }
                tlt = favDialog.getTitle();
                boolean insert = favDatabaseHelper.addData(tlt, viewer.getUrl());

                if (insert) {
                    Toast.makeText(getApplicationContext(), "Successfull", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "unSuccessfull", Toast.LENGTH_SHORT).show();
                }
                Handler handler1 = new Handler();
                handler1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        home = hpDatabaseHelper.getHomePage().toString();
                       // Toast.makeText(getApplicationContext(), "hpr", Toast.LENGTH_LONG).show();
                    }
                },200);
            }
        }, 10000);
    }

    public void settings(View v) {
        Intent goSettings = new Intent(this, Settings.class);
        startActivity(goSettings);
    }

    public void go(View v) {
        filterUrl(addressBar.getText().toString());
        viewer.loadUrl(tempUrl);
    }

    public void refresh(View v) {
        viewer.loadUrl(viewer.getUrl());
    }

    public void gobackPage(View v) {
        if (viewer.canGoBack()) {
            viewer.goBack();
        }
    }

    public void favorite(View v) {
        openDialog();
    }

    public void homeP(View v) {
        if (hpDatabaseHelper.getHomePage().toString().equals("")) {
            try {
                SharedPreferences getSavedHP;
                getSavedHP = getSharedPreferences("SAVED_URL", Context.MODE_PRIVATE);
                home = getSavedHP.getString("MEM-URL", "");
            } catch (Exception e) {
            }
        }
        home = filterUrl(home);
        viewer.loadUrl(home);
    }

    public String filterUrl(String link) {
        int count = 0;
        tempUrl = link;
        tempUrl = addressBar.getText().toString();
        if (tempUrl.toLowerCase().startsWith("http://")) {
            tempUrl.substring(7);
        }

        if (tempUrl.toLowerCase().startsWith("https://")) {
            tempUrl.substring(9);
        }

        for (int i = 0; i < tempUrl.length(); i++) {
            if (String.valueOf(tempUrl.charAt(i)).equals(".")) {
                count++;
            }
            if (String.valueOf(tempUrl.charAt(i)).equals(" ")) {
                count = 0;
                break;
            }
        }

        if (count == 0) {
            tempUrl = "www.google.com/#q=" + tempUrl;
        } else {
            if (!tempUrl.toLowerCase().startsWith("ww")) {
                tempUrl = "www." + tempUrl;
            }
        }
        tempUrl = "http://" + tempUrl;
        return tempUrl;
    }

    public void del(View v) {
        hpDatabaseHelper.del();
    }
}
