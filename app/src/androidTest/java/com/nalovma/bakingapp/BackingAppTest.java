package com.nalovma.bakingapp;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.IdlingResource;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.filters.LargeTest;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.nalovma.bakingapp.page.main.MainActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class BackingAppTest {

    private static final String RECIPE_ITEM = "Brownies";
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);
    private IdlingResource mIdlingResource;


    @Before
    public void registerIdlingResource() {
        ActivityScenario activityScenario = ActivityScenario.launch(MainActivity.class);
        activityScenario.onActivity((ActivityScenario.ActivityAction<MainActivity>) activity -> {
            mIdlingResource = activity.getIdlingResource();
            // To prove that the test fails, omit this call:
            IdlingRegistry.getInstance().register(mIdlingResource);
        });
    }

    @After
    public void unregisterIdlingResource() {
        if (mIdlingResource != null) {
            IdlingRegistry.getInstance().unregister(mIdlingResource);
        }
    }

    @Test
    public void checkToolBarTextOfSelectedItemTest() {
        onView(withId(R.id.recipesRecyclerView)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText(RECIPE_ITEM)), click()));
        onView(withId(R.id.main_toolbar_title)).check(matches(withText(RECIPE_ITEM)));

    }


    @Test
    public void checkMainActivityToolbarTextTest() {
        onView(withId(R.id.main_toolbar_title)).check(matches(isDisplayed()));
        onView(withId(R.id.main_toolbar_title)).check(matches(withText(R.string.recipes_list)));
    }

    @Test
    public void openStepTest() {
        onView(withId(R.id.recipesRecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.stepsRecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.stepInstructions)).check(matches(isDisplayed()));
    }

    @Test
    public void openIngredientTest() {
        onView(withId(R.id.recipesRecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.ingredientButton)).perform(click());
        onView(withId(R.id.ingredientRecyclerView)).check(matches(isDisplayed()));
    }
}
