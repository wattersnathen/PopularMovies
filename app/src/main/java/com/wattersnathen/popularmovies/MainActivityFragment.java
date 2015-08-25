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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.wattersnathen.popularmovies.adapter.MovieAdapter;
import com.wattersnathen.popularmovies.model.Movie;

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

    private boolean mErrorOccurred;
    private String mPreviousSearch = "";

    public MainActivityFragment() {}

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.sort_by_rating:
                if (!mPreviousSearch.equals("vote_average.desc")) {
                    new FetchMoviesTask().execute("vote_average.desc");
                    mPreviousSearch = "vote_average.desc";
                }
                return true;
            case R.id.sort_by_popularity:
                if (!mPreviousSearch.equals("popularity.desc")) {
                    new FetchMoviesTask().execute("popularity.desc");
                    mPreviousSearch = "popularity.desc";
                }
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreate(Bundle savedInstanceBundle) {
        super.onCreate(savedInstanceBundle);
        setHasOptionsMenu(true);

        mMovieAdapter = new MovieAdapter(getActivity());

        if (savedInstanceBundle == null) {
            executeFetchMoviesTask(getSortPreference());

        } else {
            ArrayList<Movie> movies = savedInstanceBundle
                    .getParcelableArrayList(getString(R.string.parceable_list_label));
            mMovieAdapter.addAll(movies);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(getString(R.string.parceable_list_label),
                (ArrayList<Movie>) mMovieAdapter.getMovieList());
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

            if (mErrorOccurred) {
                Toast.makeText(
                        getActivity(),
                        R.string.network_request_error_message,
                        Toast.LENGTH_LONG)
                        .show();
            }

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

            String searchSortRequest = params[0];

            HttpURLConnection urlConnection = null;
            BufferedReader bufferedReader = null;

            String moviesJsonString = null;

            try {

                Uri builtUri = Uri.parse(MovieDBOperations.BASE_DISCOVERY_URL).buildUpon()
                        .appendQueryParameter(MovieDBOperations.SORT, searchSortRequest)
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
                mErrorOccurred = true;
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
            JSONArray results = resultString.getJSONArray(getString(R.string.api_results));

            List<Movie> movies = new ArrayList<>();
            for (int index = 0; index < results.length(); index++) {
                Movie movie = new Movie();
                JSONObject movieObject = results.getJSONObject(index);

                movie.setAdult(movieObject.getBoolean(getString(R.string.api_adult)));
                movie.setBackdropPath(movieObject.getString(getString(R.string.api_backdrop_path)));
                movie.setId(movieObject.getLong(getString(R.string.api_id)));
                movie.setOriginalLanguage(movieObject.getString(getString(R.string.api_original_language)));
                movie.setOriginalTitle(movieObject.getString(getString(R.string.api_original_title)));
                movie.setOverview(movieObject.getString(getString(R.string.api_overview)));
                movie.setReleaseDate(movieObject.getString(getString(R.string.api_release_date)));
                movie.setPosterPath(movieObject.getString(getString(R.string.api_poster_path)));
                movie.setPopularity(movieObject.getDouble(getString(R.string.api_popularity)));
                movie.setTitle(movieObject.getString(getString(R.string.api_title)));
                movie.setVideo(movieObject.getBoolean(getString(R.string.api_video)));
                movie.setVoteAverage(movieObject.getDouble(getString(R.string.api_vote_average)));
                movie.setVoteCount(movieObject.getInt(getString(R.string.api_vote_count)));

                movies.add(movie);
            }

            return movies;
        }
    }
}
