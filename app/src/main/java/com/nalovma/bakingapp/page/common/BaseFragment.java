package com.nalovma.bakingapp.page.common;

import androidx.fragment.app.Fragment;

import com.nalovma.bakingapp.page.main.MainActivity;
import com.nalovma.bakingapp.page.recipe.RecipeActivity;

import butterknife.Unbinder;

public class BaseFragment extends Fragment {

    protected Unbinder mUnbinder;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mUnbinder == null) {
            return;
        }
        mUnbinder.unbind();
    }

    protected void setToolbarTitle(String message) {
        if (!(getActivity() instanceof MainActivity)) {
            return;
        }
        ((MainActivity) getActivity()).setToolbarTitle(message);
    }

    protected void setToolbarVisible(boolean visible){
        if (!(getActivity() instanceof RecipeActivity)) {
            return;
        }
        ((RecipeActivity) getActivity()).setToolbarVisibility(visible);
    }

}
