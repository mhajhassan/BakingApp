package com.nalovma.bakingapp.remote;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nalovma.bakingapp.model.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecipeResponse implements Callback<List<Recipe>> {


    private static final String BASE_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/";

    public void start() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        BackingAPI backingAPI = retrofit.create(BackingAPI.class);

        Call<List<Recipe>> call = backingAPI.loadRecipes();
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
        if (response.isSuccessful()) {
            List<Recipe> recipeList = response.body();
            for (Recipe recipe : recipeList) {
                System.out.println(recipe.getName());
            }
        }
    }

    @Override
    public void onFailure(Call<List<Recipe>> call, Throwable t) {
        t.printStackTrace();
    }
}
