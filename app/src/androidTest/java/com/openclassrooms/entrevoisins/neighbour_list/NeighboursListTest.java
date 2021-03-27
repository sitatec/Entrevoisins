
package com.openclassrooms.entrevoisins.neighbour_list;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;


import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.test_utils.actions.SelectAllNeighboursTabAction;
import com.openclassrooms.entrevoisins.test_utils.actions.SelectFavoriteTabAction;
import com.openclassrooms.entrevoisins.test_utils.Utils;
import com.openclassrooms.entrevoisins.ui.neighbour_list.ListNeighbourActivity;
import com.openclassrooms.entrevoisins.test_utils.actions.DeleteViewAction;
import com.openclassrooms.entrevoisins.ui.neighbour_detail.NeighbourDetailActivity;
import com.openclassrooms.entrevoisins.utils.FavoriteNeighbourIds;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.openclassrooms.entrevoisins.test_utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.core.IsNull.notNullValue;



/**
 * Test class for list of neighbours
 */
@RunWith(AndroidJUnit4.class)
public class NeighboursListTest {

    private static int INITIAL_FAVORITE_NEIGHBOURS_COUNT = 3;

    private ListNeighbourActivity mActivity;
    private final FavoriteNeighbourIds favoriteNeighbourIds = DI.getFavoriteNeighbourIds();
    private int favoriteNeighbourCount ;
    private int allNeighbourCount;

    @Rule
    public ActivityTestRule<ListNeighbourActivity> mActivityRule =
            new ActivityTestRule<>(ListNeighbourActivity.class);

    @BeforeClass
    public static void setUpOnce(){
        FavoriteNeighbourIds.setData(getFavoritesNeighbourIds(INITIAL_FAVORITE_NEIGHBOURS_COUNT));
    }

    @Before
    public void setUp() {
        favoriteNeighbourCount = favoriteNeighbourIds.getNeighbours().size();
        allNeighbourCount = DI.getNeighbourApiService().getNeighbours().size();
        mActivity = mActivityRule.getActivity();
        assertThat(mActivity, notNullValue());
    }

    /**
     * We ensure that our recyclerview is displaying at least on item
     */
    @Test
    public void myNeighboursList_shouldNotBeEmpty() {
        // First scroll to the position that needs to be matched and click on it.
        Utils.getAllNeighboursLViewMatcher().check(matches(hasMinimumChildCount(1)));
    }

    @Test
    public void favorite_tab_should_contains_only_favorites_neighbours(){
        onView(withId(R.id.tabs)).perform(new SelectFavoriteTabAction());
        Utils.getFavoriteNeighboursViewInteraction()
                .check(withItemCount(favoriteNeighbourCount));
    }

    @Test
    public void deleting_neighbour_which_is_in_favorites_should_affect_the_recyclerview_inside_the_favorite_tab() throws InterruptedException {
        // if a *favorite* neighbour is deleted from MY NEIGHBOURS tab it must not appear inside the
        // FAVORITES tab.

        // check the initial number of favorite neighbours
        onView(withId(R.id.tabs)).perform(new SelectFavoriteTabAction());
        Utils.getFavoriteNeighboursViewInteraction()
                .check(withItemCount(favoriteNeighbourCount));
        // delete a neighbour form all neighbour tab.
        onView(withId(R.id.tabs)).perform(new SelectAllNeighboursTabAction());
        Thread.sleep(500); // Wait for tab switching transition/animation to be done.
        Utils.getAllNeighboursLViewMatcher()
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, new DeleteViewAction()));
        // check that the number of favorite neighbours has decreased by 1
        onView(withId(R.id.tabs)).perform(new SelectFavoriteTabAction());
        Utils.getFavoriteNeighboursViewInteraction()
                .check(withItemCount(favoriteNeighbourCount - 1));
    }

    @Test
    public void should_start_show_the_AddNeighbourActivity(){
        onView(withId(R.id.add_neighbour)).perform(click());
        onView(withId(R.id.add_neighbour_layout)).check(matches(isDisplayed()));
    }

    @Test
    public void should_add_new_neighbour()  {
        Utils.getAllNeighboursLViewMatcher().check(withItemCount(allNeighbourCount));

        onView(withId(R.id.add_neighbour)).perform(click());
        onView(withId(R.id.name)).perform(typeText("NewNeighbour"));
        onView(withId(R.id.phoneNumber)).perform(typeText("55443322"));
        // The aboutMe field and the submit button might be hidden by the keyboard, so we have to close it.
        onView(withId(R.id.address)).perform(typeText("44 add bool, NB")).perform(closeSoftKeyboard());
        onView(withId(R.id.aboutMe)).perform(typeText("long text....")).perform(closeSoftKeyboard());
        onView(withId(R.id.create)).perform(click());

        Utils.getAllNeighboursLViewMatcher().check(withItemCount(allNeighbourCount + 1));
    }


    /**
     * When we delete an item, the item is no more shown
     */
    @Test
    public void myNeighboursList_deleteAction_shouldRemoveItem() {
        Utils.getAllNeighboursLViewMatcher().check(withItemCount(allNeighbourCount));
        // When perform a click on a delete icon
        Utils.getAllNeighboursLViewMatcher()
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, new DeleteViewAction()));

        Utils.getAllNeighboursLViewMatcher().check(withItemCount(allNeighbourCount-1));
    }

    @Test
    public void recyclerView_item_click_should_start_NeighbourDetailActivity(){
        // Check if the NeighbourDetailActivity is started after clicking on a RecyclerView Item.
        Intents.init();
        Utils.startDetailActivityWithRandomNeighbour();
        intended(hasComponent(NeighbourDetailActivity.class.getName()));
        Intents.release();
        // Check if the layout of NeighbourDetailActivity is visible
        onView(withId(R.id.neighbour_detail_root)).check(matches(isDisplayed()));
    }

    @Test
    public void neighbourDetailActivity_should_contain_the_correct_neighbour_name(){
        final Neighbour neighbour = Utils.startDetailActivityWithRandomNeighbour();
        // Check if the NeighbourDetailActivity contain the correct Neighbour name.
        onView(ViewMatchers.withId(R.id.neighbour_name)).check(matches(withText(neighbour.getName())));
    }

    private static Set<Long> getFavoritesNeighbourIds(int numberOfFavorites){
        final List<Neighbour> neighbourList = DI.getNewInstanceApiService().getNeighbours();
        final Set<Long> favoritesNeighbourIds = new HashSet<>();

        while (--numberOfFavorites >= 0){
            favoritesNeighbourIds.add(neighbourList.get(numberOfFavorites).getId());
        }
        return favoritesNeighbourIds;
    }


}