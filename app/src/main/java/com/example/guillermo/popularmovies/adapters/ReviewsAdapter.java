package com.example.guillermo.popularmovies.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.guillermo.popularmovies.R;
import com.example.guillermo.popularmovies.enums.ReviewTableProjections;

/**
 * Created by guillermo on 11/18/16.
 */

public class ReviewsAdapter extends CursorAdapter{

    public ReviewsAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.review_item,parent,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView userTextview = (TextView) view.findViewById(R.id.review_user);
        TextView reviewTextview = (TextView) view.findViewById(R.id.review_content);
        userTextview.setText(cursor.getString(ReviewTableProjections.AUTHOR));
        reviewTextview.setText(cursor.getString(ReviewTableProjections.CONTENT));
    }
}
