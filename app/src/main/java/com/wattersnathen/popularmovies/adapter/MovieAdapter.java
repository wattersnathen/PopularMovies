package com.wattersnathen.popularmovies.adapter;

/**
 * Created by Nathen on 8/22/2015.
 */
public class MovieAdapter {

        Picasso.with(mContext)
                .load(imageUrl)
                .error(R.mipmap.ic_launcher)
                .into(holder.mImageView);

}
