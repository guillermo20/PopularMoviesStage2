package com.example.guillermo.popularmovies.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;

import com.example.guillermo.popularmovies.R;

/**
 * Created by guillermo on 11/18/16.
 */

public class TrailersAdapter extends CursorAdapter {

    private Context context;

    private  String LOG_TAG = TrailersAdapter.class.getSimpleName();

    public TrailersAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        this.context = context;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        //LayoutInflater cursorInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ImageView imageView = (ImageView) LayoutInflater.from(context).inflate(R.layout.trailer_item, parent, false);
        return imageView;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ImageView imageView = (ImageView) view;
        imageView.setVisibility(View.VISIBLE);

    }
}
