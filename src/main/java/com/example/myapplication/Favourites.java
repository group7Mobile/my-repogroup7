package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import background.FavDatabaseHelper;
import background.FavList;

public class Favourites extends AppCompatActivity {

    private static final String TAG = "ListDataActivity";

    FavDatabaseHelper favDatabaseHelper;

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);
        listView = (ListView) findViewById(R.id.listView);
        favDatabaseHelper = new FavDatabaseHelper(this);

        populateList();
    }

    private void populateList() {
        Log.d(TAG, "populateListView: Display");
        favDatabaseHelper = new FavDatabaseHelper(this);
        Cursor data = favDatabaseHelper.getData();
        ArrayList<String> listData = new ArrayList<>();
        while(data.moveToNext()) {
            listData.add((data.getString(1)));
        }

        ListAdapter adapter;
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        listView.setAdapter(adapter);
    }
}
