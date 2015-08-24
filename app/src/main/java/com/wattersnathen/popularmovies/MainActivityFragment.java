package com.wattersnathen.popularmovies;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.wattersnathen.popularmovies.adapter.MovieAdapter;
import com.wattersnathen.popularmovies.model.Movie;
import com.wattersnathen.popularmovies.model.MovieDBOperations;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private static final String LOG_TAG = MainActivityFragment.class.getSimpleName();

    private MovieAdapter mMovieAdapter;
    private GridView mGridView;

    public MainActivityFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceBundle) {
        super.onCreate(savedInstanceBundle);

        mMovieAdapter = new MovieAdapter(getActivity());

        if (savedInstanceBundle == null) {
            FetchMoviesTask fetchMoviesTask = new FetchMoviesTask();
            fetchMoviesTask.execute(" ");

        } else {
            ArrayList<Movie> movies = savedInstanceBundle.getParcelableArrayList("movies");
            mMovieAdapter.addAll(movies);
            mMovieAdapter.notifyDataSetChanged();
        }
    }

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

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movie movie = (Movie)mMovieAdapter.getItem(position);
                Intent intent = new Intent(getActivity(), MovieDetailActivity.class);
                intent.putExtra("movie_detail", movie);
                startActivity(intent);
            }
        });

        return rootView;
    }

    public class FetchMoviesTask extends AsyncTask<String, Void, List<Movie>> {

        @Override
        protected void onPostExecute(List<Movie> results) {
            if (results != null) {

                mMovieAdapter.clear();
                mMovieAdapter.addAll(results);
                mMovieAdapter.notifyDataSetChanged();
            }
        }

        @Override
        protected List<Movie> doInBackground(String... params) {
            if (params.length == 0) {
                return null;
            }



            HttpURLConnection urlConnection = null;
            BufferedReader bufferedReader = null;

            String moviesJsonString = null;

            try {

                Uri builtUri = Uri.parse(MovieDBOperations.BASE_DISCOVERY_URL).buildUpon()
                        .appendQueryParameter(MovieDBOperations.SORT, MovieDBOperations.SORT_BY_POPULARITY)
                        .appendQueryParameter(MovieDBOperations.API_KEY, MovieDBOperations.API_KEY_VALUE)
                        .build();

                URL url = new URL(builtUri.toString());

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer stringBuffer = new StringBuffer();

                if (inputStream == null) {
                    return null; // nothing to do
                }

                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    stringBuffer.append(line + "\n");
                }

                if (stringBuffer.length() == 0) {
                    return null;
                }

                moviesJsonString = stringBuffer.toString();

            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);
                return null;

            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }

                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }

            try {
                return getMovieDataFromJson(moviesJsonString);
            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }

            return null;
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
