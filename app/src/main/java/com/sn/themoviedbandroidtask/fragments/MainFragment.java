package com.sn.themoviedbandroidtask.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.sn.themoviedbandroidtask.activities.MainActivity;

/**
 * Created by Saad Nayyer on 5/9/2018.
 */

public abstract class MainFragment extends Fragment {
    protected MainActivity activityContext;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activityContext = (MainActivity) context;
    }
}
