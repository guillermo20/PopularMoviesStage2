package com.example.guillermo.popularmovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.guillermo.popularmovies.fragments.MovieDetailsFragment;

public class MovieDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        if (savedInstanceState == null){

            Bundle arguments = new Bundle();
            arguments.putParcelable(MovieDetailsFragment.TRAILER_URI, getIntent().getData());

            MovieDetailsFragment fragment = new MovieDetailsFragment();
            fragment.setArguments(arguments);

            getSupportFragmentManager().beginTransaction().add(R.id.activity_movie_detail_id,fragment).commit();
        }
    }

}
