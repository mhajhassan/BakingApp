package com.nalovma.bakingapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nalovma.bakingapp.R;
import com.nalovma.bakingapp.model.Ingredient;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientHolder> {

    private List<Ingredient> mItems = new ArrayList<>();

    @NonNull
    @Override
    public IngredientHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new IngredientHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_item, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull IngredientHolder holder, int position) {
        Ingredient ingredient = mItems.get(position);
        holder.setStep(ingredient, position);
    }

    public void setItems(List<Ingredient> list) {
        this.mItems.clear();
        this.mItems.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mItems == null) {
            return 0;
        }
        return mItems.size();
    }

    public class IngredientHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.recipeName)
        TextView mRecipeName;

        @BindView(R.id.recipeSteps)
        TextView mRecipeSteps;

        @BindView(R.id.recipeNumber)
        TextView mRecipeNumber;

        public IngredientHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setStep(Ingredient ingredient, int position) {

            mRecipeName.setText(ingredient.getIngredient());
            mRecipeSteps.setText(String.format(Locale.ENGLISH, "(%s %s)", ingredient.getQuantity(), ingredient.getMeasure()));
            mRecipeNumber.setText(String.format(Locale.ENGLISH, "%02d", position + 1));

        }


    }
}
