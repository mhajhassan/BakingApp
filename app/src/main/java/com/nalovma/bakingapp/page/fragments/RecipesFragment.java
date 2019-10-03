package com.nalovma.bakingapp.page.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nalovma.bakingapp.R;
import com.nalovma.bakingapp.adapter.RecipeAdapter;
import com.nalovma.bakingapp.model.Recipe;
import com.nalovma.bakingapp.page.common.BaseFragment;
import com.nalovma.bakingapp.page.main.MainInterface;
import com.nalovma.bakingapp.page.main.MainViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.nalovma.bakingapp.page.fragments.RecipeDetailFragment.RECIPE;

public class RecipesFragment extends BaseFragment implements MainInterface, RecipeAdapter.RecipeOnItemClickListener {

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
        mRecipesRecyclerView.setHasFixedSize(true);
        setToolbarTitle(getString(R.string.recipes_list));
        initData();
    }

    private void initData() {

        MainViewModel vm = ViewModelProviders.of(this).get(MainViewModel.class);
        vm.mainInterface = this;
        vm.loadData();
    }

    @Override
    public void setRecipe(List<Recipe> recipes) {
        recipeAdapter.setRecipe(recipes, this);
    }

    @Override
    public void onRecipeItemClick(View view, int position) {
        Recipe recipe = recipeAdapter.getRecipeByPosition(position);
        Bundle bundle = new Bundle();
        bundle.putParcelable(RECIPE, recipe);
        RecipeDetailFragment fragment = new RecipeDetailFragment();
        fragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_container, fragment, fragment.getTag())
                .addToBackStack(fragment.getClass().getSimpleName())
                .commit();
    }
}
