package wos.lea;

import android.support.annotation.DrawableRes;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.view.menu.ActionMenuItemView;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class ExamDetailActivityTest {

    @Rule
    public IntentsTestRule<ExamDetailActivity> testRule =
            new IntentsTestRule<>(ExamDetailActivity.class);

    @Test
    public void examListAdapterExistsTest() {
        ListView listView = testRule.getActivity().findViewById(R.id.questionList);
        ListAdapter adapter = listView.getAdapter();
        assertNotNull(adapter);
    }

    @Test
    public void appBarDateTest() {
        TextView dateView = testRule.getActivity().findViewById(R.id.appBarExamDate);
        assertNotNull(dateView.getText());
        assertTrue(dateView.getText().length() > 0);
    }

    @Test
    public void appBarNameTest() {
        TextView lectureName = testRule.getActivity().findViewById(R.id.appBarExamDate);
        assertNotNull(lectureName.getText());
        assertTrue(lectureName.getText().length() > 0);
    }

    @Test
    public void menuRememberClickableTest()
    {
        onView(withId(R.id.action_remember)).check(matches(isClickable()));
    }

    @Test
    public void menuRememberColorChangeTest()
    {

    }




}
