package com.example.guillermo.popularmovies.model;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by guillermo on 10/15/16.
 */

public class VideoMovieInfo implements Serializable {

    private String videoId;

    private String key;

    private String name;

    private String site;

    private String size;

    private String type;

    public VideoMovieInfo(String videoId, String key, String name, String site, String size, String type) {
        this.videoId = videoId;
        this.key = key;
        this.name = name;
        this.site = site;
        this.size = size;
        this.type = type;
    }

    public String getVideoId() {
        return videoId;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public String getSite() {
        return site;
    }

    public String getSize() {
        return size;
    }

    public String getType() {
        return type;
    }

    public static VideoMovieInfo toVideoMovieInfoFromJson(String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            String videoId = jsonObject.getString("id");
            String key = jsonObject.getString("key");
            String name = jsonObject.getString("name");
            String site = jsonObject.getString("site");
            String size = jsonObject.getString("size");
            String type = jsonObject.getString("type");

            return new VideoMovieInfo(videoId,key,name,site,size,type);
        }catch (JSONException e){
            Log.e("VideoMovieInfo",e.getMessage());
        }
        return null;
    }
}
