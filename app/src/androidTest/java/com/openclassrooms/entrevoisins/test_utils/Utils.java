package com.openclassrooms.entrevoisins.test_utils;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;

import java.util.List;
import java.util.Random;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static org.hamcrest.Matchers.allOf;

public class Utils {

    public static Neighbour startDetailActivityWithRandomNeighbour(){
        final List<Neighbour> neighbourList = DI.getNewInstanceApiService().getNeighbours();
        final int neighbourIndex = new Random().nextInt(neighbourList.size());
        final Neighbour neighbour = neighbourList.get(neighbourIndex);

        onView(allOf(ViewMatchers.withId(R.id.list_neighbours), isDisplayed()))
                .perform(RecyclerViewActions.actionOnItemAtPosition(neighbourIndex, click()));
        return neighbour;
    }

}
