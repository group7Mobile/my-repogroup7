package com.example.artemis.backend;

import java.util.ArrayList;

/*
 * Class for list of favourites.
 */
public class FavouritesList {
    private ArrayList<Favourite> favourites;

    public FavouritesList() {
        ArrayList<Favourite> favourites = new ArrayList<Favourite>();
    }

    /*
     * Adds a favourite to the list.
     */
    public void addFavourite(String title, String url) {
        Favourite fav = new Favourite(title, url);
        favourites.add(fav);
    }

    /*
     * Removes a favourite from the list.
     */
    public void removeFavourite(Favourite fav) {
        favourites.remove(fav);
    }

}
