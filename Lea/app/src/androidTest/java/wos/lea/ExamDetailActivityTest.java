package wos.lea;

import android.support.design.widget.FloatingActionButton;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withHint;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.core.AllOf.allOf;

@RunWith(AndroidJUnit4.class)
public class ExamDetailActivityTest {

    @Rule
    public IntentsTestRule<ExamDetailActivity> testRule =
            new IntentsTestRule<>(ExamDetailActivity.class);

    @Test
    public void examListAdapterExistsTest() {
        RecyclerView listView = testRule.getActivity().findViewById(R.id.questionRecyclerView);
        RecyclerView.Adapter adapter = listView.getAdapter();
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
    public void createQuestionTest()
    {
        onView(withId(R.id.add_question)).perform(click());
        onView(withId(R.id.question_text)).check(matches(withHint("Add question here...")));
    }

    @Test
    public void viewQuestionDetailTest()
    {
        onView(withId(R.id.questionRecyclerView))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.display_question_text)).check(matches(withHint("Question")));
    }

}
