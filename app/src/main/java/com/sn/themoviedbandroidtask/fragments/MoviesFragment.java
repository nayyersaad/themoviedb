package com.sn.themoviedbandroidtask.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sn.themoviedbandroidtask.R;
import com.sn.themoviedbandroidtask.adapters.MoviesListAdapter;
import com.sn.themoviedbandroidtask.databinding.FragmentMoviesBinding;
import com.sn.themoviedbandroidtask.helpers.apphelpers.ApiConstants;
import com.sn.themoviedbandroidtask.helpers.apphelpers.DateSelect;
import com.sn.themoviedbandroidtask.helpers.apphelpers.JsonHelpers;
import com.sn.themoviedbandroidtask.helpers.apphelpers.UIHelpers;
import com.sn.themoviedbandroidtask.helpers.webhelpers.methods.WebApiRequest;
import com.sn.themoviedbandroidtask.listeners.DateSelectedListener;
import com.sn.themoviedbandroidtask.listeners.NowPlayingResponseCallback;
import com.sn.themoviedbandroidtask.models.Movie;
import com.sn.themoviedbandroidtask.models.NowPlayingResponse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

/**
 * Created by Saad Nayyer on 5/9/2018.
 */

public class MoviesFragment extends MainFragment implements View.OnClickListener{
    FragmentMoviesBinding binding;
    LinearLayoutManager linearLayoutManager;
    MoviesListAdapter adapter;
    int page = 1;
    boolean infiniteScroll = false, isSwiped = false;
    ArrayList<Movie> movies = new ArrayList<>();
    ArrayList<Movie> filteredMovies = new ArrayList<>();
    DateSelect dateSelect;

    public static MoviesFragment newInstance(){
        return new MoviesFragment();
    }

    public MoviesFragment(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movies, container, false);

        adapter = new MoviesListAdapter(activityContext, new ArrayList<Movie>());
        linearLayoutManager = new LinearLayoutManager(activityContext);
        binding.rvMovies.setLayoutManager(linearLayoutManager);
        binding.rvMovies.setAdapter(adapter);

        getMovies(false);
        setListeners();

        return binding.getRoot();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tvClear:
                getMovies(false);
                break;

            case R.id.tvFromDate:
                dateSelect = new DateSelect(new DateSelectedListener() {
                    @Override
                    public void onDateSelected(String date) {
                        binding.tvFromDate.setText(date);
                    }
                });
                dateSelect.show(activityContext.getSupportFragmentManager(), DateSelect.class.getSimpleName());
                break;

            case R.id.tvToDate:
                dateSelect = new DateSelect(new DateSelectedListener() {
                    @Override
                    public void onDateSelected(String date) {
                        binding.tvToDate.setText(date);
                        if(!(binding.tvFromDate.getText().toString().equals(activityContext.getResources().getString(R.string.from_date)))){
                            applyFilter();
                        }else{
                            binding.tvFromDate.setText(activityContext.getResources().getString(R.string.from_date));
                            binding.tvToDate.setText(activityContext.getResources().getString(R.string.to_date));
                            UIHelpers.showSnackBarShort(activityContext.getMainFrameLayout(), activityContext.getResources().getString(R.string.select_first_from_to));
                        }
                    }
                });
                dateSelect.show(activityContext.getSupportFragmentManager(), DateSelect.class.getSimpleName());
                break;
        }
    }

    void setInfiniteScroll(){
        if(infiniteScroll){
            binding.rvMovies.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    if(linearLayoutManager.findLastCompletelyVisibleItemPosition() >= linearLayoutManager.getItemCount() - 1){
                        if(filteredMovies.size() == 0){
                            page += 1;
                            getMovies(false);
                        }
                    }
                }
            });
        }
    }

    void setListeners(){
        binding.tvClear.setOnClickListener(this);
        binding.tvFromDate.setOnClickListener(this);
        binding.tvToDate.setOnClickListener(this);

        binding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                getMovies(true);
                binding.swipeRefresh.setRefreshing(false);
            }
        });
    }

    void applyFilter(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date fromDate, toDate, movieDate;
        try {
            fromDate = format.parse(binding.tvFromDate.getText().toString().trim());
            toDate = format.parse(binding.tvToDate.getText().toString().trim());
            for(int pos = 0; pos < movies.size(); pos++){
                movieDate = format.parse(movies.get(pos).getReleaseDate());
                if(movieDate.before(toDate) && movieDate.after(fromDate)){
                    Log.i("testing", movies.get(pos).getReleaseDate());
                    filteredMovies.add(movies.get(pos));
                }
            }
            adapter.addAllMovies(filteredMovies);
            adapter.notifyDataSetChanged();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    void getMovies(final boolean isSwiped){
        activityContext.loaderVisible();
        WebApiRequest.getInstance(activityContext)
                .getNowPlayingMovies(
                        ApiConstants.API_KEY,
                        ApiConstants.LANG_EN_US,
                        page,
                        new NowPlayingResponseCallback() {
                            @Override
                            public void onSuccess(NowPlayingResponse response) {
                                binding.tvFromDate.setText(activityContext.getResources().getString(R.string.from_date));
                                binding.tvToDate.setText(activityContext.getResources().getString(R.string.to_date));
                                movies = (ArrayList<Movie>) JsonHelpers.parseArray(response.getResults(), Movie.class);
                                if(movies.size() > 0){
                                    filteredMovies = new ArrayList<>();
                                    binding.rvMovies.setVisibility(View.VISIBLE);
                                    binding.tvNoData.setVisibility(View.GONE);
                                    if(isSwiped){
                                        adapter.addAllMovies(movies);
                                        adapter.notifyDataSetChanged();
                                    }else{
                                        adapter.addMovies(movies);
                                        adapter.notifyDataSetChanged();
                                    }
                                }
                                if(response.getTotalPages() > 1){
                                    infiniteScroll = true;
                                    setInfiniteScroll();
                                }
                                activityContext.loaderGone();
                            }

                            @Override
                            public void onError() {
                                binding.tvFromDate.setText(activityContext.getResources().getString(R.string.from_date));
                                binding.tvToDate.setText(activityContext.getResources().getString(R.string.to_date));
                                UIHelpers.showSnackBarShort(activityContext.getMainFrameLayout(), activityContext.getResources().getString(R.string.request_error));
                                activityContext.loaderGone();
                            }

                            @Override
                            public void onNotConnected() {
                                binding.tvFromDate.setText(activityContext.getResources().getString(R.string.from_date));
                                binding.tvToDate.setText(activityContext.getResources().getString(R.string.to_date));
                                UIHelpers.showSnackBarShort(activityContext.getMainFrameLayout(), activityContext.getResources().getString(R.string.no_internet_connection));
                                activityContext.loaderGone();
                            }
                        });
    }
}
