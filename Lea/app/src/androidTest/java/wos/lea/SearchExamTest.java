package wos.lea;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class SearchExamTest extends ActivityInstrumentationTestCase2<SearchExamActivity> {

    private SearchExamActivity searchExamActivity ;
    public SearchExamTest() {
        super(SearchExamActivity.class);
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();
        injectInsrumentation(InstrumentationRegistry.getInstrumentation());
        searchExamActivity = getActivity();
    }

    @Test
    public void DisplayTest() {
        onView(withId(R.id.studyProgramSearch)).check(matches(isDisplayed()));
        onView(withId(R.id.courseSearch)).check(matches(isDisplayed()));
        onView(withId(R.id.dateSearch)).check(matches(isDisplayed()));
        onView(withId(R.id.studyProgramSpinner)).check(matches(isDisplayed()));
        onView(withId(R.id.courseSpinner)).check(matches(isDisplayed()));
        onView(withId(R.id.dateButton)).check(matches(isClickable()));
        onView(withId(R.id.dateButton)).perform(click());
        onView(withId(R.id.calenderSearch)).check(matches(isDisplayed()));
    }
}
