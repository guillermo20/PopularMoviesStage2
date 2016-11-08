package com.example.guillermo.popularmovies.enums;

import java.security.InvalidParameterException;

/**
 * Created by guillermo on 11/7/16.
 */

public enum MovieTableProjection {
    _ID(0),
    MOVIE_ID(1),
    TITLE(2),
    POSTERPATH(3),
    ADULT(4),
    OVERVIEW(5),
    RELEASE_DATE(6),
    ORIGINAL_TITLE(7),
    ORIGINAL_LANGUAGE(8),
    BACKDROP_PATH(9),
    POPULARITY(10),
    VOTE_COUNT(11),
    VIDEO(12),
    VOTE_AVERAGE(13),
    FAVORITE_FLAG(14),
    SORT_TYPE(15);

    private int code;

    MovieTableProjection(int code){
        this.code = code;
    }
    public static MovieTableProjection getByCode(int code) throws InvalidParameterException {

        for (MovieTableProjection vault : MovieTableProjection.values()) {
            if (vault.getCode() == code)
                return vault;
        }
        throw new InvalidParameterException();
    }

    public int getCode() {
        return this.code;
    }

    public static boolean codeExists(int code) {
        try {
            getByCode(code);
            return true;
        } catch (InvalidParameterException e) {
            return false;
        }
    }
}
