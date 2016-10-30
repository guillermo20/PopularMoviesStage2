package com.example.guillermo.popularmovies;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by guillermo on 10/25/16.
 */

public class StethoDebug extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //Stetho.initializeWithDefaults(this);
        Stetho.initialize(Stetho.newInitializerBuilder(this)
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                .build());
    }
    /*private class PluginsProvider implements DumperPluginsProvider {

        private Context mContext;

        public PluginsProvider(Context mContext) {
            this.mContext = mContext;
        }

        @Override
        public Iterable<DumperPlugin> get() {
            List<DumperPlugin> plugins = new ArrayList<>();
            for (DumperPlugin defaultPlugin:Stetho.defaultDumperPluginsProvider(mContext).get()) {
                plugins.add(defaultPlugin);
            }
            return plugins;
        }
    }*/
}
