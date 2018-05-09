package com.sn.themoviedbandroidtask.helpers.apphelpers;

import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created by Saad Nayyer on 5/9/2018.
 */

public class JsonHelpers {
    public static Object parseModel(Object response, Class modelClas) {
        Gson gson = new Gson();
        return gson.fromJson(gson.toJson(response).toString(),modelClas);
    }

    public static ArrayList<Object> parseArray(ArrayList<Object> response, Class modelClas) {
        Gson gson = new Gson();
        ArrayList<Object> list = new ArrayList<>();
        for (int index = 0; index < response.size(); index++) {
            list.add(gson.fromJson(gson.toJson(response.get(index)).toString(),modelClas));
        }
        return list;
    }
}
