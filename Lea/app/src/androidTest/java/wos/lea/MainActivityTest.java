package wos.lea;

import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.ComponentNameMatchers.hasShortClassName;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertNotNull;

/**
 * Created by u11s65 on 27.03.2018.
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public IntentsTestRule<MainActivity> testRule =
            new IntentsTestRule<>(MainActivity.class);


    @Test
    public void getExamsTest() throws InterruptedException {
        assertNotNull(testRule.getActivity().getExams());
    }

    @Test
    public void examListTest() {
        View listView = testRule.getActivity().findViewById(R.id.examList);
        assertNotNull(listView);
    }

    @Test
    public void examListAdapterExistsTest() {
        ListView listView = testRule.getActivity().findViewById(R.id.examList);
        ListAdapter adapter = listView.getAdapter();
        assertNotNull(adapter);
    }

    @Test
    public void searchButton() {
        onView(withId(R.id.searchButton)).check(matches(isClickable()));
        onView(withId(R.id.searchButton)).perform(click());
        intended(hasComponent(SearchExamActivity.class.getName()));
    }

    @Test
    public void clickOnExamTest() {
        ListView listView = testRule.getActivity().findViewById(R.id.examList);
        assertNotNull(listView);
        View v = listView.getChildAt(0);
        TextView name = v.findViewById(R.id.name);
        String text = (String) name.getText();
        onView(withText(text)).perform(click());
        intended(hasComponent(ExamDetailActivity.class.getName()));
    }



}