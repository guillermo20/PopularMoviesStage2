package com.example.guillermo.popularmovies.fragments;


import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.guillermo.popularmovies.R;
import com.example.guillermo.popularmovies.adapters.ReviewsAdapter;
import com.example.guillermo.popularmovies.adapters.TrailersAdapter;
import com.example.guillermo.popularmovies.database.MoviesColumnList;
import com.example.guillermo.popularmovies.database.PopularMoviesProvider;
import com.example.guillermo.popularmovies.enums.SortingMethod;
import com.example.guillermo.popularmovies.enums.TrailersTableProjection;
import com.example.guillermo.popularmovies.loaders.ReviewsLoader;
import com.example.guillermo.popularmovies.model.MovieItem;
import com.example.guillermo.popularmovies.model.ReviewMovieInfo;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.text.DateFormat;
import java.util.Date;

import static android.R.attr.data;
import static com.example.guillermo.popularmovies.R.id.trailers_list_view_id;

/**
 * Created by guillermo on 17/09/16.
 */
public class MovieDetailsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private final String LOG_TAG=MovieDetailsFragment.class.getSimpleName();

    private MovieItem mMovieItem;

    private Uri mUri;

    private Uri mUriReviews;

    public static final String TRAILER_URI = "TRAILER_URI";

    public static final String REVIEW_URI = "REVIEW_URI";

    public static int TRAILERS_LOADER_ID = 1;

    public static int REVIEW_LOADER_ID = 2;

    private TextView textViewTitle;
    private TextView textViewReleaseDate;
    private TextView textViewVoteAverage;
    private TextView textViewSynopsis;
    private Button favoriteButton;
    private ImageView posterImageview;
    private ImageView videoImageView;
    private TrailersAdapter trailersAdapter;
    private ReviewsAdapter reviewsAdapter;

    public MovieDetailsFragment() {
    }

    public MovieDetailsFragment(MovieItem mMovieItem, Uri mUriReviews) {
        this.mMovieItem = mMovieItem;
        this.mUriReviews = mUriReviews;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        trailersAdapter = new TrailersAdapter(getActivity(),null,0);
        reviewsAdapter = new ReviewsAdapter(getActivity(),null,0);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        if (arguments != null) {
            mUri = arguments.getParcelable(this.TRAILER_URI);
            Log.i(LOG_TAG,"************** Uri passed = "+mUri+" ************");
        }
        if (mMovieItem==null)
            mMovieItem = (MovieItem) getActivity().getIntent().getSerializableExtra("movieItem");
        if (mUriReviews == null)
            mUriReviews = getActivity().getIntent().getParcelableExtra(MovieDetailsFragment.REVIEW_URI);



        View rootView = inflater.inflate(R.layout.movie_details_fragment,container,false);
        textViewTitle = (TextView) rootView.findViewById(R.id.movie_details_title);
        textViewReleaseDate = (TextView) rootView.findViewById(R.id.movie_details_release_date);
        textViewVoteAverage = (TextView) rootView.findViewById(R.id.movie_details_vote_avg);
        textViewSynopsis = (TextView) rootView.findViewById(R.id.movie_details_synopsis);
        posterImageview = (ImageView) rootView.findViewById(R.id.image_thumbnail);
        videoImageView = (ImageView) rootView.findViewById(R.id.video_intent);
        favoriteButton = (Button) rootView.findViewById(R.id.movie_details_favorite_button);
        ListView listTrailersView = (ListView) rootView.findViewById(R.id.trailers_list_view_id);
        listTrailersView.setAdapter(trailersAdapter);
        listTrailersView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = (Cursor) parent.getItemAtPosition(position);
                Log.i(LOG_TAG, "***** item clicked key = "+cursor.getString(TrailersTableProjection.KEY.getCode()));
                String site = cursor.getString(TrailersTableProjection.SITE.getCode());
                String key = cursor.getString(TrailersTableProjection.KEY.getCode());
                if (site.equalsIgnoreCase("youtube")){
                    String youtubeUrl = "https://www.youtube.com/watch?v="+key;
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(youtubeUrl));
                    getActivity().startActivity(intent);
                }
            }
        });
        ListView listReviewView = (ListView) rootView.findViewById(R.id.reviews_list_view_id);
        listReviewView.setAdapter(reviewsAdapter);
        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(LOG_TAG,"**button clicked**");
                ContentValues movieContentValues = new ContentValues();
                movieContentValues.put(MoviesColumnList.SORT_TYPE_FAVORITES, SortingMethod.FAVORITES_MOVIES_SORT.getCode());
                String where = MoviesColumnList.MOVIE_ID + "= ?";
                String[] values = new String[]{mMovieItem.getMovieId()};
                getActivity().getContentResolver().update(PopularMoviesProvider.Movies.CONTENT_URI,
                        movieContentValues,
                        where,
                        values);
                Toast.makeText(getActivity(),"Added to favorites",Toast.LENGTH_SHORT).show();
            }

        });

        return rootView;
    }

    private String[] returnReviews(MovieItem movieItem){
        int i=0;
        if(!movieItem.getReviews().isEmpty()){
            String result[] = new String[movieItem.getReviews().size()];
            for (ReviewMovieInfo review: movieItem.getReviews()) {
                result[i]=review.getContent();
                i++;
            }
            return result;
        }
        return null;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        if (mMovieItem!=null){
            getLoaderManager().initLoader(TRAILERS_LOADER_ID,null,this);
            getLoaderManager().initLoader(REVIEW_LOADER_ID,null,new ReviewsLoader(mUriReviews,getActivity(),reviewsAdapter));
        }
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if (mUri != null){
            CursorLoader dataCursor = new CursorLoader(getActivity(),
                    mUri,
                    null,
                    null,
                    null,
                    null);
            return dataCursor;
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        if(mMovieItem!=null){
            trailersAdapter.swapCursor(data);
            String date = mMovieItem.getReleaseDate().substring(8,10)+"-"+mMovieItem.getReleaseDate().substring(5,7)+"-"+mMovieItem.getReleaseDate().substring(0,4);
            String nameFile = mMovieItem.getPosterPath().replace("/","");
            textViewTitle.setText(mMovieItem.getTitle());
            textViewReleaseDate.setText(date);
            textViewVoteAverage.setText(mMovieItem.getVoteAverage() + "/10");
            textViewSynopsis.setText(mMovieItem.getOverview());
            File file = getActivity().getFileStreamPath(nameFile);
            Picasso.with(getActivity()).load(file).error(R.drawable.error).into(posterImageview);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        trailersAdapter.swapCursor(null);
    }
}
