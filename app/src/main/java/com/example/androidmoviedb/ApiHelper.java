package com.example.androidmoviedb;

import android.app.Activity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ApiHelper {
    private final String API_KEY = "af49cda3d2d7a1dffa14998136e9898e";
    OkHttpClient client;

    public ApiHelper(){
        client = new OkHttpClient();
    }

    public void search(MainActivity a, String movie) throws IOException {
        String url = "https://api.themoviedb.org/3/search/movie?api_key=" + API_KEY
                + "&language=" + Locale.getDefault().toLanguageTag()
                + "&query=" + movie
                + "&page=1&include_adult=false";
        Request request = new Request.Builder()
                .url(url)
                .build();

        //Response response = client.newCall(request).execute();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson = new Gson();
                SearchResult sr = gson.fromJson(response.body().string(), SearchResult.class);
                List<Movie> movieList = sr.getResult();
                a.runOnUiThread(() -> a.loadSearch(sr.getResult()));
            }
        });
    }

}
