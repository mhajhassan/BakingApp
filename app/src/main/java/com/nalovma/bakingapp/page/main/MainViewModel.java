package com.nalovma.bakingapp.page.main;

import androidx.lifecycle.ViewModel;

import com.nalovma.bakingapp.model.Recipe;
import com.nalovma.bakingapp.remote.BackingAPI;
import com.nalovma.bakingapp.remote.RetrofitConnection;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewModel extends ViewModel {

    public MainInterface mainInterface;

    public void loadData() {
        BackingAPI client = RetrofitConnection.getClient().create(BackingAPI.class);
        Call<List<Recipe>> loadRecipes = client.loadRecipes();
        loadRecipes.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                if (response.body() != null) {
                    mainInterface.setRecipe(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {

            }
        });
    }
}
