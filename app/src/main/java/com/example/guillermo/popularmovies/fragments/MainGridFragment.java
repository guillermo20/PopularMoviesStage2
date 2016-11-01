package com.example.guillermo.popularmovies.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
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
import com.example.guillermo.popularmovies.enums.SortingMethod;
import com.example.guillermo.popularmovies.model.MovieItem;
import com.example.guillermo.popularmovies.sync.MoviesSyncAdapter;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by guillermo on 11/09/16.
 */
public class MainGridFragment extends Fragment {

    private final String LOG_TAG = MainGridFragment.class.getSimpleName();

    /*private List<String> popularMoviesList;
    private List<String> mostRatedMoviesList;
    */
    private FetchPopularMoviesTask backgroundTask;
    private ArrayAdapter<MovieItem> adapter;
    private ArrayAdapter<String> sortingAdapter;
    private String options[] = {"Most Popular","Most Rated"};
    private int option=0;

    @Override
    public void onCreate(Bundle savedInstanceState) {


        adapter = new GridAdapter(getActivity(),R.layout.main_grid_fragment,new ArrayList<MovieItem>());
        sortingAdapter = new ArrayAdapter <String> (getActivity(),R.layout.spinner_item,R.id.spinner_texview_id, Arrays.asList(options));
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }


    @Override
    public void onResume() {
        super.onResume();
        //MoviesSyncAdapter.syncImmediately(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.v(LOG_TAG, "maingrid oncreateview");
        View root = inflater.inflate(R.layout.main_grid_fragment, container, false);
        GridView gridView = (GridView) root.findViewById(R.id.main_grid_fragment_id);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MovieItem item = (MovieItem) parent.getItemAtPosition(position);
                startActivity(new Intent(getActivity(),MovieDetailActivity.class).putExtra(MovieItem.class.getSimpleName(),item));
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

                /*option=position;
                backgroundTask = new FetchPopularMoviesTask(getActivity(),adapter);
                switch (position){
                    case 0:
                        backgroundTask.execute(FetchPopularMoviesTask.POPULAR_MOVIES);
                        break;
                    case 1:
                        backgroundTask.execute(FetchPopularMoviesTask.TOP_RATED_MOVIES);
                        break;
                }*/
                SharedPreferences.Editor editor = getActivity().getSharedPreferences(SortingMethod.class.getSimpleName().toLowerCase(),Context.MODE_PRIVATE).edit();
                switch (position){
                    case 0:
                        editor.putString(SortingMethod.class.getSimpleName().toLowerCase(),SortingMethod.POPULAR_MOVIES_SORT.getCode());
                        editor.commit();
                        MoviesSyncAdapter.syncImmediately(getActivity());
                        break;
                    case 1:
                        editor.putString(SortingMethod.class.getSimpleName().toLowerCase(),SortingMethod.TOP_RATED_MOVIES_SORT.getCode());
                        editor.commit();
                        MoviesSyncAdapter.syncImmediately(getActivity());
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    public boolean isNetworkConectivityOnline() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
