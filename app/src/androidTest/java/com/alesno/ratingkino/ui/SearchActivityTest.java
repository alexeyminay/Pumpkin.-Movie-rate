package com.alesno.ratingkino.ui;

import android.view.View;

import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.ViewAssertion;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.alesno.ratingkino.R;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.*;


@RunWith(AndroidJUnit4.class)
public class SearchActivityTest {

    @Rule
    public ActivityTestRule<SearchActivity>activityTestRule = new ActivityTestRule<>(SearchActivity.class);

    @Test
    public void checkResult(){
        String requestFilm = "зеленая миля";
        onView(withId(R.id.edit_input)).perform(replaceText(requestFilm));
        onView(withId(R.id.button_search)).perform(click());
        onView(isRoot()).perform(waitFor(3000));
        onView(withId(R.id.text_result)).check(matches(withText("435")));

    }

    public static ViewAction waitFor(final long millis){
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isRoot();
            }

            @Override
            public String getDescription() {
                return "Wait for " + millis + " milliseconds.";
            }

            @Override
            public void perform(UiController uiController, View view) {
                uiController.loopMainThreadForAtLeast(millis);
            }
        };

    }

}