package com.openclassrooms.entrevoisins.neighbour_detail;

import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.test_utils.Utils;
import com.openclassrooms.entrevoisins.ui.neighbour_list.ListNeighbourActivity;
import com.openclassrooms.entrevoisins.utils.FavoriteNeighbourIds;

import org.hamcrest.Matcher;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashSet;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.core.IsNull.notNullValue;

@RunWith(AndroidJUnit4.class)
public class NeighbourDetailTest {

    private ListNeighbourActivity mActivity;
    private FavoriteNeighbourIds favoriteNeighbourIds;
    Neighbour neighbour;

    @Rule
    public ActivityTestRule<ListNeighbourActivity> mActivityRule =
            new ActivityTestRule<>(ListNeighbourActivity.class);


    @Before
    public void setUp() {
        favoriteNeighbourIds = DI.getFavoriteNeighbourIds();
        FavoriteNeighbourIds.setData(new HashSet<>());// clear favorites.
        neighbour = Utils.startDetailActivityWithRandomNeighbour();
        mActivity = mActivityRule.getActivity();
        assertThat(mActivity, notNullValue());
    }


    @Test
    public void should_add_neighbour_to_favorites() {
        Assert.assertTrue(favoriteNeighbourIds.getNeighbours().isEmpty());
        onView(withId(R.id.toggle_favorite_button)).perform(click());
        Assert.assertTrue(favoriteNeighbourIds.getNeighbours().contains(neighbour));
        Assert.assertEquals(1, favoriteNeighbourIds.getNeighbours().size());
    }

    @Test
    public void should_show_snackbar_with_add_to_favorite_success_message(){
        final String snackBarMessage = mActivity.getString(R.string.favorite_added_successfully, neighbour.getName());
        onView(withId(R.id.toggle_favorite_button)).perform(click());
        onView(withId(android.support.design.R.id.snackbar_text))
                .check(matches(withText(snackBarMessage)));
    }

    @Test
    public void should_remove_neighbour_to_favorites() {
        onView(withId(R.id.toggle_favorite_button)).perform(click());
        Assert.assertEquals(1, favoriteNeighbourIds.getNeighbours().size());
        onView(withId(R.id.toggle_favorite_button)).perform(click());
        Assert.assertTrue(favoriteNeighbourIds.getNeighbours().isEmpty());
    }


    @Test
    public void should_show_snackbar_with_remove_from_favorite_success_message() throws InterruptedException {
        final String snackBarMessage = mActivity.getString(R.string.favorite_removed_successfully, neighbour.getName());
        onView(withId(R.id.toggle_favorite_button)).perform(click());
        // Click second time to remove (neighbour was already added in favorites).
        onView(withId(R.id.toggle_favorite_button)).perform(click());
        Thread.sleep(Snackbar.LENGTH_LONG + 500);// Wait for previous snack bar to disappear.
        onView(withId(android.support.design.R.id.snackbar_text))
                .check(matches(withText(snackBarMessage)));
    }

    @Test
    public void clicking_on_back_button_should_return_to_ListNeighbourActivity(){
        onView(withId(R.id.neighbour_detail_root)).check(matches(isDisplayed()));
        onView(withId(R.id.back_button)).perform(click());
        onView(allOf(withId(R.id.list_neighbours), isDisplayed())).check(matches(isDisplayed()));
    }

    public ViewAction click(){
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.LOLLIPOP_MR1){
            // Clicking on CardView throw a "PerformException: Error performing 'single click - At Coordinates: ..."
            // on old android apis such as api 22 so we "force" clicking on the card view.
            // (we need support for api 22 for travis ci emulator).
            return new ViewAction() {
                @Override public Matcher<View> getConstraints() {
                    return allOf(isClickable(), isEnabled(), isDisplayed());
                }

                @Override public String getDescription() {
                    return "force click";
                }

                @Override public void perform(UiController uiController, View view) {
                    view.performClick(); // perform click without checking view coordinates.
                    uiController.loopMainThreadUntilIdle();
                }
            };
        }
        return click();
    }

}
