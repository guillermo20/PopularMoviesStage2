package com.example.guillermo.popularmovies.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;

import com.example.guillermo.popularmovies.R;
import com.example.guillermo.popularmovies.enums.MovieTableProjection;
import com.example.guillermo.popularmovies.model.MovieItem;
import com.squareup.picasso.Picasso;

/**
 * Created by guillermo on 11/09/16.
 */
public class GridAdapter extends CursorAdapter {

    private Context context;

    private final String LOG_TAG = GridAdapter.class.getSimpleName();

    /*public GridAdapter(Context context, int resource, List<MovieItem> objects) {
        super(context, resource, objects);
        this.context = context;
    }*/

    public GridAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        this.context = context;
    }

    /*@Override
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
    }*/

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        //View view = LayoutInflater.from(context).inflate(R.layout.main_grid_fragment, parent, false);
        ImageView imageView = new ImageView(context);
        //Log.i(LOG_TAG,"**** posterpath = "+ MovieItem.getPosterUri(MovieItem.IMAGE_SIZE_W185,cursor.getString(MovieTableProjection.POSTERPATH.getCode())));
        Picasso.with(context)
                .load(MovieItem.getPosterUri(MovieItem.IMAGE_SIZE_W185,cursor.getString(MovieTableProjection.POSTERPATH.getCode())))
                .resize(400,540).centerInside()
                .error(R.drawable.error)
                .into(imageView);
        return imageView;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        /*ImageView imageView = new ImageView(context);
        Log.i(LOG_TAG,"**** posterpath = "+ MovieItem.getPosterUri(MovieItem.IMAGE_SIZE_W185,cursor.getString(MovieTableProjection.POSTERPATH.getCode())));
        /*Picasso.with(context)
                .load(MovieItem.getPosterUri(MovieItem.IMAGE_SIZE_W185,cursor.getString(MovieTableProjection.POSTERPATH.getCode())))
                .resize(400,540).centerInside()
                .error(R.drawable.error)
                .into(imageView);*/
    }
}
