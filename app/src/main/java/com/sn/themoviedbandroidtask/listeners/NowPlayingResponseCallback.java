package com.sn.themoviedbandroidtask.listeners;

import com.sn.themoviedbandroidtask.models.NowPlayingResponse;

/**
 * Created by Saad Nayyer on 5/9/2018.
 */

public interface NowPlayingResponseCallback {
    void onSuccess(NowPlayingResponse response);
    void onError();
    void onNotConnected();
}
