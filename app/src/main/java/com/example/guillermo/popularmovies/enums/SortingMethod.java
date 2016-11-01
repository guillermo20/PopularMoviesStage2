package com.example.guillermo.popularmovies.enums;

import java.security.InvalidParameterException;

/**
 * Created by guillermo on 11/1/16.
 */

public enum SortingMethod {

    POPULAR_MOVIES_SORT("popular"),
    TOP_RATED_MOVIES_SORT("top_rated"),
    FAVORITES_MOVIES_SORT("FMSS");


    private String code;

    SortingMethod(String code) {
        this.code = code;
    }

    public static SortingMethod getByCode(String code) throws InvalidParameterException {

        for (SortingMethod vault : SortingMethod.values()) {
            if (vault.getCode().equals(code))
                return vault;
        }
        throw new InvalidParameterException();
    }

    public String getCode() {
        return this.code;
    }

    public static boolean codeExists(String code) {
        try {
            getByCode(code);
            return true;
        } catch (InvalidParameterException e) {
            return false;
        }
    }

}