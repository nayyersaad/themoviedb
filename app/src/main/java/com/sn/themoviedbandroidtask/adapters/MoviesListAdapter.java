package com.sn.themoviedbandroidtask.adapters;

import android.databinding.DataBindingUtil;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sn.themoviedbandroidtask.R;
import com.sn.themoviedbandroidtask.activities.MainActivity;
import com.sn.themoviedbandroidtask.databinding.LayoutMovieItemBinding;
import com.sn.themoviedbandroidtask.fragments.DetailsFragment;
import com.sn.themoviedbandroidtask.helpers.apphelpers.ApiConstants;
import com.sn.themoviedbandroidtask.helpers.apphelpers.UIHelpers;
import com.sn.themoviedbandroidtask.models.Movie;

import java.util.ArrayList;

/**
 * Created by Saad Nayyer on 5/9/2018.
 */

public class MoviesListAdapter extends RecyclerView.Adapter<MoviesListAdapter.Holder>{
    LayoutMovieItemBinding binding;
    MainActivity activityContext;
    ArrayList<Movie> movies;

    public MoviesListAdapter(MainActivity activityContext, ArrayList<Movie> movies){
        this.activityContext = activityContext;
        this.movies = movies;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(activityContext), R.layout.layout_movie_item, parent, false);
        return new Holder(binding);
    }

    @Override
    public void onBindViewHolder(Holder holder, final int position) {
        UIHelpers.setImageWithGlide(activityContext, binding.ivMoviePoster, ApiConstants.POSTER_BASE_URL + movies.get(position).getPosterPath());
        holder.binding.tvMovieName.setText(movies.get(position).getTitle());
        holder.binding.tvDescription.setText(movies.get(position).getOverview());
        holder.binding.tvReleaseDate.setText(activityContext.getResources().getString(R.string.release_date_colon) + " " + movies.get(position).getReleaseDate());

        holder.binding.llMovieItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetailsFragment detailsFragment = new DetailsFragment(activityContext, movies.get(position));
                activityContext
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .add(activityContext.getMainFrameLayoutId(), detailsFragment, DetailsFragment.class.getSimpleName())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        if(movies != null){
            return movies.size();
        }else {
            return 0;
        }
    }

    public void addAllMovies(ArrayList<Movie> movies){
        this.movies.clear();
        this.movies.addAll(movies);
    }

    public void addMovies(ArrayList<Movie> movies){
        this.movies.addAll(movies);
    }

    public static class Holder extends RecyclerView.ViewHolder {
        LayoutMovieItemBinding binding;

        Holder(LayoutMovieItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public Holder(View itemView) {
            super(itemView);
        }
    }
}
