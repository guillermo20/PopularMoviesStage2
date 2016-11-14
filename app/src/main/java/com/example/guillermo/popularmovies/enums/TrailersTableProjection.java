package com.example.guillermo.popularmovies.enums;

import java.security.InvalidParameterException;

/**
 * Created by guillermo on 11/7/16.
 */

public enum TrailersTableProjection {
    _ID(0),
    VIDEO_ID(1),
    KEY(2),
    NAME(3),
    SITE(4),
    SIZE(5),
    TYPE(6),
    MOVIE_ID(7);

    private int code;

    TrailersTableProjection(int code){
        this.code = code;
    }
    public static TrailersTableProjection getByCode(int code) throws InvalidParameterException {

        for (TrailersTableProjection vault : TrailersTableProjection.values()) {
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
