package com.example.guillermo.popularmovies.loaders;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;

/**
 * Created by guillermo on 11/15/16.
 */

public class ReviewsLoader implements LoaderManager.LoaderCallbacks<Cursor> {

    private String LOG_TAG = ReviewsLoader.class.getSimpleName();

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Log.i(LOG_TAG,"***** onCreateLoader ****");
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Log.i(LOG_TAG,"***** onLoadFinished ****");
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        Log.i(LOG_TAG,"***** onLoaderReset ****");
    }
}
