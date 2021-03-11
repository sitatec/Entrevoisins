package com.openclassrooms.entrevoisins.test_utils;

import android.support.design.widget.TabLayout;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.view.View;

import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static org.hamcrest.Matchers.allOf;
import org.hamcrest.Matcher;

public class SelectFavoriteTabAction implements ViewAction {
    @Override
    public Matcher<View> getConstraints() {
        return allOf(isDisplayed(), isAssignableFrom(TabLayout.class));
    }

    @Override
    public String getDescription() {
        return "Select Favorite tab";
    }

    @Override
    public void perform(UiController uiController, View view) {
        ((TabLayout) view).getTabAt(1).select();
    }
}
