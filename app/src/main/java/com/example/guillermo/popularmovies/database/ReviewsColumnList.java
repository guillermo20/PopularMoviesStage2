package com.example.guillermo.popularmovies.database;

import net.simonvt.schematic.annotation.ConflictResolutionType;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.PrimaryKey;
import net.simonvt.schematic.annotation.References;

/**
 * Created by guillermo on 10/24/16.
 */

public interface ReviewsColumnList {
    @DataType(DataType.Type.TEXT) @PrimaryKey(onConflict = ConflictResolutionType.REPLACE)
    String _ID = "review_id";

    @DataType(DataType.Type.TEXT)
    String REVIEWS = "reviews";

    @DataType(DataType.Type.TEXT)
    String AUTHOR = "author";

    @DataType(DataType.Type.TEXT)
    String CONTENT = "content";

    @DataType(DataType.Type.TEXT)
    String URL = "url";

    @DataType(DataType.Type.TEXT) @References(table = PopularMoviesDataBase.MOVIES , column = MoviesColumnList.MOVIE_ID)
    String MOVIE_ID = "movie_id";
}
