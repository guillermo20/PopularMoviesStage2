package com.example.guillermo.popularmovies.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by guillermo on 10/29/16.
 */

public class MoviesAuthenticatorService extends Service{

    private MoviesAuthenticator moviesAuthenticator;

    @Override
    public void onCreate() {
        moviesAuthenticator = new MoviesAuthenticator(this);
    }

    // NOTE to self when overriding this method implementing Services erease the nullable annotation cuz it screws up the initialization
    @Override
    public IBinder onBind(Intent intent) {
        return moviesAuthenticator.getIBinder();
    }
}
