package com.example.guillermo.popularmovies.fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.PorterDuff;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.MenuItemCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Spinner;

import com.example.guillermo.popularmovies.MovieDetailActivity;
import com.example.guillermo.popularmovies.R;
import com.example.guillermo.popularmovies.adapters.GridAdapter;
import com.example.guillermo.popularmovies.backgroundtasks.FetchPopularMoviesTask;
import com.example.guillermo.popularmovies.database.MoviesColumnList;
import com.example.guillermo.popularmovies.database.PopularMoviesProvider;
import com.example.guillermo.popularmovies.enums.MoviesTableProjection;
import com.example.guillermo.popularmovies.enums.SortingMethod;
import com.example.guillermo.popularmovies.model.MovieItem;
import com.example.guillermo.popularmovies.sync.MoviesSyncAdapter;

import java.util.Arrays;

/**
 * Created by guillermo on 11/09/16.
 */
public class MainGridFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private final String LOG_TAG = MainGridFragment.class.getSimpleName();

    private FetchPopularMoviesTask backgroundTask;
    private GridAdapter adapter;
    private ArrayAdapter<String> sortingAdapter;
    private String options[] = {"Most Popular","Most Rated"};
    private int option=0;

    private static final int LOADER_ID = 0;

    public MainGridFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        adapter = new GridAdapter(getActivity(),null,0);
        sortingAdapter = new ArrayAdapter <String> (getActivity(),R.layout.spinner_item,R.id.spinner_texview_id, Arrays.asList(options));
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.main_grid_fragment, container, false);
        GridView gridView = (GridView) root.findViewById(R.id.main_grid_fragment_id);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i(LOG_TAG, " item clicked position = "+position);
                Cursor cursor = (Cursor) parent.getItemAtPosition(position);
                Uri trailerContentUri = PopularMoviesProvider.Trailers.withId(cursor.getLong(MoviesTableProjection.MOVIE_ID.getCode()));
                Uri reviewContentUri = PopularMoviesProvider.Reviews.withId(cursor.getLong(MoviesTableProjection.MOVIE_ID.getCode()));
                MovieItem item = makeMovieItem(cursor);
                Intent intent = new Intent(getActivity(),MovieDetailActivity.class);
                intent.setData(trailerContentUri);
                //TODO: find the contentUri of the trailers.
                intent.putExtra(MovieDetailsFragment.REVIEW_URI,reviewContentUri);
                intent.putExtra("movieItem",item);
                startActivity(intent);
            }
        });
        return root;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.main_menu,menu);
        MenuItem menuItem = menu.findItem(R.id.main_menu_spinner);
        Spinner spinner = (Spinner) MenuItemCompat.getActionView(menuItem);
        spinner.setAdapter(sortingAdapter);
        spinner.getPopupBackground().setColorFilter(getResources().getColor(R.color.colorPrimaryDark), PorterDuff.Mode.SCREEN);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.v(LOG_TAG,"clicked on item from spinner");
                SharedPreferences.Editor editor = getActivity().getSharedPreferences(SortingMethod.class.getSimpleName().toLowerCase(),Context.MODE_PRIVATE).edit();
                switch (position){
                    case 0:
                        editor.putString(SortingMethod.class.getSimpleName().toLowerCase(),SortingMethod.POPULAR_MOVIES_SORT.getCode());
                        editor.commit();
                        MoviesSyncAdapter.syncImmediately(getActivity());
                        restartLoader();
                        break;
                    case 1:
                        editor.putString(SortingMethod.class.getSimpleName().toLowerCase(),SortingMethod.TOP_RATED_MOVIES_SORT.getCode());
                        editor.commit();
                        MoviesSyncAdapter.syncImmediately(getActivity());
                        restartLoader();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void restartLoader(){
        getLoaderManager().restartLoader(LOADER_ID,null,this);
    }

    public boolean isNetworkConectivityOnline() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(LOADER_ID,null,this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri movieUri = PopularMoviesProvider.Movies.CONTENT_URI;
        SharedPreferences prefs = getActivity().getSharedPreferences(SortingMethod.class.getSimpleName().toLowerCase(),Context.MODE_PRIVATE);
        String sortParam = prefs.getString(SortingMethod.class.getSimpleName().toLowerCase(),SortingMethod.POPULAR_MOVIES_SORT.getCode());
        String selection= "";
        switch (SortingMethod.getByCode(sortParam)){
            case POPULAR_MOVIES_SORT: selection= MoviesColumnList.SORT_TYPE_POPULAR + "=?"; break;
            case TOP_RATED_MOVIES_SORT: selection= MoviesColumnList.SORT_TYPE_RATED + "=?"; break;
            case FAVORITES_MOVIES_SORT: selection= MoviesColumnList.SORT_TYPE_FAVORITES + "=?"; break;
        }

        String[] selectionArgs = { String.valueOf(sortParam)};
        return new CursorLoader(getActivity(),
                movieUri,
                null,
                selection,
                selectionArgs,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        //Log.i(LOG_TAG,"******* onLoadFinished called *********");
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        //Log.i(LOG_TAG,"******* onLoaderReset called *********");
        adapter.swapCursor(null);
    }

    private MovieItem makeMovieItem(Cursor cursor){
        MovieItem item = new MovieItem(
                cursor.getString(MoviesTableProjection.POSTERPATH.getCode()),
                Boolean.getBoolean(cursor.getString(MoviesTableProjection.ADULT.getCode())),
                cursor.getString(MoviesTableProjection.OVERVIEW.getCode()),
                cursor.getString(MoviesTableProjection.RELEASE_DATE.getCode()),
                cursor.getString(MoviesTableProjection.MOVIE_ID.getCode()),
                cursor.getString(MoviesTableProjection.ORIGINAL_TITLE.getCode()),
                cursor.getString(MoviesTableProjection.ORIGINAL_LANGUAGE.getCode()),
                cursor.getString(MoviesTableProjection.TITLE.getCode()),
                cursor.getString(MoviesTableProjection.BACKDROP_PATH.getCode()),
                cursor.getDouble(MoviesTableProjection.POPULARITY.getCode()),
                cursor.getLong(MoviesTableProjection.VOTE_COUNT.getCode()),
                Boolean.getBoolean(cursor.getString(MoviesTableProjection.VIDEO.getCode())),
                cursor.getDouble(MoviesTableProjection.VOTE_AVERAGE.getCode())
        );
        return item;
    }
}
