package com.example.guillermo.popularmovies;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.guillermo.popularmovies.fragments.MainGridFragment;
import com.facebook.stetho.DumperPluginsProvider;
import com.facebook.stetho.Stetho;
import com.facebook.stetho.dumpapp.DumperPlugin;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(savedInstanceState==null){
            getFragmentManager().beginTransaction().add(R.id.main_id,new MainGridFragment()).commit();
        }
        Stetho.initialize(Stetho.newInitializerBuilder(this)
                .enableDumpapp(new PluginsProvider(this))
                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                .build());

        /*new OkHttpClient.Builder().addInterceptor(new StethoInterceptor()).build();*/
    }

    private class PluginsProvider implements DumperPluginsProvider{

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
    }
}
