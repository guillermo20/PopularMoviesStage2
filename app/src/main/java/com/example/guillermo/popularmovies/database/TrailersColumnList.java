package com.example.guillermo.popularmovies.database;

import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.ConflictResolutionType;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.PrimaryKey;
import net.simonvt.schematic.annotation.References;
import net.simonvt.schematic.annotation.Unique;

/**
 * Created by guillermo on 10/24/16.
 */

public interface TrailersColumnList {

    @DataType(DataType.Type.INTEGER) @PrimaryKey
    @AutoIncrement
    String _ID = "_id";

    @DataType(DataType.Type.TEXT) @Unique(onConflict = ConflictResolutionType.REPLACE)
    String VIDEO_ID = "video_id";

    @DataType(DataType.Type.TEXT)
    String KEY = "key";

    @DataType(DataType.Type.TEXT)
    String NAME = "name";

    @DataType(DataType.Type.TEXT)
    String SITE = "site";

    @DataType(DataType.Type.TEXT)
    String SIZE = "size";

    @DataType(DataType.Type.TEXT)
    String TYPE = "type";

    @DataType(DataType.Type.TEXT) @References(table = PopularMoviesDataBase.MOVIES , column = MoviesColumnList.MOVIE_ID)
    String MOVIE_ID = "movie_id";
}
