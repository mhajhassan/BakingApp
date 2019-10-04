package com.nalovma.bakingapp.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.Nullable;

import com.nalovma.bakingapp.model.Recipe;


public class WidgetService extends IntentService {

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */

    private static final String KEY_RECIPE = "recipe_name";
    public static final String ACTION_UPDATE_RECIPE_WIDGET = "com.nalovma.bakingapp.widget.action.update_recipe_widget";
    private static final String SERVICE_NAME = "UpdateRecipeWidgetIntentService";

    public WidgetService() {
        super(SERVICE_NAME);
    }

    public WidgetService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_UPDATE_RECIPE_WIDGET.equals(action)) {
                Recipe recipe = intent.getParcelableExtra(KEY_RECIPE);
                handleUpdateWidget(recipe);
            }
        }
    }

    private void handleUpdateWidget(Recipe recipe) {

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);

        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, RecipeAppWidget.class));

        RecipeAppWidget.updateAppWidget(this, appWidgetManager, appWidgetIds, recipe);
    }

    public static void startUpdateWidget(Context context, Recipe recipe) {

        Intent intent = new Intent(context, WidgetService.class);
        intent.setAction(ACTION_UPDATE_RECIPE_WIDGET);
        intent.putExtra(KEY_RECIPE, recipe);
        context.startService(intent);
    }
}
