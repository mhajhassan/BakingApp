package com.nalovma.bakingapp.utils;

import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AppCompatActivity;

public final class Utils {

    private Utils() {
    }

    public static void clearFullBackStackButFirst(AppCompatActivity activity) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 1) {
            // clear the fragment back stack
            FragmentManager.BackStackEntry first = fragmentManager.getBackStackEntryAt(1);
            fragmentManager.popBackStackImmediate(first.getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }
}
