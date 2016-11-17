package com.example.guillermo.popularmovies.sync;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SyncResult;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;

import com.example.guillermo.popularmovies.BuildConfig;
import com.example.guillermo.popularmovies.R;
import com.example.guillermo.popularmovies.database.MoviesColumnList;
import com.example.guillermo.popularmovies.database.PopularMoviesProvider;
import com.example.guillermo.popularmovies.database.ReviewsColumnList;
import com.example.guillermo.popularmovies.database.TrailersColumnList;
import com.example.guillermo.popularmovies.enums.SortingMethod;
import com.example.guillermo.popularmovies.model.MovieItem;
import com.example.guillermo.popularmovies.model.ReviewMovieInfo;
import com.example.guillermo.popularmovies.model.VideoMovieInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by guillermo on 10/29/16.
 */

public class MoviesSyncAdapter extends AbstractThreadedSyncAdapter {

    private final String LOG_TAG = "MoviesSyncAdapter";

    private final String POPULAR_MOVIES_URL = "http://api.themoviedb.org/3/movie/";

    private String POPULAR_MOVIES_VIDEO_URL = "http://api.themoviedb.org/3/movie/$movie_id/videos";

    private String POPULAR_MOVIES_REVIEW_URL = "http://api.themoviedb.org/3/movie/$movie_id/reviews";

    public static final String POPULAR_MOVIES = "popular";
    public static final String TOP_RATED_MOVIES = "top_rated";

    private String sortingParam;

    private Context context;

    public MoviesSyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        this.context = context;
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
        SharedPreferences prefs = context.getSharedPreferences(SortingMethod.class.getSimpleName().toLowerCase(),Context.MODE_PRIVATE);
        String sortingParam = prefs.getString(SortingMethod.class.getSimpleName().toLowerCase(),SortingMethod.POPULAR_MOVIES_SORT.getCode());
        List<MovieItem> listMovieItem;
        List<ContentValues> moviecontentlist = new ArrayList<>();
        List<ContentValues> videoContentList = new ArrayList<>();
        List<ContentValues> reviewcontentList = new ArrayList<>();
        Log.i(LOG_TAG,"********************* on permform sync called *********************** sortingParam = "+sortingParam);
        String[] results = queryTheMoviedb(sortingParam);
        if (results != null) {
            listMovieItem = new ArrayList<>();
            moviecontentlist = new ArrayList<>();
            for (int i = 0; i < results.length; i++) {
                MovieItem movieItem = MovieItem.toMovieItemFromJson(results[i]);
                String[] queryResults=queryVideoInfo(movieItem);
                List<VideoMovieInfo> listOfVideos = new ArrayList<>();
                if(queryResults!=null){
                    for (int j = 0; j<queryResults.length;j++){
                        VideoMovieInfo videoItem =VideoMovieInfo.toVideoMovieInfoFromJson(queryResults[j]);
                        listOfVideos.add(videoItem);
                        ContentValues videoContentValues = new ContentValues();
                        videoContentValues.put(TrailersColumnList.VIDEO_ID,videoItem.getVideoId());
                        videoContentValues.put(TrailersColumnList.MOVIE_ID,movieItem.getMovieId());
                        videoContentValues.put(TrailersColumnList.NAME,videoItem.getName());
                        videoContentValues.put(TrailersColumnList.KEY,videoItem.getKey());
                        videoContentValues.put(TrailersColumnList.SITE,videoItem.getSite());
                        videoContentList.add(videoContentValues);
                    }
                    movieItem.setVideos(listOfVideos);
                }
                List<ReviewMovieInfo> reviewMovieInfoResults = new ArrayList<>();
                queryResults = queryReviewInfo(movieItem);
                if(queryResults!=null){
                    for (int j = 0; j<queryResults.length;j++){
                        ReviewMovieInfo reviewItem = ReviewMovieInfo.fromJsonToReviewMovieInfo(queryResults[j]);
                        reviewMovieInfoResults.add(reviewItem);
                        ContentValues reviewContentValues = new ContentValues();
                        reviewContentValues.put(ReviewsColumnList.MOVIE_ID,movieItem.getMovieId());
                        reviewContentValues.put(ReviewsColumnList.CONTENT,reviewItem.getContent());
                        reviewContentValues.put(ReviewsColumnList.AUTHOR,reviewItem.getAuthor());
                        reviewContentValues.put(ReviewsColumnList.URL,reviewItem.getUrl());
                        reviewContentValues.put(ReviewsColumnList.REVIEW_ID,reviewItem.getReviewId());
                        reviewcontentList.add(reviewContentValues);
                    }
                    movieItem.setReviews(reviewMovieInfoResults);
                }
                byte[] posterImage = queryMoviePoster(movieItem);
                listMovieItem.add(movieItem);
                ContentValues movieContentValues = new ContentValues();
                movieContentValues.put(MoviesColumnList.MOVIE_ID,movieItem.getMovieId());
                movieContentValues.put(MoviesColumnList.TITLE,movieItem.getTitle());
                movieContentValues.put(MoviesColumnList.BACKDROP_PATH,movieItem.getBackdropPath());
                movieContentValues.put(MoviesColumnList.RELEASE_DATE,movieItem.getReleaseDate());
                movieContentValues.put(MoviesColumnList.POSTERPATH,movieItem.getPosterPath());
                movieContentValues.put(MoviesColumnList.OVERVIEW,movieItem.getOverview());
                switch (SortingMethod.getByCode(sortingParam)){
                    case POPULAR_MOVIES_SORT:
                        movieContentValues.put(MoviesColumnList.SORT_TYPE_POPULAR,sortingParam);
                        break;
                    case TOP_RATED_MOVIES_SORT:
                        movieContentValues.put(MoviesColumnList.SORT_TYPE_RATED,sortingParam);
                        break;
                    case FAVORITES_MOVIES_SORT:
                        movieContentValues.put(MoviesColumnList.SORT_TYPE_FAVORITES,sortingParam);
                        break;
                }
                movieContentValues.put(MoviesColumnList.POSTER_IMAGE,posterImage);
                moviecontentlist.add(movieContentValues);
                context.getContentResolver().insert(PopularMoviesProvider.Movies.CONTENT_URI,movieContentValues);

            }
            if (!reviewcontentList.isEmpty()){
                ContentValues[] contentArray = new ContentValues[reviewcontentList.size()];
                reviewcontentList.toArray(contentArray);
                context.getContentResolver().bulkInsert(PopularMoviesProvider.Reviews.CONTENT_URI,contentArray);
            }
            if (!videoContentList.isEmpty()){
                ContentValues[] contentArray = new ContentValues[videoContentList.size()];
                videoContentList.toArray(contentArray);
                context.getContentResolver().bulkInsert(PopularMoviesProvider.Trailers.CONTENT_URI,contentArray);
            }
        }
    }

    public static void syncImmediately(Context context) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        ContentResolver.requestSync(getSyncAccount(context),
                context.getString(R.string.content_authority), bundle);
    }


    public static Account getSyncAccount(Context context) {
        // Get an instance of the Android account manager
        AccountManager accountManager =
                (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);

        // Create the account type and default account
        Account newAccount = new Account(
                context.getString(R.string.app_name), context.getString(R.string.sync_account_type));

        // If the password doesn't exist, the account doesn't exist
        if ( null == accountManager.getPassword(newAccount) ) {

        /*
         * Add the account and account type, no password or user data
         * If successful, return the Account object, otherwise report an error.
         */
            if (!accountManager.addAccountExplicitly(newAccount, "", null)) {
                return null;
            }
            /*
             * If you don't set android:syncable="true" in
             * in your <provider> element in the manifest,
             * then call ContentResolver.setIsSyncable(account, AUTHORITY, 1)
             * here.
             */

        }
        return newAccount;
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
        if(sortType.equals(SortingMethod.POPULAR_MOVIES_SORT.getCode()) || sortType.equals(SortingMethod.TOP_RATED_MOVIES_SORT.getCode())) {
            try {
                URL url = new URL(POPULAR_MOVIES_URL + sortType + "?api_key=" + THE_MOVIEDB_API_KEY);
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
                Log.v("ForecastFragment", " movie json string: " + forecastJsonStr);

            } catch (Exception e) {
                Log.e(LOG_TAG, e.getMessage());
            }
        }
        //TODO: find the favorited movies to return from the DB
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

    /**
     * checks if the smartphone has an active conection to internet.
     * @return
     */
    public boolean isNetworkConectivityOnline() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }


    private String[] queryVideoInfo(MovieItem movieItem){
        HttpURLConnection urlConnection;
        BufferedReader reader = null;
        String forecastJsonStr = "";
        String THE_MOVIEDB_API_KEY = BuildConfig.THE_MOVIEDB_API_KEY;
        //this part of the code was taken from the sunshine example on how to retreive the data from a rest api
        if(!isNetworkConectivityOnline()){
            return null;
        }
        try {
            String strUrl = POPULAR_MOVIES_VIDEO_URL.replace("$movie_id",movieItem.getMovieId())+"?api_key="+THE_MOVIEDB_API_KEY;
            URL url = new URL(strUrl);
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
            //Log.v("ForecastFragment", " trailers json string: " + forecastJsonStr);

        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage());
        }
        return getListFromJson(forecastJsonStr);
    }

    private String[] queryReviewInfo(MovieItem movieItem){
        HttpURLConnection urlConnection;
        BufferedReader reader = null;
        String forecastJsonStr = "";
        String THE_MOVIEDB_API_KEY = BuildConfig.THE_MOVIEDB_API_KEY;
        //this part of the code was taken from the sunshine example on how to retreive the data from a rest api
        if(!isNetworkConectivityOnline()){
            return null;
        }
        try {
            String strUrl = POPULAR_MOVIES_REVIEW_URL.replace("$movie_id",movieItem.getMovieId())+"?api_key="+THE_MOVIEDB_API_KEY;
            URL url = new URL(strUrl);
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
            //Log.v("ForecastFragment", " review json string: " + forecastJsonStr);

        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage());
        }
        return getListFromJson(forecastJsonStr);
    }


    private byte[] queryMoviePoster(MovieItem item){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] result = null;
        try {
            URL url = new URL(item.getPosterUri(MovieItem.IMAGE_SIZE_W342).toString());
            InputStream inputStream = url.openStream();
            byte[] b = new byte[4096];
            int length;
            while ((length = inputStream.read(b)) != -1) {
                outputStream.write(b, 0, length);
            }
            result = outputStream.toByteArray();
            inputStream.close();
            outputStream.close();
        }catch (Exception e){
            Log.e(LOG_TAG,e.getMessage());
        }
        return result;
    }
}
