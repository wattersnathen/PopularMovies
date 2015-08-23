package com.wattersnathen.popularmovies.adapter;

import android.content.Context;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nathen on 8/22/2015.
 */
public class MovieAdapter extends BaseAdapter {

    private static final String LOG_TAG = MovieAdapter.class.getSimpleName();

    private Context mContext;
    private List<Movie> mMovieList;
    private LayoutInflater inflater;

    public MovieAdapter(Context context) {
        this(context, new ArrayList<Movie>());
    }

    public MovieAdapter(Context context, List<Movie> movies) {
        mContext = context;
        mMovieList = movies;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void addAll(List<Movie> movies) {
        if (mMovieList != null) {
            mMovieList.addAll(movies);
        }
    }

    public void clear() {
        if (mMovieList != null) {
            mMovieList.clear();
        }
    }

    public List<Movie> getMovieList() {
        return (mMovieList != null && mMovieList.size() > 0) ? mMovieList : null;
    }

    @Override
    public int getCount() {
        return (mMovieList == null) ? 0 : mMovieList.size();
    }

    @Override
    public Object getItem(int position) {
        return (mMovieList == null) ? null : mMovieList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return Long.valueOf(mMovieList.get(position).getId().toString());
    }

        Picasso.with(mContext)
                .load(imageUrl)
                .error(R.mipmap.ic_launcher)
                .into(holder.mImageView);

}
