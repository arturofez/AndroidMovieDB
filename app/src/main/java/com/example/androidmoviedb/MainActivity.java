package com.example.androidmoviedb;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import android.widget.GridView;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private GridView gridView;
    private MovieAdapter movieAdapter;
    private TextView searchInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Formulario de búsqueda
        searchInput = findViewById(R.id.searchInput);

        // GridView para mostrar las películas
        gridView = (GridView) findViewById(R.id.gridView);
        // Adapter personalizado para mostrar las películas en el GridView
        movieAdapter = new MovieAdapter(this, new ArrayList<Movie>());
        gridView.setAdapter(movieAdapter);

        // Listener para cada item del GridView
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
    }

    public void onClick(View view) {
        if(view.getId() == R.id.buttonSearch) { // Botón buscar
            searchMovie();
        } else if(view.getId() == R.id.favoritesButton){ // Botón favoritos
            Intent intento = new Intent(getApplicationContext(), FavoritesActivity.class);
            startActivity(intento);
        }
    }

    /*
     * Hace una petición de búsqueda a la API asíncronamente
     */
    private void searchMovie() {
        movieAdapter.clear();
        ApiHelper api = new ApiHelper();
        try {
            api.search(this, searchInput.getText().toString().trim().toLowerCase());
        } catch (Exception e) {
            Toast.makeText(this, R.string.error, Toast.LENGTH_LONG).show();
        }
    }

    /*
     * Carga los resultados de la búsqueda a la API
     */
    void loadSearch(List<Movie> movieList) {
        if(movieList.isEmpty()){
            Toast.makeText(this, R.string.noResults, Toast.LENGTH_LONG).show(); //Si no hay resultados, se informa
        } else {
            movieAdapter.addAll(movieList);
        }
    }

}