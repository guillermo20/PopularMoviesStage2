package com.example.guillermo.popularmovies.model;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by guillermo on 10/18/16.
 */

public class ReviewMovieInfo implements Serializable {

    private String reviewId;

    private String author;

    private String content;

    private String url;

    public ReviewMovieInfo( String reviewId, String author, String content, String url) {
        this.reviewId = reviewId;
        this.author = author;
        this.content = content;
        this.url = url;
    }

    public static ReviewMovieInfo fromJsonToReviewMovieInfo(String json){

        try {
            JSONObject jsonObject = new JSONObject(json);
            String reviewId = jsonObject.getString("id");
            String author = jsonObject.getString("author");
            String content = jsonObject.getString("content");
            String url = jsonObject.getString("url");

            return new ReviewMovieInfo(reviewId,author,content,url);
        }catch (JSONException e){
            Log.e("ReviewMovieInfo",e.getMessage());
        }
        return null;
    }
}
