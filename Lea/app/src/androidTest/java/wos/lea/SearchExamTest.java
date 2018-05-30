package wos.lea;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;

import junit.framework.Assert;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.AllOf.allOf;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

import android.support.test.espresso.contrib.PickerActions;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;



import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

@RunWith(AndroidJUnit4.class)
public class SearchExamTest {

    @Rule
    public IntentsTestRule<SearchExamActivity> testRule = new IntentsTestRule<>(SearchExamActivity.class);

    private SearchExamActivity searchExamActivity ;

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
        onView(withId(R.id.examDate)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).check(matches(isDisplayed()));
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2018, 8, 25));
        onView(withText("OK")).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).check(doesNotExist());
        onView(allOf(withId(R.id.examDate), withText("25.8.2018")));
    }

    @Test
    public void SpinnerTest() {
        onView(withId(R.id.studyProgramSpinner)).perform(click());
        onData(anything()).atPosition(0).perform(click());
        onView(withId(R.id.studyProgramSpinner)).check(matches(withSpinnerText(containsString("Computer Science"))));

        onView(withId(R.id.courseSpinner)).perform(click());
        onData(anything()).atPosition(0).perform(click());
        onView(withId(R.id.courseSpinner)).check(matches(withSpinnerText(containsString("Mobile Applications"))));
    }


    @Test
    public void noExamsFoundSnackbar(){
        //fill spinner
        onView(withId(R.id.studyProgramSpinner)).perform(click());
        onData(anything()).atPosition(0).perform(click());
        onView(withId(R.id.courseSpinner)).perform(click());
        onData(anything()).atPosition(1).perform(click());
        onView(withId(R.id.examDate)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).check(matches(isDisplayed()));
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2018, 8, 25));
        onView(withText("OK")).perform(click());

        ListView listView = testRule.getActivity().findViewById(R.id.ExamView);

        onView(withId(R.id.noExamsText)).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.createNewExam))).check(matches(isDisplayed()));
        onView(withText("CREATE")).perform(click());
    }

    @Test
    public void createExam(){
        SearchExamActivity activity = testRule.getActivity();

        //fill spinner
        onView(withId(R.id.studyProgramSpinner)).perform(click());
        onData(anything()).atPosition(0).perform(click());
        onView(withId(R.id.courseSpinner)).perform(click());
        onData(anything()).atPosition(1).perform(click());
        onView(withId(R.id.examDate)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).check(matches(isDisplayed()));
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2018, 8, 25));
        onView(withText("OK")).perform(click());
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withText("CREATE")).perform(click());

        onView(withText("Exam was created!")).inRoot(withDecorView(not(is(activity.getWindow().getDecorView())))).check(matches(isDisplayed()));
        ListView listView = testRule.getActivity().findViewById(R.id.ExamView);
        assertNotNull(listView);
    }

    @Test
    public void createExamNoDate(){
        SearchExamActivity activity = testRule.getActivity();

        //fill spinner
        onView(withId(R.id.studyProgramSpinner)).perform(click());
        onData(anything()).atPosition(0).perform(click());
        onView(withId(R.id.courseSpinner)).perform(click());
        onData(anything()).atPosition(1).perform(click());
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withText("CREATE")).perform(click());

        //onView(allOf(withId(R.id.createNewExam))).perform(click());

        onView(withText("You have to select a date!")).inRoot(withDecorView(not(is(activity.getWindow().getDecorView())))).check(matches(isDisplayed()));
    }

    @Test
    public void showExamOnlyOneDate(){
        onView(withId(R.id.studyProgramSpinner)).perform(click());
        onData(anything()).atPosition(0).perform(click());
        onView(withId(R.id.courseSpinner)).perform(click());
        onData(anything()).atPosition(0).perform(click());
        onView(withId(R.id.examDate)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).check(matches(isDisplayed()));
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2018, 1, 21));
        onView(withText("OK")).perform(click());

        ListView listView = testRule.getActivity().findViewById(R.id.ExamView);
        assertNotNull(listView);
        ArrayList <View> w = new ArrayList<>();
        for (int i = 0; i < listView.getCount(); i++){
            w.add(listView.getChildAt(i));
        }
        Assert.assertEquals(w.size(), 1);
    }

    @Test
    public void ShowExamTest(){

        //fill spinner
        onView(withId(R.id.studyProgramSpinner)).perform(click());
        onData(anything()).atPosition(0).perform(click());
        onView(withId(R.id.courseSpinner)).perform(click());
        onData(anything()).atPosition(0).perform(click());

        //check ui

        ListView listView = testRule.getActivity().findViewById(R.id.ExamView);
        assertNotNull(listView);
        ArrayList <View> w = new ArrayList<>();
        for (int i = 0; i < listView.getCount(); i++){
            w.add(listView.getChildAt(i));
        }

        //click on exam
        View v = listView.getChildAt(0);
        TextView name = v.findViewById(R.id.name);
        String text = (String) name.getText();
        onData(anything()).inAdapterView(allOf(withId(R.id.ExamView),
                isCompletelyDisplayed())).atPosition(0).perform(click());

        intended(hasComponent(ExamDetailActivity.class.getName()));
    }
}
