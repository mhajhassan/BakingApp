package com.nalovma.bakingapp.page.recipe;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.nalovma.bakingapp.R;
import com.nalovma.bakingapp.model.Recipe;
import com.nalovma.bakingapp.page.fragments.RecipeDetailFragment;
import com.nalovma.bakingapp.page.fragments.StepDetailViewFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.nalovma.bakingapp.utils.constants.RECIPE_ID;

public class RecipeActivity extends AppCompatActivity {

    @BindView(R.id.main_toolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        ButterKnife.bind(this);

        if (null == savedInstanceState) {
            Intent intent = getIntent();
            Recipe recipe = intent.getParcelableExtra(RECIPE_ID);
            setToolbarTitle(recipe.getName());
            mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
            setSupportActionBar(mToolbar);
            Bundle bundle = new Bundle();
            bundle.putParcelable(RECIPE_ID, recipe);

            RecipeDetailFragment fragment = new RecipeDetailFragment();
            fragment.setArguments(bundle);

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.main_container, fragment);
            ft.commit();

            if (findViewById(R.id.steps_container) != null) {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.steps_container, new StepDetailViewFragment());
                fragmentTransaction.commit();
            }
        }


    }


    public void setToolbarTitle(String title) {
        mToolbar.setTitle(title);
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
        if (findViewById(R.id.steps_container) != null) {
            finish();
            return;
        }
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
