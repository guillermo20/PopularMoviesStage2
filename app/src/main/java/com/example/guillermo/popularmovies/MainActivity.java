package com.example.guillermo.popularmovies;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.guillermo.popularmovies.fragments.MainGridFragment;
import com.example.guillermo.popularmovies.fragments.MovieDetailsFragment;
import com.example.guillermo.popularmovies.model.MovieItem;

;

public class MainActivity extends AppCompatActivity implements MainGridFragment.Callback{

    private boolean mTwoPane;

    private String DETAILFRAGMENT_TAG = "DFRAGMENT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(findViewById(R.id.movie_details_fragment) != null){
            mTwoPane = true;
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.movie_details_fragment, new MovieDetailsFragment(), DETAILFRAGMENT_TAG)
                    .commit();
        }
        else {
            mTwoPane = false;
            getSupportFragmentManager().beginTransaction().add(R.id.main_id,new MainGridFragment()).commit();
        }
    }

    @Override
    public void onItemSelected(Uri trailerContentUri, Uri reviewContentUri, MovieItem item) {
        if (mTwoPane) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            Bundle args = new Bundle();
            args.putParcelable(MovieDetailsFragment.TRAILER_URI, trailerContentUri);
            MovieDetailsFragment fragment = new MovieDetailsFragment(item,reviewContentUri);
            fragment.setArguments(args);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.movie_details_fragment, fragment, DETAILFRAGMENT_TAG)
                    .commit();
        } else {
            Intent intent = new Intent(this,MovieDetailActivity.class);
            intent.setData(trailerContentUri);
            intent.putExtra(MovieDetailsFragment.REVIEW_URI,reviewContentUri);
            intent.putExtra("movieItem",item);
            startActivity(intent);
        }
    }
}
