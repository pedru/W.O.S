package wos.lea;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;

import junit.framework.Assert;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.AllOf.allOf;
import android.support.test.espresso.contrib.PickerActions;
import android.widget.DatePicker;

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
    }

    @Test
    public void CalenderTest() {
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).check(doesNotExist());
        onView(withId(R.id.dateButton)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).check(matches(isDisplayed()));
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2018, 8, 25));
        onView(withText("OK")).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).check(doesNotExist());
        onView(allOf(withId(R.id.dateButton), withText("25.8.2018")));
    }

    @Test
    public void SpinnerTest() {
        onView(withId(R.id.studyProgramSpinner)).perform(click());
        onData(anything()).atPosition(1).perform(click());
        //onView(withId(R.id.studyProgramSpinner)).check(matches(withSpinnerText(containsString("Business Services"))));

        onView(withId(R.id.courseSpinner)).perform(click());
        onData(anything()).atPosition(1).perform(click());
        //onView(withId(R.id.courseSpinner)).check(matches(withSpinnerText(containsString("Computers"))));
    }
}
