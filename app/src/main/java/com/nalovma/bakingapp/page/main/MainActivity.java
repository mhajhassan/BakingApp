package com.nalovma.bakingapp.page.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.TextView;

import com.nalovma.bakingapp.R;
import com.nalovma.bakingapp.adapter.RecipeAdapter;
import com.nalovma.bakingapp.model.Recipe;
import com.nalovma.bakingapp.page.recipe.RecipeActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.nalovma.bakingapp.utils.constants.*;

public class MainActivity extends AppCompatActivity implements MainInterface, RecipeAdapter.RecipeOnItemClickListener {

    @BindView(R.id.main_toolbar_title)
    TextView mTitleRoomTextView;


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
        initData();
    }

    private void initData() {

        MainViewModel vm = ViewModelProviders.of(this).get(MainViewModel.class);
        vm.mainInterface = this;
        vm.loadData();
    }

    public void setToolbarTitle(String title) {
        mTitleRoomTextView.setText(title);
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
    }
}
