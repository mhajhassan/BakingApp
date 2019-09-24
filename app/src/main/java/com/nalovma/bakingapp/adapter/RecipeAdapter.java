package com.nalovma.bakingapp.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.nalovma.bakingapp.R;
import com.nalovma.bakingapp.model.Recipe;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeHolder> {

    private List<Recipe> mItems = new ArrayList<>();

    public RecipeAdapter() {
    }

    public RecipeAdapter(List<Recipe> mItems) {
        this.mItems = mItems;
    }

    @NonNull
    @Override
    public RecipeHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new RecipeHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recipe_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeHolder recipeHolder, int i) {
        Recipe recipe = mItems.get(i);
        recipeHolder.setRecipe(recipe);
    }

    @Override
    public int getItemCount() {
        if (mItems == null) {
            return 0;
        }
        return mItems.size();

    }

    public static class RecipeHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.recipeImageView)
        ImageView mRecipeImageView;

        @BindView(R.id.recipeName)
        TextView mRecipeName;

        @BindView(R.id.recipeSteps)
        TextView mRecipeSteps;

        public RecipeHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setRecipe(Recipe recipe) {
            mRecipeName.setText(recipe.getName());
            mRecipeSteps.setText(String.format(Locale.ENGLISH, "Steps: %d", recipe.getSteps().size()));

            RequestOptions options = new RequestOptions()
                    .fitCenter()
                    .diskCacheStrategy(DiskCacheStrategy.ALL);

            Glide.with(mRecipeImageView)
                    .load(recipe.getImage())
                    .apply(options)
                    .into(mRecipeImageView);
        }
    }
}
