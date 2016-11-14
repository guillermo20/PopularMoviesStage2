package com.example.guillermo.popularmovies.fragments;


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
import android.widget.ArrayAdapter;

import com.example.guillermo.popularmovies.R;
import com.example.guillermo.popularmovies.enums.TrailersTableProjection;
import com.example.guillermo.popularmovies.model.MovieItem;
import com.example.guillermo.popularmovies.model.ReviewMovieInfo;

/**
 * Created by guillermo on 17/09/16.
 */
public class MovieDetailsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private final String LOG_TAG=MovieDetailsFragment.class.getSimpleName();

    private MovieItem movieItem;

    private ArrayAdapter<String> reviewAdapter;

    private Uri mUri;

    public static final String DETAIL_URI = "URI";

    private int TRAILERS_LOADER_ID = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        if (arguments != null) {
            mUri = arguments.getParcelable(this.DETAIL_URI);
            Log.i(LOG_TAG,"************** Uri passed = "+mUri+" ************");
        }


        View rootView = inflater.inflate(R.layout.movie_details_fragment,container,false);
//        TextView textViewTitle = (TextView) rootView.findViewById(R.id.movie_details_title);
//        TextView textViewReleaseDate = (TextView) rootView.findViewById(R.id.movie_details_release_date);
//        TextView textViewVoteAverage = (TextView) rootView.findViewById(R.id.movie_details_vote_avg);
//        TextView textViewSynopsis = (TextView) rootView.findViewById(R.id.movie_details_synopsis);
//        textViewTitle.setText("Title: "+movieItem.getTitle());
//        textViewReleaseDate.setText("Release date: "+movieItem.getReleaseDate());
//        textViewVoteAverage.setText("Vote: "+movieItem.getVoteAverage());
//        textViewSynopsis.setText("Synopsis: "+movieItem.getOverview());
//        ImageView imageView = (ImageView) rootView.findViewById(R.id.image_thumbnail);
//        Picasso.with(getActivity()).load(movieItem.getBackdropUri(MovieItem.IMAGE_SIZE_W500)).error(R.drawable.error).into(imageView);
//        ImageView videoImageView = (ImageView) rootView.findViewById(R.id.video_intent);
//        if (!movieItem.getVideos().isEmpty()){
//            Log.i(LOG_TAG,"the movie has videos!!");
//            videoImageView.setVisibility(View.VISIBLE);
//            videoImageView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Log.i(LOG_TAG,"the video intent must be called");
//
//                    String youtubeUrl = "https://www.youtube.com/watch?v="+movieItem.getVideos().get(0).getKey();
//                    Intent intent = new Intent(Intent.ACTION_VIEW);
//                    intent.setData(Uri.parse(youtubeUrl));
//                    startActivity(intent);
//                }
//            });
//        }
//
//        String[] result = returnReviews(movieItem);
//        if(result!=null){
//            List<String> data= new ArrayList<String>(Arrays.asList(result));
//            reviewAdapter = new ArrayAdapter<String>(getActivity(),R.layout.review_item,R.id.review_textview,data);
//            ListView reviewsListview = (ListView) rootView.findViewById(R.id.reviews_list_view_id);
//            reviewsListview.setAdapter(reviewAdapter);
//        }

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
        getLoaderManager().initLoader(TRAILERS_LOADER_ID,null,this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
//        Uri contentUri = PopularMoviesProvider.Trailers.CONTENT_URI;
//        String selection = TrailersColumnList.MOVIE_ID + "=?";
//        String[] selectionArgs = { String.valueOf(movieItem.getMovieId())};
        CursorLoader dataCursor = new CursorLoader(getActivity(),
                mUri,
                null,
                null,
                null,
                null);
        return dataCursor;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Log.i(LOG_TAG,"*************** init onLoadFinished *********** ");
        if (data.moveToFirst()){
            do {
                Log.i(LOG_TAG," data = "+data.getString(TrailersTableProjection.NAME.getCode())+ " key ="+data.getString(TrailersTableProjection.KEY.getCode()));
            }while (data.moveToNext());
        }
        Log.i(LOG_TAG,"*************** end  onLoadFinished *********** ");
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        Log.i(LOG_TAG,"************** onLoaderReset   ***********");
    }
}
