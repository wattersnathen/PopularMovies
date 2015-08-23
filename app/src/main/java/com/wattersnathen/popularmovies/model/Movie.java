package com.wattersnathen.popularmovies.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Nathen on 8/22/2015.
 */
public class Movie implements Parcelable {
    private String mTitle;
    private Boolean mAdult;
    private String mBackdropPath;
    private List<Integer> mGenreIds;
    private Long mId;
    private String mOriginalLanguage;
    private String mOriginalTitle;
    private String mOverview;
    private String mReleaseDate;
    private String mPosterPath;
    private Double mPopularity;
    private Boolean mVideo;
    private Double mVoteAverage;
    private Integer mVoteCount;

    public Movie() {}

    public Movie(Parcel in) {
        mTitle = in.readString();
        mAdult = Boolean.valueOf(in.readByte() == (byte) 1);
        mBackdropPath = in.readString();
        mGenreIds = in.readArrayList(ClassLoader.getSystemClassLoader());
        mId = in.readLong();
        mOriginalLanguage = in.readString();
        mOriginalTitle = in.readString();
        mOverview = in.readString();
        mReleaseDate = in.readString();
        mPosterPath = in.readString();
        mPopularity = in.readDouble();
        mVideo = Boolean.valueOf(in.readByte() == (byte) 1);
        mVoteAverage = in.readDouble();
        mVoteCount = in.readInt();
    }

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mTitle);
        dest.writeByte((byte) (mAdult ? 1 : 0));
        dest.writeString(mBackdropPath);
        dest.writeList(mGenreIds);
        dest.writeLong(mId);
        dest.writeString(mOriginalLanguage);
        dest.writeString(mOriginalTitle);
        dest.writeString(mOverview);
        dest.writeString(mReleaseDate);
        dest.writeString(mPosterPath);
        dest.writeDouble(mPopularity);
        dest.writeByte((byte) (mVideo ? 1 : 0));
        dest.writeDouble(mVoteAverage);
        dest.writeInt(mVoteCount);
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public Boolean getAdult() {
        return mAdult;
    }

    public void setAdult(Boolean adult) {
        mAdult = adult;
    }

    public String getBackdropPath() {
        return mBackdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        mBackdropPath = backdropPath;
    }

    public List<Integer> getGenreIds() {
        return mGenreIds;
    }

    public void setGenreIds(List<Integer> genreIds) {
        mGenreIds = genreIds;
    }

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

    public String getOriginalLanguage() {
        return mOriginalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        mOriginalLanguage = originalLanguage;
    }

    public String getOriginalTitle() {
        return mOriginalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        mOriginalTitle = originalTitle;
    }

    public String getOverview() {
        return mOverview;
    }

    public void setOverview(String overview) {
        mOverview = overview;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        mReleaseDate = releaseDate;
    }

    public String getPosterPath() {
        return mPosterPath;
    }

    public void setPosterPath(String posterPath) {
        mPosterPath = posterPath;
    }

    public Double getPopularity() {
        return mPopularity;
    }

    public void setPopularity(Double popularity) {
        mPopularity = popularity;
    }

    public Boolean getVideo() {
        return mVideo;
    }

    public void setVideo(Boolean video) {
        mVideo = video;
    }

    public Double getVoteAverage() {
        return mVoteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        mVoteAverage = voteAverage;
    }

    public Integer getVoteCount() {
        return mVoteCount;
    }

    public void setVoteCount(Integer voteCount) {
        mVoteCount = voteCount;
    }
}
