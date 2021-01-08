package com.example.androidmoviedb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class SearchActivity extends AppCompatActivity {

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Intent intento = getIntent();
        Bundle b = intento.getExtras();

        ApiHelper api = new ApiHelper();

        try {
            api.getMovieById(this,"408");
        } catch (Exception e) {

        }
        /*Toast toast1 =
                    Toast.makeText(getApplicationContext(),
                            "No se ha encontrado la película", Toast.LENGTH_SHORT);

            toast1.show();*/
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