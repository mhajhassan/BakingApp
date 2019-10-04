package com.nalovma.bakingapp.page.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nalovma.bakingapp.R;
import com.nalovma.bakingapp.adapter.StepAdapter;
import com.nalovma.bakingapp.model.Recipe;
import com.nalovma.bakingapp.model.Step;
import com.nalovma.bakingapp.page.common.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.nalovma.bakingapp.utils.constants.*;

public class RecipeDetailFragment extends BaseFragment implements StepAdapter.StepOnItemClickListener {

    @BindView(R.id.stepsRecyclerView)
    RecyclerView mStepsRecyclerView;

    private StepAdapter stepAdapter;

    private Recipe recipe;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_details, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        Bundle bundle = getArguments();
        if (bundle != null) {
            recipe = (Recipe) bundle.getParcelable(RECIPE_ID);
            if (recipe != null)
                initData(recipe);
        }
    }

    @OnClick(R.id.ingredientButton)
    public void onIngredientClicked() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(RECIPE_ID, recipe);
        IngredientFragment fragment = new IngredientFragment();
        fragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

        boolean isTablet = getResources().getBoolean(R.bool.isTablet);
        if (!isTablet) {
            fragmentTransaction.replace(R.id.main_container, fragment, fragment.getTag())
                    .addToBackStack(fragment.getClass().getSimpleName())
                    .commit();
        } else {
            fragmentTransaction.replace(R.id.steps_container, fragment, fragment.getTag())
                    .addToBackStack(fragment.getClass().getSimpleName())
                    .commit();
        }
    }

    private void initData(Recipe recipe) {
        stepAdapter = new StepAdapter();
        mStepsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mStepsRecyclerView.setAdapter(stepAdapter);
        mStepsRecyclerView.setHasFixedSize(true);
        setToolbarTitle(recipe.getName());
        stepAdapter.setStep(recipe.getSteps(), this);
    }

    @Override
    public void onStepItemClick(View view, int position) {
        Step step = stepAdapter.getStepByPosition(position);
        Bundle bundle = new Bundle();
        bundle.putParcelable(STEP_ID, step);
        StepDetailViewFragment fragment = new StepDetailViewFragment();
        fragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

        boolean isTablet = getResources().getBoolean(R.bool.isTablet);
        if (!isTablet) {
            fragmentTransaction.replace(R.id.main_container, fragment, fragment.getTag())
                    .addToBackStack(fragment.getClass().getSimpleName())
                    .commit();
        } else {
            fragmentTransaction.replace(R.id.steps_container, fragment, fragment.getTag())
                    .addToBackStack(fragment.getClass().getSimpleName())
                    .commit();
        }

    }
}
