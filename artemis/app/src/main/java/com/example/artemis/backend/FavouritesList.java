package com.example.artemis.backend;

import android.content.Context;
import android.content.SharedPreferences;
import com.example.artemis.MainActivity;
import com.google.gson.Gson;
import java.util.ArrayList;

/*
 * Class for list of favourites.
 */
public class FavouritesList {
    private SharedPreferences fav;

    public FavouritesList() {

    }

    /*
     * 	Adds a favourite to the list. To save to sharedPreferences, must convert arraylist to gson:
     */
    public void addToFavourites(String title, String url) {
        Favourite favId = new Favourite(title, url);
        SharedPreferences fav = MainActivity.this.getSharedPreferences();
        //Retrieve ArrayList from preferences:
        Gson gson = new Gson();
        String json = fav.getString("Favourites", "");
        ArrayList<Favourite> list = gson.fromJson(json, ArrayList.class);

        list.add(favId);
        //Add ArrayLit back to preferences:
        Gson gsonAdd = new Gson();
        String jsonAdd = gson.toJson(list);
        SharedPreferences.Editor editor = fav.edit();
        editor.putString("Favourites", json);
        editor.commit();
    }

    /*
     * Removes a favourite from the list.
     */
    public void removeFavourite(Favourite favId) {
        SharedPreferences fav = MainActivity.this.getSharedPreferences();
        //Retrieve ArrayList from preferences:
        Gson gson = new Gson();
        String json = fav.getString("Favourites", "");
        ArrayList<Favourite> list = gson.fromJson(json, ArrayList.class);

        list.remove(favId);
        //Add ArrayLit back to preferences:
        Gson gsonAdd = new Gson();
        String jsonAdd = gson.toJson(list);
        SharedPreferences.Editor editor = fav.edit();
        editor.putString("Favourites", json);
        editor.commit();
    }

}
