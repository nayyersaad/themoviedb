package com.sn.themoviedbandroidtask.helpers.apphelpers;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

/**
 * Created by Saad Nayyer on 5/9/2018.
 */

public class UIHelpers {
    public static void showSnackBarShort(View view, String message) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();
    }

    public static void setImageWithGlide(Context context, ImageView view, String url){
        Glide.with(context).clear(view);
        Glide.with(context).load(url).into(view);
    }

}
