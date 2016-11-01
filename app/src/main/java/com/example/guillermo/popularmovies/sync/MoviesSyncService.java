package com.example.guillermo.popularmovies.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by guillermo on 10/29/16.
 */

public class MoviesSyncService extends Service {


    private static final Object sSyncAdapterLock = new Object();


    private static MoviesSyncAdapter mSyncAdapter = null;

    @Override
    public void onCreate() {

        Log.d("MoviesSyncService", "onCreate - MoviesSyncService");
        synchronized (sSyncAdapterLock) {
            if (mSyncAdapter == null) {
                mSyncAdapter = new MoviesSyncAdapter(getApplicationContext(), true);
            }
        }
    }

    // NOTE to self when overriding this method implementing Services erease the nullable annotation cuz it mess up the initialization
    @Override
    public IBinder onBind(Intent intent) {
        return mSyncAdapter.getSyncAdapterBinder();
    }
}
