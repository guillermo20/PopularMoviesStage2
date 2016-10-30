package com.example.guillermo.popularmovies.database;

import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;
import net.simonvt.schematic.annotation.References;

/**
 * Created by guillermo on 10/24/16.
 */

public interface ReviewsColumnList {
    @DataType(DataType.Type.INTEGER) @PrimaryKey
    @AutoIncrement
    String _ID = "_id";

    @DataType(DataType.Type.TEXT) @NotNull
    String REVIEWS = "reviews";

    @DataType(DataType.Type.TEXT) @NotNull
    String AUTHRO = "author";

    @DataType(DataType.Type.TEXT) @NotNull
    String CONTENT = "content";

    @DataType(DataType.Type.TEXT) @NotNull
    String URL = "url";

    @DataType(DataType.Type.TEXT) @References(table = PopularMoviesDataBase.MOVIES , column = MoviesColumnList.MOVIE_ID)
    String MOVIE_ID = "movie_id";
}
