package com.sn.themoviedbandroidtask.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Saad Nayyer on 5/9/2018.
 */

public class NowPlayingResponse<T> {
    @SerializedName("results")
    @Expose
    private ArrayList<T> results = new ArrayList<>();
    @SerializedName("page")
    @Expose
    private int page;
    @SerializedName("total_results")
    @Expose
    private int totalResults;
    @SerializedName("total_pages")
    @Expose
    private int totalPages;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public ArrayList<T> getResults() {
        return results;
    }

    public void setResults(ArrayList<T> results) {
        this.results = results;
    }
}
