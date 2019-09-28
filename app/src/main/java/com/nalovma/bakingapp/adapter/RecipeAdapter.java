package com.nalovma.bakingapp.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
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
    private RecipeOnItemClickListener recipeOnItemClickListener;

    public RecipeAdapter() {
    }

    @NonNull
    @Override
    public RecipeHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new RecipeHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recipe_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeHolder recipeHolder, int i) {
        Recipe recipe = mItems.get(i);
        recipeHolder.setRecipe(recipe, i);
    }

    @Override
    public int getItemCount() {
        if (mItems == null) {
            return 0;
        }
        return mItems.size();

    }

    public Recipe getRecipeByPosition(int pos) {
        if (!mItems.isEmpty()) {
            return mItems.get(pos);
        }
        return null;
    }

    public void setRecipe(List<Recipe> recipes, RecipeOnItemClickListener recipeOnItemClickListener) {
        this.mItems.clear();
        this.mItems.addAll(recipes);
        notifyDataSetChanged();
        this.recipeOnItemClickListener = recipeOnItemClickListener;
    }

    public interface RecipeOnItemClickListener {

        void onRecipeItemClick(View view, int position);
    }

    public class RecipeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.recipeImageView)
        ImageView mRecipeImageView;

        @BindView(R.id.recipeName)
        TextView mRecipeName;

        @BindView(R.id.recipeSteps)
        TextView mRecipeSteps;

        @BindView(R.id.recipeNumber)
        TextView mRecipeNumber;

        public RecipeHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        public void setRecipe(Recipe recipe, int position) {
            mRecipeName.setText(recipe.getName());
            mRecipeSteps.setText(String.format(Locale.ENGLISH, "Steps count:%d, servings count:%d ", recipe.getSteps().size(),recipe.getServings()));
            mRecipeNumber.setText(String.format(Locale.ENGLISH, "%02d", position + 1));
            RequestOptions options = new RequestOptions()
                    .fitCenter()
                    .diskCacheStrategy(DiskCacheStrategy.ALL);

            Glide.with(mRecipeImageView)
                    .load(recipe.getImage())
                    .apply(options)
                    .into(mRecipeImageView);
        }

        @Override
        public void onClick(View v) {
            if (recipeOnItemClickListener != null) {
                recipeOnItemClickListener.onRecipeItemClick(v, getAdapterPosition());
            }
        }
    }
}
