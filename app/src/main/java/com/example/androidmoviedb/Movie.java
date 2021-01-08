package com.example.androidmoviedb;

import java.io.Serializable;

public class Movie implements Serializable {

    private String id;
    private String title;
    private String poster_path;
    private String overview;

    public Movie(){}

    /*
     * Crea un objeto película con su título, descripción, id en themoviedb.com y url del poster
     */
    public Movie(String id, String title, String poster_path, String overview) {
        this.id = id;
        this.title = title;
        this.poster_path = poster_path;
        this.overview = overview;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getOverview() { return overview; }

    public void setOverview(String overview) { this.overview = overview; }
}
