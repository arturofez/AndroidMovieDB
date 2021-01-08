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

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Movies (id INT PRIMARY KEY, title TEXT, overview TEXT, poster TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Movies");
        onCreate(db);
    }

    public boolean add(Movie movie) {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.query("Movie",
                new String[]{"Id", "Title", "Overview", "Poster"},
                "Id LIKE ?",
                new String[]{"%"+movie.getId()+"%"},null,null,null,null);

        ContentValues values = new ContentValues();
        values.put("Id", movie.getId());
        values.put("Title", movie.getTitle());
        values.put("Overview", movie.getOverview());
        values.put("Poster", movie.getPoster_path());

        boolean updated = false;

        if(!cursor.moveToFirst()) {
            //Not in database -> insert
            db.insert("Movies",null, values);

        } else {
            // Already in database -> update
            db.update("Movies", values, "Id" + "=?", new String[] {movie.getId()});
            updated = true;
        }

        db.close();
        return updated;
    }

    public Movie getById(String id) {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.query("Movies",
                new String[]{"Id", "Title","Overview", "Poster"},
                "Id LIKE ?",
                new String[]{id},null,null,null,null);

        Movie movie = new Movie();
        if(cursor.moveToFirst()){
            movie.setId(cursor.getString(cursor.getColumnIndex("Id")));
            movie.setTitle(cursor.getString(cursor.getColumnIndex("Title")));
            movie.setOverview(cursor.getString(cursor.getColumnIndex("Overview")));
            movie.setPoster_path(cursor.getString(cursor.getColumnIndex("Poster")));
        }
        cursor.close();
        return movie;
    }

    public List<Movie> getAll() {
        SQLiteDatabase db = this.getWritableDatabase();

        List<Movie> movieList = new ArrayList<>();

        Cursor cursor = db.query("Movies",
                new String[]{"Id", "Title","Overview", "Poster"},
               null,null,null,null,null,null);

        if(cursor.moveToFirst()){
            do{
                Movie movie = new Movie();
                movie.setId(cursor.getString(cursor.getColumnIndex("Id")));
                movie.setTitle(cursor.getString(cursor.getColumnIndex("Title")));
                movie.setOverview(cursor.getString(cursor.getColumnIndex("Overview")));
                movie.setPoster_path(cursor.getString(cursor.getColumnIndex("Poster")));
                movieList.add(movie);
            } while(cursor.moveToNext());
        }
        cursor.close();
        return movieList;
    }

}
