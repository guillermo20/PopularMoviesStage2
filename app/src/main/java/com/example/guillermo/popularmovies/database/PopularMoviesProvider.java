package com.example.guillermo.popularmovies.database;

import android.net.Uri;

import net.simonvt.schematic.annotation.ContentProvider;
import net.simonvt.schematic.annotation.ContentUri;
import net.simonvt.schematic.annotation.InexactContentUri;
import net.simonvt.schematic.annotation.TableEndpoint;

/**
 * Created by guillermo on 10/24/16.
 */
@ContentProvider(authority = PopularMoviesProvider.AUTHORITY, database = PopularMoviesDataBase.class)
public final class PopularMoviesProvider {

    private static final String LOG_TAG = PopularMoviesProvider.class.getSimpleName();

    public static final String AUTHORITY = "com.example.guillermo.popularmovies";

    static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    private static Uri buildUri(String... paths) {
        Uri.Builder builder = BASE_CONTENT_URI.buildUpon();
        for (String path : paths) {
            builder.appendPath(path);
        }
        return builder.build();
    }

    public PopularMoviesProvider() {
    }

    @TableEndpoint(table = PopularMoviesDataBase.MOVIES) public static class Movies {

        @ContentUri(
                path = "movies",
                type = "vnd.android.cursor.dir/movie")
        public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/movies");
    }

    @TableEndpoint(table = PopularMoviesDataBase.REVIEWS) public static class Reviews {

        @ContentUri(
                path = "reviews",
                type = "vnd.android.cursor.dir/review")
        public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/reviews");
    }

    @TableEndpoint(table = PopularMoviesDataBase.TRAILERS) public static class Trailers {

        @ContentUri(
                path = "trailers",
                type = "vnd.android.cursor.dir/trailer")
        public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/trailers");

        @InexactContentUri(
                name = "TRAILER_ID",
                path = "trailers" + "/#",
                type = "vnd.android.cursor.dir/trailers",
                whereColumn = TrailersColumnList.MOVIE_ID,
                pathSegment = 1)
        public static Uri withId(long id) {
            return buildUri("trailers", String.valueOf(id));
        }

    }
}
