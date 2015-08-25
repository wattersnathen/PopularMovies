package com.wattersnathen.popularmovies;

import android.content.Intent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wattersnathen.popularmovies.model.Movie;

public class MovieDetailFragment extends Fragment {

    private ImageView mBackdropImageView;
    private ImageView mThumbnailImageView;
    private TextView mTitleTextView;
    private TextView mReleaseDateTextView;
    private TextView mRatingsTextView;
    private TextView mSynopsisTextView;

    private String mImageUrl = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImageUrl = getString(R.string.image_base_url) + "/"
                + getString(R.string.backdrop_image_size) + "/";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Intent intent = getActivity().getIntent();
        View rootView = inflater.inflate(R.layout.fragment_movie_detail, container, false);

        if (intent != null && intent.hasExtra("movie_detail")) {
            Movie movie = intent.getParcelableExtra("movie_detail");

            getActivity().setTitle(movie.getTitle());

            mBackdropImageView = (ImageView) rootView.findViewById(R.id.movie_detail_backdrop_image);
            mThumbnailImageView = (ImageView) rootView.findViewById(R.id.movie_detail_thumbnail);
            mTitleTextView = (TextView) rootView.findViewById(R.id.movie_detail_title);
            mRatingsTextView = (TextView) rootView.findViewById(R.id.movie_detail_rating);
            mSynopsisTextView = (TextView) rootView.findViewById(R.id.movie_detail_synopsis);
            mReleaseDateTextView = (TextView) rootView.findViewById(R.id.movie_detail_releaseDate);

            mTitleTextView.setText(movie.getTitle());
            mRatingsTextView.setText("Rated: " + String.valueOf(movie.getVoteAverage()) + " / 10.0");
            mReleaseDateTextView.setText("Released: " + movie.getReleaseDate());

            mSynopsisTextView.setText(movie.getOverview());

            Picasso.with(getActivity())
                    .load(mImageUrl + movie.getBackdropPath())
                    .error(R.mipmap.ic_launcher)
                    .into(mBackdropImageView);

            Picasso.with(getActivity())
                    .load(mImageUrl + movie.getPosterPath())
                    .error(R.mipmap.ic_launcher)
                    .into(mThumbnailImageView);
        }

        return rootView;
    }

}
