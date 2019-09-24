package com.nalovma.bakingapp.page.main;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.TextView;

import com.nalovma.bakingapp.R;
import com.nalovma.bakingapp.page.recipes.RecipesFragment;
import com.nalovma.bakingapp.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainNavigation {

    @BindView(R.id.main_drawer_layout)
    DrawerLayout mDrawerLayout;

    @BindView(R.id.main_toolbar_title)
    TextView mTitleRoomTextView;

    @BindView(R.id.main_toolbar)
    Toolbar mToolbar;

    @BindView(R.id.drawer_recipes)
    Button mDrawerRecipesButton;

    private NavigationDrawerFragment mNavigationDrawerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.main_drawer_fragment);
        mNavigationDrawerFragment.setUp(R.id.main_drawer_fragment, mDrawerLayout, mToolbar);
        switchFragment(new RecipesFragment());
    }

    public void setToolbarTitle(String title) {
        mTitleRoomTextView.setText(title);
    }

    @Override
    public void goToRecipes() {
        switchFragment(new RecipesFragment());
    }

    private void switchFragment(@NonNull Fragment fragment) {
        switchFragment(fragment, true);
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

    @Override
    public void onBackPressed() {
        if (mNavigationDrawerFragment.isDrawerOpen()) {
            mNavigationDrawerFragment.closeDrawer();
            return;
        }
        if (getSupportFragmentManager().getBackStackEntryCount() <= 1) {
            finish();
            return;
        }
        mNavigationDrawerFragment.setButtonActivated(mNavigationDrawerFragment.mDrawerScheduleButton);
        super.onBackPressed();
    }
}
