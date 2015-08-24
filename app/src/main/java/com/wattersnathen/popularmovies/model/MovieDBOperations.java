package com.wattersnathen.popularmovies.model;

import com.wattersnathen.popularmovies.R;

/**
 * Created by Nathen on 8/22/2015.
 */
public final class MovieDBOperations {
    private MovieDBOperations() {}

    // Supported base URLs
    public static final String BASE_URL = "http://api.themoviedb.org/3/";
    public static final String BASE_DISCOVERY_URL = BASE_URL + "discover/movie?";
    public static final String BASE_MOVIE_URL = BASE_URL + "movie/";

    // API key query information
    public static final String API_KEY = "api_key";
    public static final String API_KEY_VALUE = "ENTER YOUR KEY HERE";

    // Supported Sorting operations
    public static final String SORT = "sort_by";
    public static final String SORT_BY_POPULARITY = "popularity.desc";
    public static final String SORT_BY_AVERAGE_VOTE = "vote_average.desc";
    public static final String SORT_BY_VOTE_COUNT = "vote_count.desc";

    // Supported Image operations
    public static final String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/";
    public static final String IMAGE_SIZE = "w185";
    public static final String BACKDROP_IMAGE_SIZE = "w300";
    public static final String BACKDROP_LARGE_IMAGE_SIZE = "w780";

    // JSON result Identifiers
    public static final String RESULTS = "results";
    public static final String ADULT = "adult";
    public static final String BACKDROP_PATH = "backdrop_path";
    public static final String GENRE_IDS = "genre_ids";
    public static final String ID = "id";
    public static final String ORIGINAL_LANGUAGE = "original_language";
    public static final String ORIGINAL_TITLE = "original_title";
    public static final String OVERVIEW = "overview";
    public static final String RELEASE_DATE = "release_date";
    public static final String POSTER_PATH = "poster_path";
    public static final String POPULARITY = "popularity";
    public static final String TITLE = "title";
    public static final String VIDEO = "video";
    public static final String VOTE_AVERAGE = "vote_average";
    public static final String VOTE_COUNT = "vote_count";

}
