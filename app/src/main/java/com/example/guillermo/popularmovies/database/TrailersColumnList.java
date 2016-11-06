package com.example.guillermo.popularmovies.database;

import net.simonvt.schematic.annotation.ConflictResolutionType;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.PrimaryKey;
import net.simonvt.schematic.annotation.References;

/**
 * Created by guillermo on 10/24/16.
 */

public interface TrailersColumnList {

    @DataType(DataType.Type.TEXT) @PrimaryKey(onConflict = ConflictResolutionType.REPLACE)
    //@AutoIncrement
    String _ID = "trailer_id";

    @DataType(DataType.Type.TEXT)
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
