
package com.openclassrooms.entrevoisins.neighbour_list;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;


import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.test_utils.SelectFavoriteTabAction;
import com.openclassrooms.entrevoisins.test_utils.Utils;
import com.openclassrooms.entrevoisins.ui.neighbour_list.ListNeighbourActivity;
import com.openclassrooms.entrevoisins.test_utils.DeleteViewAction;
import com.openclassrooms.entrevoisins.ui.neighbour_list.NeighbourDetailActivity;
import com.openclassrooms.entrevoisins.utils.FavoriteNeighbourIds;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static android.support.test.espresso.Espresso.onView;
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
 *
 *          ** IMPORTANT **
 * Tests must be run in a specific order if they are run all together, otherwise some tests may
 * cause others to fail. All methods prefixed with "a_" have high priority because the method names
 * are sorted by "MethodSorters.NAME_ASCENDING".
 */
@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class NeighboursListTest {

    // This is fixed
    private static int ITEMS_COUNT = 12;
    private static int DEFAULT_FAVORITE_NEIGHBOURS_COUNT = 3;

    private ListNeighbourActivity mActivity;

    @Rule
    public ActivityTestRule<ListNeighbourActivity> mActivityRule =
            new ActivityTestRule(ListNeighbourActivity.class);

    @BeforeClass
    public static void setUpOnce(){
        FavoriteNeighbourIds.setData(getFavoritesNeighbourIds(DEFAULT_FAVORITE_NEIGHBOURS_COUNT));
    }

    @Before
    public void setUp() {
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
    public void a_favorite_tab_should_contains_only_favorites_neighbours(){
        onView(withId(R.id.tabs)).perform(new SelectFavoriteTabAction());
        Utils.getFavoriteNeighboursViewInteraction()
                .check(withItemCount(DEFAULT_FAVORITE_NEIGHBOURS_COUNT));
    }


    /**
     * When we delete an item, the item is no more shown
     */
    @Test
    public void myNeighboursList_deleteAction_shouldRemoveItem() {
        // Given : We remove the element at position 2
        Utils.getAllNeighboursLViewMatcher().check(withItemCount(ITEMS_COUNT));
        // When perform a click on a delete icon
        Utils.getAllNeighboursLViewMatcher()
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, new DeleteViewAction()));
        // Then : the number of element is 11
        Utils.getAllNeighboursLViewMatcher().check(withItemCount(ITEMS_COUNT-1));
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
    public void a_neighbourDetailActivity_should_contain_the_correct_neighbour_name(){
        final Neighbour neighbour = Utils.startDetailActivityWithRandomNeighbour();
        // Check if the NeighbourDetailActivity contain the correct Neighbour name.
        onView(ViewMatchers.withId(R.id.neighbour_name)).check(matches(withText(neighbour.getName())));
    }

    private static Set<Long> getFavoritesNeighbourIds(int numberOfFavoritesToSet){
        final List<Neighbour> neighbourList = DI.getNewInstanceApiService().getNeighbours();
        final Set<Long> favoritesNeighbourIds = new HashSet<>();

        while (--numberOfFavoritesToSet >= 0){
            favoritesNeighbourIds.add(neighbourList.get(numberOfFavoritesToSet).getId());
        }
        return favoritesNeighbourIds;
    }


}