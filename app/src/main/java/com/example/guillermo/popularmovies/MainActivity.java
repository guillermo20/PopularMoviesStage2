package com.example.guillermo.popularmovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

;import com.example.guillermo.popularmovies.fragments.MainGridFragment;
import com.example.guillermo.popularmovies.fragments.MovieDetailsFragment;

public class MainActivity extends AppCompatActivity {

    private String DETAILFRAGMENT_TAG = "DFRAGMENT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        if(savedInstanceState==null){
//
//        }
        if(findViewById(R.id.movie_details_fragment) != null){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.movie_details_fragment, new MovieDetailsFragment(), DETAILFRAGMENT_TAG)
                    .commit();
        }
        else{
            getSupportFragmentManager().beginTransaction().add(R.id.main_id,new MainGridFragment()).commit();
        }
    }
}
