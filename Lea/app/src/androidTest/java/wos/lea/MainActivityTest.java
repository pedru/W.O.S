package wos.lea;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import wos.lea.networking.NetworkManager;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

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
        onView(withId(R.id.action_search_exam)).check(matches(isClickable()));
        onView(withId(R.id.action_search_exam)).perform(click());
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

    @Test
    public void authTest() {
        SharedPreferences sharedPref = testRule.getActivity().getPreferences(Context.MODE_PRIVATE);
        String authtoken = sharedPref.getString("Token", "");
        assertTrue(authtoken.length() > 4);
    }


    @Test
    public void auth1Test() {
        SharedPreferences sharedPref = testRule.getActivity().getPreferences(Context.MODE_PRIVATE);
        sharedPref.edit().clear();
        sharedPref.edit().apply();
        testRule.getActivity().authenticate();
        assertTrue(NetworkManager.getInstance().getAuthtoken().length() > 4);
        SharedPreferences sharedPref1 = testRule.getActivity().getPreferences(Context.MODE_PRIVATE);
        String authtoken = sharedPref1.getString("Token", "");
        assertTrue(authtoken.length() > 4);

    }


}