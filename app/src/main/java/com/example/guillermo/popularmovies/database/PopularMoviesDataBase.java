package com.example.guillermo.popularmovies.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import net.simonvt.schematic.annotation.Database;
import net.simonvt.schematic.annotation.ExecOnCreate;
import net.simonvt.schematic.annotation.OnCreate;
import net.simonvt.schematic.annotation.Table;

/**
 * Created by guillermo on 10/24/16.
 */

@Database(version = PopularMoviesDataBase.VERSION)
public final class PopularMoviesDataBase {

    public static final int VERSION = 1;

    @Table(MoviesColumnList.class) public static final String MOVIES = "movies";
    @Table(ReviewsColumnList.class) public static final String REVIEWS = "reviews";
    @Table(TrailersColumnList.class) public static final String TRAILERS = "trailers";

    @OnCreate
    public static void onCreate(Context context, SQLiteDatabase db) {
        System.out.println("********************** DATABASE CREATED ***************************");
        Log.i(PopularMoviesDataBase.class.getSimpleName(),"********************** DATABASE CREATED ***************************");
    }

    @ExecOnCreate
    public static final String EXEC_ON_CREATE = "SELECT * FROM " + MOVIES;
}
