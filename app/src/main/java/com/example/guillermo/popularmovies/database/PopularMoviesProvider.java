package com.example.guillermo.popularmovies.database;

import android.net.Uri;

import net.simonvt.schematic.annotation.ContentProvider;
import net.simonvt.schematic.annotation.ContentUri;
import net.simonvt.schematic.annotation.TableEndpoint;

/**
 * Created by guillermo on 10/24/16.
 */
@ContentProvider(authority = PopularMoviesProvider.AUTHORITY, database = PopularMoviesDataBase.class)
public final class PopularMoviesProvider {

    private static final String LOG_TAG = PopularMoviesProvider.class.getSimpleName();

    public static final String AUTHORITY = "com.example.guillermo.popularmovies";

    public PopularMoviesProvider() {
    }

    @TableEndpoint(table = PopularMoviesDataBase.MOVIES) public static class Movies {

        @ContentUri(
                path = "movies",
                type = "vnd.android.cursor.dir/movie")
        public static final Uri MOVIES_URI = Uri.parse("content://" + AUTHORITY + "/movies");
    }

    @TableEndpoint(table = PopularMoviesDataBase.REVIEWS) public static class Reviews {

        @ContentUri(
                path = "reviews",
                type = "vnd.android.cursor.dir/review")
        public static final Uri REVIEWS_URI = Uri.parse("content://" + AUTHORITY + "/reviews");
    }

    @TableEndpoint(table = PopularMoviesDataBase.TRAILERS) public static class Trailers {

        @ContentUri(
                path = "trailers",
                type = "vnd.android.cursor.dir/trailer")
        public static final Uri TRAILERS_URI = Uri.parse("content://" + AUTHORITY + "/trailers");
    }
}
