package com.example.guillermo.popularmovies.database;

import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.ConflictResolutionType;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.Database;
import net.simonvt.schematic.annotation.DefaultValue;
import net.simonvt.schematic.annotation.PrimaryKey;
import net.simonvt.schematic.annotation.Unique;

import java.util.function.BooleanSupplier;

/**
 * Created by guillermo on 10/24/16.
 */

public interface MoviesColumnList {

    @DataType(DataType.Type.INTEGER) @PrimaryKey
    @AutoIncrement
    String _ID = "_id";

    @DataType(DataType.Type.TEXT) @Unique(onConflict = ConflictResolutionType.IGNORE)
    String MOVIE_ID = "movie_id";

    @DataType(DataType.Type.TEXT)
    String TITLE = "title";

    @DataType(DataType.Type.TEXT)
    String POSTERPATH = "poster_path";

    @DataType(DataType.Type.TEXT)
    String ADULT = "adult";

    @DataType(DataType.Type.TEXT)
    String OVERVIEW = "overview";

    @DataType(DataType.Type.TEXT)
    String RELEASE_DATE = "release_date";

    @DataType(DataType.Type.TEXT)
    String ORIGINAL_TITLE = "original_title";

    @DataType(DataType.Type.TEXT)
    String ORIGINAL_LANGUAGE = "original_language";

    @DataType(DataType.Type.TEXT)
    String BACKDROP_PATH = "backdrop_path";

    @DataType(DataType.Type.REAL)
    String POPULARITY = "popularity";

    @DataType(DataType.Type.INTEGER)
    String VOTE_COUNT = "vote_count";

    @DataType(DataType.Type.TEXT)
    String VIDEO = "video";

    @DataType(DataType.Type.REAL)
    String VOTE_AVERAGE = "vote_average";

    @DataType(DataType.Type.TEXT) @DefaultValue("false")
    String FAVORITE_FLAG = "favorite_flag";

    @DataType(DataType.Type.TEXT)
    String SORT_TYPE_POPULAR = "sort_type_popular";

    @DataType(DataType.Type.TEXT)
    String SORT_TYPE_RATED = "sort_type_rated";

    @DataType(DataType.Type.TEXT)
    String SORT_TYPE_FAVORITES = "sort_type_favorites";


    @DataType(DataType.Type.BLOB)
    String POSTER_IMAGE = "poster_image";

}
