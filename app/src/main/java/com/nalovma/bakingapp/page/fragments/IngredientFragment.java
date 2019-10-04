package com.nalovma.bakingapp.page.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nalovma.bakingapp.R;
import com.nalovma.bakingapp.adapter.IngredientAdapter;
import com.nalovma.bakingapp.model.Recipe;
import com.nalovma.bakingapp.page.common.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.nalovma.bakingapp.utils.constants.*;

public class IngredientFragment extends BaseFragment {


    @BindView(R.id.ingredientRecyclerView)
    RecyclerView mIngredientRecyclerView;

    private IngredientAdapter ingredientAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ingredient, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        Bundle bundle = getArguments();
        if (bundle != null) {
            Recipe recipe = (Recipe) bundle.getParcelable(RECIPE_ID);
            if (recipe != null)
                initData(recipe);
        }
    }

    private void initData(Recipe recipe) {
        ingredientAdapter = new IngredientAdapter();
        mIngredientRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mIngredientRecyclerView.setAdapter(ingredientAdapter);
        mIngredientRecyclerView.setHasFixedSize(true);
        setToolbarTitle(recipe.getName());
        ingredientAdapter.setItems(recipe.getIngredients());

    }
}
