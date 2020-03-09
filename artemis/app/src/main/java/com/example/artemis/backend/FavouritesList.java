package com.example.artemis.backend;

import java.util.ArrayList;

public class FavouritesList {
    private ArrayList<Favourite> favourites;

    public FavouritesList() {
        ArrayList<Favourite> favourites = new ArrayList<Favourite>();
    }

    public String addFavourite(String title, String url) {
        Favourite fav = new Favourite(title, url);
        favourites.add(fav);
        return ("Successfully added " + title + " to Favourites!");
    }

    public String removeFavourite(Favourite fav) {
        favourites.remove(fav);
        return ("Successfully removed " + fav.getTitle() + " from Favourites!");
    }

}
