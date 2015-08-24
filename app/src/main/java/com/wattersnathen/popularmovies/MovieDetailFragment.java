package com.wattersnathen.popularmovies;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wattersnathen.popularmovies.model.Movie;

public class MovieDetailFragment extends Fragment {


    public MovieDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Intent intent = getActivity().getIntent();
        View rootView = inflater.inflate(R.layout.fragment_movie_detail, container, false);

        if (intent != null && intent.hasExtra("movie_detail")) {
            Movie movie = intent.getParcelableExtra("movie_detail");
            ((TextView) rootView.findViewById(R.id.testing_textView))
                    .setText(movie.getTitle());
        }

        return rootView;
    }

}
