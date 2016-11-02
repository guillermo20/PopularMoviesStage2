package com.example.guillermo.popularmovies;

import android.content.ComponentName;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.net.Uri;
import android.test.AndroidTestCase;
import android.util.Log;

import com.example.guillermo.popularmovies.database.MoviesColumnList;
import com.example.guillermo.popularmovies.database.generated.PopularMoviesProvider;

/**
 * Created by guillermo on 10/29/16.
 */

public class TestProvider extends AndroidTestCase {

    public final static String LOG_TAG = TestProvider.class.getSimpleName();

    public void testContentProvider(){

        PackageManager pm = mContext.getPackageManager();

        // We define the component name based on the package name from the context and the
        // WeatherProvider class.
        ComponentName componentName = new ComponentName(mContext.getPackageName(),
                PopularMoviesProvider.class.getName());
        try {
            // Fetch the provider info using the component name from the PackageManager
            // This throws an exception if the provider isn't registered.
            ProviderInfo providerInfo = pm.getProviderInfo(componentName, 0);

            // Make sure that the registered authority matches the authority from the Contract.
            assertEquals("Error: WeatherProvider registered with authority: " + providerInfo.authority +
                            " instead of authority: " + PopularMoviesProvider.AUTHORITY,
                    providerInfo.authority, PopularMoviesProvider.AUTHORITY);
        } catch (PackageManager.NameNotFoundException e) {
            // I guess the provider isn't registered correctly.
            assertTrue("Error: WeatherProvider not registered at " + mContext.getPackageName(),
                    false);
        }


    }

    public void testInsertProvider(){

        ContentValues values = createMovieValues();

        Uri uri = mContext.getContentResolver().insert(com.example.guillermo.popularmovies.database.PopularMoviesProvider.Movies.CONTENT_URI,values);
        long moviesRowId = ContentUris.parseId(uri);

        // Verify we got a row back.
        assertTrue(moviesRowId != -1);
        Log.i(LOG_TAG, "New row id: " + moviesRowId);

        Cursor cursor = mContext.getContentResolver().query(
                com.example.guillermo.popularmovies.database.PopularMoviesProvider.Movies.CONTENT_URI,
                null,   // projection
                MoviesColumnList._ID + " = " + moviesRowId,
                null,   // Values for the "where" clause
                null    // sort order
        );

        assertTrue("should return a row",cursor.moveToFirst());
        Log.i(LOG_TAG, "row id: " + cursor.getString(0) + "  "+cursor.getString(1));
    }

    static ContentValues createMovieValues() {
        // Create a new map of values, where column names are the keys
        ContentValues testValues = new ContentValues();
        testValues.put(MoviesColumnList.MOVIE_ID,"388835");
        testValues.put(MoviesColumnList.TITLE, "title");
        testValues.put(MoviesColumnList.ADULT, "false");
        testValues.put(MoviesColumnList.BACKDROP_PATH, "nose");

        return testValues;
    }
}
