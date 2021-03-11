package com.openclassrooms.entrevoisins.test_utils.actions;

import android.support.design.widget.TabLayout;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.view.View;

import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static org.hamcrest.Matchers.allOf;
import org.hamcrest.Matcher;

public abstract class SelectTabAction implements ViewAction {
        protected final int tabIndex;

        public SelectTabAction(int tabIndex){
            this.tabIndex = tabIndex;
        }

        @Override
        public Matcher<View> getConstraints() {
                return allOf(isDisplayed(), isAssignableFrom(TabLayout.class));
        }

        @Override
        public String getDescription() {
                return "Select tab at index " + tabIndex;
        }

        @Override
        public void perform(UiController uiController, View view) {
                ((TabLayout) view).getTabAt(tabIndex).select();
        }
}
