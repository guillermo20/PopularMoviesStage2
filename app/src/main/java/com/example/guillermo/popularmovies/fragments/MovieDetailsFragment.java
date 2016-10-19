package com.example.guillermo.popularmovies.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.guillermo.popularmovies.R;
import com.example.guillermo.popularmovies.model.MovieItem;
import com.squareup.picasso.Picasso;

/**
 * Created by guillermo on 17/09/16.
 */
public class MovieDetailsFragment extends Fragment {

    private final String LOG_TAG=MovieDetailsFragment.class.getSimpleName();

    private MovieItem movieItem;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        movieItem = (MovieItem) getActivity().getIntent().getSerializableExtra(MovieItem.class.getSimpleName());
        Log.v(LOG_TAG,movieItem.getTitle());
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.movie_details_fragment,container,false);
        TextView textViewTitle = (TextView) rootView.findViewById(R.id.movie_details_title);
        TextView textViewReleaseDate = (TextView) rootView.findViewById(R.id.movie_details_release_date);
        TextView textViewVoteAverage = (TextView) rootView.findViewById(R.id.movie_details_vote_avg);
        TextView textViewSynopsis = (TextView) rootView.findViewById(R.id.movie_details_synopsis);
        textViewTitle.setText("Title: "+movieItem.getTitle());
        textViewReleaseDate.setText("Release date: "+movieItem.getReleaseDate());
        textViewVoteAverage.setText("Vote: "+movieItem.getVoteAverage());
        textViewSynopsis.setText("Synopsis: "+movieItem.getOverview());
        ImageView imageView = (ImageView) rootView.findViewById(R.id.image_thumbnail);
        Picasso.with(getActivity()).load(movieItem.getBackdropUri(MovieItem.IMAGE_SIZE_W500)).error(R.drawable.error).into(imageView);
        ImageView videoImageView = (ImageView) rootView.findViewById(R.id.video_intent);
        if (!movieItem.getVideos().isEmpty()){
            Log.i(LOG_TAG,"the movie has videos!!");
            videoImageView.setVisibility(View.VISIBLE);
            videoImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i(LOG_TAG,"the video intent must be called");

                    String youtubeUrl = "https://www.youtube.com/watch?v="+movieItem.getVideos().get(0).getKey();
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(youtubeUrl));
                    startActivity(intent);
                }
            });
        }
        return rootView;
    }
}
