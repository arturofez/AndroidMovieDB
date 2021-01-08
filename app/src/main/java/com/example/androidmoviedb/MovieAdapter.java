package com.example.androidmoviedb;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieAdapter extends ArrayAdapter<Movie> {

    public MovieAdapter(Context context, ArrayList<Movie> movies){
        super(context, 0, movies);
    }

    /*
     * Adapter personalizado para mostrar las películas en un GridView
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Movie movie = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.movie_list_item, parent, false);
        }

        TextView title = (TextView) convertView.findViewById(R.id.itemTitle);
        title.setText(movie.getTitle());

        ImageView poster = (ImageView) convertView.findViewById(R.id.itemPoster);
        if(movie.getPoster_path() != null){
            Picasso.get().load("https://image.tmdb.org/t/p/w200/" + movie.getPoster_path()).into(poster);
        } else {
            Picasso.get().load("https://piotrkowalski.pw/assets/camaleon_cms/image-not-found-4a963b95bf081c3ea02923dceaeb3f8085e1a654fc54840aac61a57a60903fef.png")
                    .resize(200, 300)
                    .centerCrop()
                    .into(poster);
        }

        return convertView;
    }


}
