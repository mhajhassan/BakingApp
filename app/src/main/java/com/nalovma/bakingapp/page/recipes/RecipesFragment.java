package com.nalovma.bakingapp.page.recipes;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nalovma.bakingapp.R;
import com.nalovma.bakingapp.adapter.RecipeAdapter;
import com.nalovma.bakingapp.page.common.BaseFragment;
import com.nalovma.bakingapp.remote.RecipeResponse;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipesFragment extends BaseFragment {

    @BindView(R.id.recipesRecyclerView)
    RecyclerView mRecipesRecyclerView;

    private RecipeAdapter recipeAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipes, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        recipeAdapter = new RecipeAdapter();

        mRecipesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecipesRecyclerView.setAdapter(recipeAdapter);

        RecipeResponse response= new RecipeResponse();
        response.start();
    }
}
