package com.example.guillermo.popularmovies.backgroundtasks;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.guillermo.popularmovies.BuildConfig;
import com.example.guillermo.popularmovies.model.MovieItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by guillermo on 11/09/16.
 */
public class FetchPopularMoviesTask extends AsyncTask<String, Void, List<MovieItem>> {

    private final String LOG_TAG = FetchPopularMoviesTask.class.getSimpleName();
    private final String POPULAR_MOVIES_URL = "http://api.themoviedb.org/3/movie/";

    public static final String POPULAR_MOVIES = "popular";
    public static final String TOP_RATED_MOVIES = "top_rated";
    private List<MovieItem> listMovieItem;

    private boolean flag;

    private Context context;

    private ArrayAdapter<MovieItem> adapter;

    public FetchPopularMoviesTask(Context context,ArrayAdapter<MovieItem> adapter) {
        this.adapter = adapter;
        this.context = context;
    }

    @Override
    protected List<MovieItem> doInBackground(String... params) {

        while(true){
            Log.i(LOG_TAG,"while");
            if (isNetworkConectivityOnline()){
                if (!flag){
                    flag = true;
                    String[] results = queryTheMoviedb(params[0]);
                    if (results != null) {
                        if (listMovieItem == null) {
                            listMovieItem = new ArrayList<>();
                        }
                        for (int i = 0; i < results.length; i++) {
                            listMovieItem.add(MovieItem.toMovieItemFromJson(results[i]));
                        }
                    }
                    return listMovieItem;
                }
            }else{
                flag= false;
            }
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }

    @Override
    protected void onPostExecute(List<MovieItem> movieItems) {
        if (movieItems != null) {
            Log.i(LOG_TAG, "refreshing adapter " + movieItems.size());
            for (MovieItem item : movieItems) {
                Log.v(LOG_TAG, "Item =" + item.getTitle());
            }
            adapter.clear();
            adapter.addAll(movieItems);
        }else{
            Toast.makeText(context,"Couldn't refresh info",Toast.LENGTH_SHORT).show();
        }
    }

    private String[] queryTheMoviedb(String sortType) {
        HttpURLConnection urlConnection;
        BufferedReader reader = null;
        String forecastJsonStr = "";
        String THE_MOVIEDB_API_KEY = BuildConfig.THE_MOVIEDB_API_KEY;
        //this part of the code was taken from the sunshine example on how to retreive the data from a rest api
        if(!isNetworkConectivityOnline()){
            return null;
        }
        try {
            URL url = new URL(POPULAR_MOVIES_URL + sortType + "?api_key="+THE_MOVIEDB_API_KEY);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }


            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }

            forecastJsonStr = buffer.toString();
            Log.v("ForecastFragment", " json string: " + forecastJsonStr);

        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage());
        }
        return getListFromJson(forecastJsonStr);
    }

    private String[] getListFromJson(String json) {
        String[] results = null;
        if (!json.equals("")) {
            try {
                JSONArray jsonArray;
                JSONObject jsonObject = new JSONObject(json);
                jsonArray = jsonObject.getJSONArray("results");
                results = new String[jsonArray.length()];
                for (int i = 0; i < jsonArray.length(); i++) {
                    results[i] = jsonArray.getString(i);
                }
                return results;
            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage());
            }
        }
        return results;
    }

    public List<MovieItem> getListMovieItem() {
        return listMovieItem;
    }


    /**
     * checks if the smartphone has an active conection to internet.
     * @return
     */
    public boolean isNetworkConectivityOnline() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public Boolean isOnline() {
        try {
            Process p1 = java.lang.Runtime.getRuntime().exec("ping -c 1 www.google.com");
            int returnVal = p1.waitFor();
            boolean reachable = (returnVal==0);
            return reachable;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }

}
