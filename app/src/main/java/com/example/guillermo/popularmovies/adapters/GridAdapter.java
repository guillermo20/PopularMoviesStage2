package com.example.guillermo.popularmovies.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.example.guillermo.popularmovies.R;
import com.example.guillermo.popularmovies.model.MovieItem;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by guillermo on 11/09/16.
 */
public class GridAdapter extends ArrayAdapter<MovieItem> {

    private Context context;

    private final String LOG_TAG = GridAdapter.class.getSimpleName();

    public GridAdapter(Context context, int resource, List<MovieItem> objects) {
        super(context, resource, objects);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = null;
        MovieItem movieItem = getItem(position);
        if (convertView == null) {
            imageView = new ImageView(context);
        } else{
            imageView = (ImageView) convertView;
        }
        //Log.v(LOG_TAG,""+movieItem.getPosterUri(MovieItem.IMAGE_SIZE_W154));
        Picasso.with(context)
                .load(movieItem.getPosterUri(MovieItem.IMAGE_SIZE_W185))
                .resize(400,540).centerInside()
                .error(R.drawable.error)
                .into(imageView);
        return imageView;
    }
}
