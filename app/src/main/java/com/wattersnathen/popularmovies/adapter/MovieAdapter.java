package com.wattersnathen.popularmovies.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.wattersnathen.popularmovies.R;
import com.wattersnathen.popularmovies.model.Movie;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nathen on 8/22/2015.
 */
public class MovieAdapter extends BaseAdapter {

    private static final String LOG_TAG = MovieAdapter.class.getSimpleName();

    private Context mContext;
    private List<Movie> mMovieList;

    private String mBaseUrl = "";

    public MovieAdapter(Context context) {
        this(context, new ArrayList<Movie>());
    }

    public MovieAdapter(Context context, List<Movie> movies) {
        mContext = context;
        mMovieList = movies;
        mBaseUrl = mContext.getString(R.string.image_base_url)
                + mContext.getString(R.string.thumbnail_image_size);
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Movie movie = (Movie) getItem(position);

        ViewHolder holder;

        if (convertView == null) {
            convertView = ((Activity) mContext).getLayoutInflater().inflate(R.layout.movie_grid_item, parent, false);
            holder = new ViewHolder();
            holder.mImageView = (ImageView) convertView.findViewById(R.id.movie_grid_item_image);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        String prevUrl = mBaseUrl;
        mBaseUrl = mBaseUrl + movie.getPosterPath();

        Picasso.with(mContext)
                .load(mBaseUrl)
                .error(R.mipmap.ic_launcher)
                .into(holder.mImageView);

        mBaseUrl = prevUrl;

        return convertView;
    }

    class ViewHolder {
        ImageView mImageView;
    }
}
