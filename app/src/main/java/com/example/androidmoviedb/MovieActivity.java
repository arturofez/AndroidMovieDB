package com.example.androidmoviedb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class MovieActivity extends AppCompatActivity {

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Intent intento = getIntent();
        Bundle b = intento.getExtras();
        Movie m = (Movie) b.getSerializable("movie");

        ApiHelper api = new ApiHelper();

        loadMovie(m);
    }

    void loadMovie(Movie movie) {
        TextView title = (TextView) findViewById(R.id.movieTitle);
        title.setText(movie.getTitle());

        TextView overview = (TextView) findViewById(R.id.movieOverview);
        overview.setText(movie.getOverview());

        imageView = (ImageView) findViewById(R.id.imageView);
        Picasso.get().load("https://image.tmdb.org/t/p/w200/" + movie.getPoster_path()).into(imageView);
    }
}