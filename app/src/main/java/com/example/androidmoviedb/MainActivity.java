package com.example.androidmoviedb;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private GridView gridView;
    private ImageView imageView;

    DatabaseHelper db;
    ArrayList<Movie> peliculas;
    MovieAdapter movieAdapter;
    TextView input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        gridView = (GridView) findViewById(R.id.gridView);

        db = DatabaseHelper.getSingleton(this);

        peliculas = (ArrayList<Movie>) db.getAll();

        movieAdapter = new MovieAdapter(this, peliculas);

        gridView.setAdapter(movieAdapter);

        input = findViewById(R.id.searchInput);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intento = new Intent(getApplicationContext(), MovieActivity.class);
                Bundle b = new Bundle();
                Movie m = (Movie) parent.getItemAtPosition(position);
                b.putSerializable("movie", m);
                intento.putExtras(b);
                startActivity(intento);
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void onClick(View view) {
        if(view.getId() == R.id.buttonSearch) {
            searchMovie();
        }
    }

    private void searchMovie() {
        movieAdapter.clear();
        ApiHelper api = new ApiHelper();

        try {
            api.search(this, input.getText().toString().trim().toLowerCase());
        } catch (Exception e) {
            Toast.makeText(this, R.string.error, Toast.LENGTH_LONG).show();
        }
    }

    void loadSearch(List<Movie> movieList) {
        if(movieList.isEmpty()){
            Toast.makeText(this, R.string.noResults, Toast.LENGTH_LONG).show();
        } else {
            movieAdapter.addAll(movieList);
        }
    }

}