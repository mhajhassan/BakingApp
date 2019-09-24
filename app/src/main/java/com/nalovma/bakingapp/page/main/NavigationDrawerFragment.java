package com.nalovma.bakingapp.page.main;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.nalovma.bakingapp.R;
import com.nalovma.bakingapp.page.common.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NavigationDrawerFragment extends BaseFragment {

    private MainNavigation mMainNavigation;
    private DrawerLayout mDrawerLayout;
    private View mFragmentContainerView;

    @BindView(R.id.drawer_recipes)
    Button mDrawerScheduleButton;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (!(context instanceof MainNavigation)) {
            throw new IllegalStateException("The activity must implement MainNavigation");
        }
        mMainNavigation = (MainNavigation) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        mDrawerScheduleButton.setActivated(true);
        return view;
    }

    public void setUp(int fragmentId, DrawerLayout drawerLayout, Toolbar toolbar) {
        mFragmentContainerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(getActivity(),
                drawerLayout,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close) {
        };
        drawerLayout.addDrawerListener(mDrawerToggle);
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
        mDrawerToggle.syncState();
    }

    private ActionBar getActionBar() {
        return ((AppCompatActivity) getActivity()).getSupportActionBar();
    }

    public void closeDrawer() {
        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawer(mFragmentContainerView);
        }
    }

    public boolean isDrawerOpen() {
        return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(mFragmentContainerView);
    }

    @OnClick(R.id.drawer_recipes)
    void recipesClicked() {
        mMainNavigation.goToRecipes();
        closeDrawer();
    }

    public void setButtonActivated(Button buttonActivated) {
        buttonActivated.setActivated(true);
    }
}
