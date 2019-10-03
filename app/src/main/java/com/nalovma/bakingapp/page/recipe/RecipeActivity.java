package com.nalovma.bakingapp.page.recipe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.nalovma.bakingapp.R;
import com.nalovma.bakingapp.model.Recipe;
import com.nalovma.bakingapp.page.fragments.RecipeDetailFragment;
import com.nalovma.bakingapp.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.nalovma.bakingapp.utils.constants.RECIPE_ID;

public class RecipeActivity extends AppCompatActivity {
    @BindView(R.id.main_toolbar_title)
    TextView mTitleRoomTextView;

    @BindView(R.id.main_toolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);

        Intent intent = getIntent();
        Recipe recipe = intent.getParcelableExtra(RECIPE_ID);
        setToolbarTitle(recipe.getName());
        
        Bundle bundle = new Bundle();
        bundle.putParcelable(RECIPE_ID, recipe);

        RecipeDetailFragment fragment = new RecipeDetailFragment();
        fragment.setArguments(bundle);
        switchFragment(fragment,true);
    }

    private void switchFragment(@NonNull Fragment fragment, boolean clearBackStack) {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.main_container);
        if (fragment.getClass().isInstance(currentFragment)) {
            return;
        }
        if (clearBackStack) {
            Utils.clearFullBackStackButFirst(this);
        }
        addFragmentToBackStack(fragment);
    }

    private void addFragmentToBackStack(@NonNull Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_container, fragment, fragment.getTag())
                .addToBackStack(fragment.getClass().getSimpleName())
                .commit();

    }

    public void setToolbarTitle(String title) {
        mTitleRoomTextView.setText(title);
    }

    public void setToolbarVisibility(boolean visibility) {
        if (visibility) {
            mToolbar.setVisibility(View.VISIBLE);
        } else {
            mToolbar.setVisibility(View.GONE);
        }

    }

    @Override
    public void onBackPressed() {

        if (getSupportFragmentManager().getBackStackEntryCount() <= 1) {
            finish();
            return;
        }
        super.onBackPressed();
    }
}
