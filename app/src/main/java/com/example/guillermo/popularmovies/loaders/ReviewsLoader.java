package com.example.guillermo.popularmovies.loaders;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;

import com.example.guillermo.popularmovies.adapters.ReviewsAdapter;

/**
 * Created by guillermo on 11/15/16.
 */

public class ReviewsLoader implements LoaderManager.LoaderCallbacks<Cursor> {

    private String LOG_TAG = ReviewsLoader.class.getSimpleName();

    private Uri mUriReviews;

    private Context context;

    private ReviewsAdapter reviewsAdapter;
    public ReviewsLoader(Uri mUriReviews, Context context, ReviewsAdapter reviewsAdapter) {
        this.mUriReviews = mUriReviews;
        this.context = context;
        this.reviewsAdapter = reviewsAdapter;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Log.i(LOG_TAG,"***** onCreateLoader ****");
        if (mUriReviews!=null){
            CursorLoader dataCursor = new CursorLoader(context,
                    mUriReviews,
                    null,
                    null,
                    null,
                    null);
            return dataCursor;
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Log.i(LOG_TAG,"***** onLoadFinished ****");
        reviewsAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        Log.i(LOG_TAG,"***** onLoaderReset ****");
        reviewsAdapter.swapCursor(null);
    }
}
