package com.example.guillermo.popularmovies.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;

import com.example.guillermo.popularmovies.R;

/**
 * Created by guillermo on 11/18/16.
 */

public class TrailersAdapter extends CursorAdapter {

    private Context context;

    private  String LOG_TAG = TrailersAdapter.class.getSimpleName();

    public TrailersAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        this.context = context;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        //LayoutInflater cursorInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ImageView imageView = (ImageView) LayoutInflater.from(context).inflate(R.layout.trailer_item, parent, false);
        return imageView;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ImageView imageView = (ImageView) view;
        imageView.setVisibility(View.VISIBLE);
        //Log.i(LOG_TAG, "***** bindview trailer key = "+cursor.getString(TrailersTableProjection.KEY.getCode()));
        /*//if(cursor.moveToFirst()){
            //do {
                String site = cursor.getString(TrailersTableProjection.SITE.getCode());
                final String key = cursor.getString(TrailersTableProjection.KEY.getCode());
                final Context context1 = this.context;
                if (site.equalsIgnoreCase("youtube")){
                    imageView.setVisibility(View.VISIBLE);
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String youtubeUrl = "https://www.youtube.com/watch?v="+key;
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setData(Uri.parse(youtubeUrl));
                            context1.startActivity(intent);
                        }
                    });
                }
            //}while (cursor.moveToNext());
        //}*/
    }
}
