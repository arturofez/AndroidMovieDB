package com.example.androidmoviedb;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class FavoritesActivity extends AppCompatActivity {

    private GridView gridView;

    private DatabaseHelper db;
    private ArrayList<Movie> peliculas;
    private MovieAdapter movieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        this.setTitle(R.string.favorites);

        // GridView para mostrar las películas
        gridView = (GridView) findViewById(R.id.gridView);

        // Obtenemos la lista de película de la BD
        db = DatabaseHelper.getSingleton(this);
        peliculas = (ArrayList<Movie>) db.getAll();

        if(peliculas.isEmpty()){ // Si no hay películas, alertamos al usuario
            AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
            dlgAlert.setMessage(R.string.noFavs);
            dlgAlert.setPositiveButton(R.string.ok, null);
            dlgAlert.setNegativeButton(R.string.back, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            dlgAlert.create().show();
        } else { // Mostramos las películas en el GridView
            movieAdapter = new MovieAdapter(this, peliculas);
            gridView.setAdapter(movieAdapter);
        }

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
}