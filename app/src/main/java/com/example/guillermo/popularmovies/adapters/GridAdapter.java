package com.example.guillermo.popularmovies.adapters;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;

import com.example.guillermo.popularmovies.R;
import com.example.guillermo.popularmovies.enums.MovieTableProjection;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by guillermo on 11/09/16.
 */
public class GridAdapter extends CursorAdapter {

    private Context context;

    private final String LOG_TAG = GridAdapter.class.getSimpleName();

    public GridAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        this.context = context;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        ImageView imageView = new ImageView(context);
        return imageView;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ImageView imageView = (ImageView) view;
        byte[] result = cursor.getBlob(MovieTableProjection.POSTER_IMAGE.getCode());
        FileOutputStream stream = null;
        String nameFile = cursor.getString(MovieTableProjection.POSTERPATH.getCode()).replace("/","");
        try {
            stream = context.openFileOutput(nameFile,Context.MODE_PRIVATE);
            stream.write(result);
            stream.close();
        }catch (IOException e){
            Log.e(LOG_TAG,e.getMessage());
        }
        File file = context.getFileStreamPath(nameFile);
        Picasso.with(context)
                .load(file)
                .resize(400,540).centerInside()
                .error(R.drawable.error)
                .into(imageView);
    }
}
