package com.example.androidmoviedb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.squareup.picasso.Picasso;

public class MovieActivity extends AppCompatActivity {

    private ImageView imageView;
    private DatabaseHelper db;
    private Movie m;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        this.setTitle(R.string.movie);

        // Recuperamos la película
        Intent intento = getIntent();
        Bundle b = intento.getExtras();
        m = (Movie) b.getSerializable("movie");

        db = DatabaseHelper.getSingleton(this);

        // Se muestra la película
        loadMovie(m);

        // Botón para marcar película como favorita
        Switch sw = (Switch) findViewById(R.id.favoriteSwitch);

        if (db.getById(m.getId()) != null){ // Si está en la BD, ya es película favorita
            sw.setChecked(true);
        }

        // Añade o elimina una película como favorita
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(m != null) {
                    if (isChecked) {
                        db.add(m);
                    } else {
                        db.delete(m);
                    }
                }
            }
        });
    }

    /*
     * Función que muestra los datos de la película
     */
    void loadMovie(Movie movie) {
        TextView title = (TextView) findViewById(R.id.movieTitle);
        title.setText(movie.getTitle());

        TextView overview = (TextView) findViewById(R.id.movieOverview);
        overview.setText(movie.getOverview());

        imageView = (ImageView) findViewById(R.id.imageView);
        if(movie.getPoster_path() != null){
            Picasso.get().load("https://image.tmdb.org/t/p/w200/" + movie.getPoster_path()).into(imageView);
        } else {
            Picasso.get().load(R.drawable.notfound).into(imageView);
        }
    }
}