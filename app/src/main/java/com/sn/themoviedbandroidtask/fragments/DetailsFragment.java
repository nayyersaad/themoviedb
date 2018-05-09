package com.sn.themoviedbandroidtask.fragments;

import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sn.themoviedbandroidtask.R;
import com.sn.themoviedbandroidtask.activities.MainActivity;
import com.sn.themoviedbandroidtask.databinding.LayoutMovieDetailBinding;
import com.sn.themoviedbandroidtask.helpers.apphelpers.ApiConstants;
import com.sn.themoviedbandroidtask.helpers.apphelpers.UIHelpers;
import com.sn.themoviedbandroidtask.models.Movie;

/**
 * Created by Saad Nayyer on 5/9/2018.
 */

public class DetailsFragment extends DialogFragment {
    MainActivity activityContext;
    LayoutMovieDetailBinding binding;
    Movie movie;

    public static DetailsFragment newInstance(MainActivity activityContext, Movie movie){
        return new DetailsFragment(activityContext, movie);
    }

    public DetailsFragment(){}

    @SuppressLint("ValidFragment")
    public DetailsFragment(MainActivity activityContext, Movie movie){
        this.activityContext = activityContext;
        this.movie = movie;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_movie_detail, container, false);

        if(movie != null){
            UIHelpers.setImageWithGlide(activityContext, binding.ivMovieBackDrop, ApiConstants.POSTER_BASE_URL + movie.getBackdropPath());
            UIHelpers.setImageWithGlide(activityContext, binding.ivMoviePoster, ApiConstants.POSTER_BASE_URL + movie.getPosterPath());

            binding.tvMovieName.setText(movie.getTitle().trim());
            binding.tvDescription.setText(movie.getOverview().trim());
            binding.tvReleaseDate.setText(activityContext.getResources().getString(R.string.release_date_colon) + " " + movie.getReleaseDate());
        }

        return binding.getRoot();
    }
}
