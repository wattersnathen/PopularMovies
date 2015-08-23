package com.wattersnathen.popularmovies;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    public MainActivityFragment() {

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("movies", (ArrayList<Movie>) mMovieAdapter.getMovieList());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        mGridView = (GridView) rootView.findViewById(R.id.movies_gridView);
        mGridView.setAdapter(mMovieAdapter);

        return rootView;
    }
        private List<Movie> getMovieDataFromJson(String movieJsonString) throws JSONException {

            JSONObject resultString = new JSONObject(movieJsonString);
            JSONArray results = resultString.getJSONArray(MovieDBOperations.RESULTS);

            List<Movie> movies = new ArrayList<>();
            for (int index = 0; index < results.length(); index++) {
                Movie movie = new Movie();
                JSONObject movieObject = results.getJSONObject(index);

                movie.setAdult(movieObject.getBoolean(MovieDBOperations.ADULT));
                movie.setBackdropPath(movieObject.getString(MovieDBOperations.BACKDROP_PATH));
                movie.setId(movieObject.getLong(MovieDBOperations.ID));
                movie.setOriginalLanguage(movieObject.getString(MovieDBOperations.ORIGINAL_LANGUAGE));
                movie.setOriginalTitle(movieObject.getString(MovieDBOperations.ORIGINAL_TITLE));
                movie.setOverview(movieObject.getString(MovieDBOperations.OVERVIEW));
                movie.setReleaseDate(movieObject.getString(MovieDBOperations.RELEASE_DATE));
                movie.setPosterPath(movieObject.getString(MovieDBOperations.POSTER_PATH));
                movie.setPopularity(movieObject.getDouble(MovieDBOperations.POPULARITY));
                movie.setTitle(movieObject.getString(MovieDBOperations.TITLE));
                movie.setVideo(movieObject.getBoolean(MovieDBOperations.VIDEO));
                movie.setVoteAverage(movieObject.getDouble(MovieDBOperations.VOTE_AVERAGE));
                movie.setVoteCount(movieObject.getInt(MovieDBOperations.VOTE_COUNT));

                movies.add(movie);
            }

            return movies;
        }
    }
}
