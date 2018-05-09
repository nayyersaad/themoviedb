package com.sn.themoviedbandroidtask.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Movie;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.sn.themoviedbandroidtask.R;
import com.sn.themoviedbandroidtask.databinding.ActivityMainBinding;
import com.sn.themoviedbandroidtask.fragments.MainFragment;
import com.sn.themoviedbandroidtask.fragments.MoviesFragment;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    MainFragment mainFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        addFragment(MoviesFragment.newInstance(), MoviesFragment.class.getSimpleName(), true, false);
    }

    public View getMainFrameLayout(){
        return binding.mainFrameLayout;
    }

    public int getMainFrameLayoutId() {
        return binding.mainFrameLayout.getId();
    }

    public void loaderVisible(){
        binding.progressBarContainer.setVisibility(View.VISIBLE);
    }

    public void loaderGone(){
        binding.progressBarContainer.setVisibility(View.GONE);
    }

    public <T> void startActivity(Class<T> cls, boolean isActivityFinish) {
        Intent intent = new Intent(this, cls);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        if (isActivityFinish) {
            finish();
        }
    }

    public void addFragment(MainFragment frag, String tag, boolean isAddToBackStack, boolean animate){
        mainFragment = frag;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if(animate){
            transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
        }
        transaction.add(getMainFrameLayoutId(), frag, tag);

        if (isAddToBackStack) {
            transaction.addToBackStack(null).commit();
        } else {
            transaction.commit();
        }
    }
}
