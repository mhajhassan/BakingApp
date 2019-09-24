package com.nalovma.bakingapp.remote;

import com.nalovma.bakingapp.model.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface BackingAPI {

    @GET("baking.json")
    Call<List<Recipe>> loadRecipes();
}
