package com.example.guillermo.popularmovies.loaders;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;

/**
 * Created by guillermo on 11/15/16.
 */

public class ReviewsLoader implements LoaderManager.LoaderCallbacks<Cursor> {

    private String LOG_TAG = ReviewsLoader.class.getSimpleName();

    private Uri mUriReviews;

    private Context context;

    public ReviewsLoader(Uri mUriReviews,Context context) {
        this.mUriReviews = mUriReviews;
        this.context = context;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Log.i(LOG_TAG,"***** onCreateLoader ****");
        CursorLoader dataCursor = new CursorLoader(context,
                mUriReviews,
                null,
                null,
                null,
                null);
        return dataCursor;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Log.i(LOG_TAG,"***** onLoadFinished ****");
        if (data.moveToFirst()){
            do {
                Log.i(LOG_TAG," data = "+data.getString(1)+ " key ="+data.getString(2));
            }while (data.moveToNext());
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        Log.i(LOG_TAG,"***** onLoaderReset ****");
    }
}
