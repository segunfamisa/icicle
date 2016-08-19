package com.segunfamisa.icicle.sample;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    private void rotateScreen() {
        Context context = InstrumentationRegistry.getContext();
        int orientation = context.getResources().getConfiguration().orientation;

        Activity activity = activityTestRule.getActivity();
        activity.setRequestedOrientation((orientation == Configuration.ORIENTATION_LANDSCAPE) ?
            ActivityInfo.SCREEN_ORIENTATION_PORTRAIT : ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    @Before
    public void checkInitialCount() {
        onView(withId(R.id.text_count))
                .check(matches(withText(String.valueOf(MainActivity.DEFAULT_COUNT))));
    }

    @Test
    public void clickIncrement() {
        onView(withId(R.id.button_increment))
                .perform(click());
    }

    @Test
    public void clickIncrementTwiceAndRotate() {
        // click button twice
        onView(withId(R.id.button_increment))
                .check(matches(withText(R.string.btn_text_increment)))
                .perform(click())
                .perform(click());

        // check the text count
        onView(withId(R.id.text_count))
                .check(matches(withText(String.valueOf(MainActivity.DEFAULT_COUNT + 2))));

        // rotate the screen
        rotateScreen();

        // check the count
        onView(withId(R.id.text_count))
                .check(matches(withText(String.valueOf(MainActivity.DEFAULT_COUNT + 2))));
    }
}
