package com.example.guillermo.popularmovies.database;

import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;
import net.simonvt.schematic.annotation.References;

/**
 * Created by guillermo on 10/24/16.
 */

public interface TrailersColumnList {

    @DataType(DataType.Type.INTEGER) @PrimaryKey
    @AutoIncrement
    String _ID = "_id";

    @DataType(DataType.Type.TEXT) @NotNull
    String VIDEO_ID = "video_id";

    @DataType(DataType.Type.TEXT) @NotNull
    String KEY = "key";

    @DataType(DataType.Type.TEXT) @NotNull
    String NAME = "name";

    @DataType(DataType.Type.TEXT) @NotNull
    String SITE = "site";

    @DataType(DataType.Type.TEXT) @NotNull
    String SIZE = "size";

    @DataType(DataType.Type.TEXT) @NotNull
    String TYPE = "type";

    @DataType(DataType.Type.TEXT) @References(table = PopularMoviesDataBase.MOVIES , column = MoviesColumnList.MOVIE_ID)
    String MOVIE_ID = "movie_id";
}
