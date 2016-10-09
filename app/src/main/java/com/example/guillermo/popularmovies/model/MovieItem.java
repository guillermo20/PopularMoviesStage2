package com.example.guillermo.popularmovies.model;

import android.net.Uri;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by guillermo on 11/09/16.
 * needed object to handle all the data fetched from themoviedb server.
 */
public class MovieItem implements Serializable{

    private String posterPath;
    private boolean adult;
    private String overview;
    private String releaseDate;
    private String movieId;
    private String originalTitle;
    private String originalLanguage;
    private String title;
    private String backdropPath;
    private double popularity;
    private long voteCount;
    private boolean video;
    private double voteAverage;

    public  static  final String IMAGE_SIZE_W154="w154";
    public  static  final String IMAGE_SIZE_W185="w185";
    public  static  final String IMAGE_SIZE_W342="w342";
    public  static  final String IMAGE_SIZE_W500="w500";

    public MovieItem(String posterPath, boolean adult, String overview, String releaseDate, String movieId, String originalTitle, String originalLanguage, String title, String backdropPath, double popularity, long voteCount, boolean video, double voteAverage) {
        this.posterPath = posterPath;
        this.adult = adult;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.movieId = movieId;
        this.originalTitle = originalTitle;
        this.originalLanguage = originalLanguage;
        this.title = title;
        this.backdropPath = backdropPath;
        this.popularity = popularity;
        this.voteCount = voteCount;
        this.video = video;
        this.voteAverage = voteAverage;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public boolean isAdult() {
        return adult;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getMovieId() {
        return movieId;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public String getTitle() {
        return title;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public double getPopularity() {
        return popularity;
    }

    public long getVoteCount() {
        return voteCount;
    }

    public boolean isVideo() {
        return video;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public Uri getPosterUri(String posterSize){
        Uri uri = Uri.parse("http://image.tmdb.org/t/p/"+posterSize+"/"+posterPath).buildUpon().build();
        return uri;
    }

    public Uri getBackdropUri(String posterSize){
        Uri uri = Uri.parse("http://image.tmdb.org/t/p/"+posterSize+"/"+backdropPath).buildUpon().build();
        return  uri;
    }

    public static MovieItem toMovieItemFromJson(String strJson){
        try {
            JSONObject jsonObject = new JSONObject(strJson);
            String posterPath = jsonObject.getString("poster_path");
            boolean adult = jsonObject.getBoolean("adult");
            String overview = jsonObject.getString("overview");
            String releaseDate = jsonObject.getString("release_date");
            String movieId = jsonObject.getString("id");
            String originalTitle = jsonObject.getString("original_title");
            String originalLanguage = jsonObject.getString("original_language");
            String title = jsonObject.getString("title");
            String backdropPath = jsonObject.getString("backdrop_path");
            double popularity=jsonObject.getDouble("popularity");
            long voteCount=jsonObject.getLong("vote_count");
            boolean video=jsonObject.getBoolean("video");
            double voteAverage=jsonObject.getDouble("vote_average");

            return  new MovieItem(posterPath,adult,overview,releaseDate,movieId,originalTitle,originalLanguage,title,backdropPath,popularity,voteCount,video,voteAverage);
        }catch (JSONException e){
            Log.e("MovieItem",e.getMessage());
        }
        return null;
    }

}
