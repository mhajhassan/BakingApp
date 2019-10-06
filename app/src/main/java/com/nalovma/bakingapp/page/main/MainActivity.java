package com.nalovma.bakingapp.page.main;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.IdlingResource;

import android.view.View;
import android.widget.TextView;

import com.nalovma.bakingapp.R;
import com.nalovma.bakingapp.adapter.RecipeAdapter;
import com.nalovma.bakingapp.model.Recipe;
import com.nalovma.bakingapp.page.recipe.RecipeActivity;
import com.nalovma.bakingapp.utils.SimpleIdlingResource;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.nalovma.bakingapp.utils.constants.*;

public class MainActivity extends AppCompatActivity implements MainInterface, RecipeAdapter.RecipeOnItemClickListener {

    @Nullable
    private SimpleIdlingResource simpleIdlingResource;

    @BindView(R.id.main_toolbar)
    Toolbar mToolbar;

    @BindView(R.id.recipesRecyclerView)
    RecyclerView mRecipesRecyclerView;

    private RecipeAdapter recipeAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        recipeAdapter = new RecipeAdapter();
        boolean isTablet = getResources().getBoolean(R.bool.isTablet);
        int spanCount = 1;
        if (isTablet) {
            spanCount = 3;
        }
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, spanCount);
        mRecipesRecyclerView.setLayoutManager(gridLayoutManager);
        mRecipesRecyclerView.setAdapter(recipeAdapter);
        mRecipesRecyclerView.setHasFixedSize(true);
        setToolbarTitle(getString(R.string.recipes_list));
        setSupportActionBar(mToolbar);
        getIdlingResource();
        initData();
    }

    private void initData() {

        MainViewModel vm = ViewModelProviders.of(this).get(MainViewModel.class);
        vm.mainInterface = this;
        setSimpleIdlingResource(false);
        vm.loadData();

    }

    public void setToolbarTitle(String title) {
        mToolbar.setTitle(title);
    }

    @Override
    public void onRecipeItemClick(View view, int position) {
        Recipe recipe = recipeAdapter.getRecipeByPosition(position);
        Intent intent = new Intent(MainActivity.this, RecipeActivity.class);
        intent.putExtra(RECIPE_ID, recipe);
        startActivity(intent);
    }

    @Override
    public void setRecipe(List<Recipe> recipes) {
        recipeAdapter.setRecipe(recipes, this);
        setSimpleIdlingResource(true);
    }


    /**
     * Only called from test, creates and returns a new {@link SimpleIdlingResource}.
     */
    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (simpleIdlingResource == null) {
            simpleIdlingResource = new SimpleIdlingResource();
        }
        return simpleIdlingResource;
    }

    private void setSimpleIdlingResource(boolean state) {
        if (simpleIdlingResource != null) {
            simpleIdlingResource.setIdleState(state);
        }
    }
}
