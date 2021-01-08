package com.example.androidmoviedb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "AndroidMovieDB";

    private static DatabaseHelper st = null;

    private DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static DatabaseHelper getSingleton(Context context) {
        if(st == null) {
            st = new DatabaseHelper(context.getApplicationContext());
        }
        return st;
    }

    /*
     * Crea la base de datos SQLite
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Movies (id TEXT PRIMARY KEY, title TEXT, overview TEXT, poster TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Movies");
        onCreate(db);
    }

    /*
     * Añade una película a la base de datos
     */
    public boolean add(Movie movie) {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.query("Movies",
                new String[]{"id", "title", "overview", "poster"},
                "Id LIKE ?",
                new String[]{"%"+movie.getId()+"%"},null,null,null,null);

        ContentValues values = new ContentValues();
        values.put("id", movie.getId());
        values.put("title", movie.getTitle());
        values.put("overview", movie.getOverview());
        values.put("poster", movie.getPoster_path());

        boolean updated = false;

        if(!cursor.moveToFirst()) {
            // No en database -> insert
            db.insert("Movies",null, values);
        } else {
            // Ya está en database -> update
            db.update("Movies", values, "id" + "=?", new String[] {movie.getId()});
            updated = true;
        }

        db.close();
        return updated;
    }

    /*
     * Recupera una película de la base de datos por su id
     */
    public Movie getById(String id) {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.query("Movies",
                new String[]{"id", "title","overview", "poster"},
                "Id LIKE ?",
                new String[]{id},null,null,null,null);

        Movie movie = null;
        if(cursor.moveToFirst()){
            movie = new Movie();
            movie.setId(cursor.getString(cursor.getColumnIndex("id")));
            movie.setTitle(cursor.getString(cursor.getColumnIndex("title")));
            movie.setOverview(cursor.getString(cursor.getColumnIndex("overview")));
            movie.setPoster_path(cursor.getString(cursor.getColumnIndex("poster")));
        }
        cursor.close();
        return movie;
    }

    /*
     * Elimina una película de la base de datos si existe
     */
    public boolean delete(Movie movie) {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.query("Movies",
                new String[]{"id", "title","overview", "poster"},
                "Id LIKE ?",
                new String[]{movie.getId()},null,null,null,null);

        boolean deleted = false;

        if(!cursor.moveToFirst()) {
            //Not in database -> nothing
        } else {
            // Already in database -> delete
            db.delete("Movies", "id" + "=?", new String[] {movie.getId()});
            deleted = true;
        }

        db.close();
        return deleted;
    }

    /*
     * Obtiene todas las películas de la base de datos
     */
    public List<Movie> getAll() {
        SQLiteDatabase db = this.getWritableDatabase();

        List<Movie> movieList = new ArrayList<>();

        Cursor cursor = db.query("Movies",null,null,null,null,null,null,null);

        if(cursor.moveToFirst()){
            do{
                Movie movie = new Movie();
                movie.setId(cursor.getString(cursor.getColumnIndex("id")));
                movie.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                movie.setOverview(cursor.getString(cursor.getColumnIndex("overview")));
                movie.setPoster_path(cursor.getString(cursor.getColumnIndex("poster")));
                movieList.add(movie);
            } while(cursor.moveToNext());
        }
        cursor.close();
        return movieList;
    }

}
