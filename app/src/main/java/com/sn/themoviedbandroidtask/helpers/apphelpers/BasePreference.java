package com.sn.themoviedbandroidtask.helpers.apphelpers;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Saad Nayyer on 5/8/2018.
 */

public class BasePreference extends PreferenceHelpers {
    private Context context;

    private static final String FILENAME = "preferences";

    public BasePreference(Context c) {
        this.context = c;
    }

    public SharedPreferences getSharedPreferences() {
        return context.getSharedPreferences(FILENAME, Activity.MODE_PRIVATE);
    }
}
