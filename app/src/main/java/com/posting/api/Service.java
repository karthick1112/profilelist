package com.posting.api;


import com.posting.model.ItemResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by delaroy on 3/22/17.
 */
public interface Service {

    @GET("https://api.github.com/repositories")
    Call<List<ItemResponse>> getItems();

    @GET("")
    Call<List<ItemResponse>> getItemsforks( @Query("link1") String link1);

}
