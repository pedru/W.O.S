package wos.lea;

import android.content.Intent;
import android.support.annotation.DrawableRes;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.view.menu.ActionMenuItemView;
import android.util.Log;
import android.view.View;
import android.support.v7.widget.RecyclerView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
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
import java.util.ArrayList;
import java.util.List;

import wos.lea.networking.Exam;
import wos.lea.networking.NetworkManager;
import wos.lea.test.LeaTestDatabase;
import wos.lea.test.LeaTestRestService;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.core.AllOf.allOf;
import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class ExamDetailActivityTest {

    @Rule
    public IntentsTestRule<ExamDetailActivity> testRule =
            new IntentsTestRule<>(ExamDetailActivity.class);

/*    @Test
    public void examListAdapterExistsTest() {

       // TextView dateView = testRule.getActivity().findViewById(R.id.appBarExamDate);
        RecyclerView listView = testRule.getActivity().findViewById(R.id.questionRecyclerView);
       // RecyclerView.Adapter adapter = listView.getAdapter();

        //assertNotNull(adapter);
    }*/

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
    public void menuRememberUnsubscribeExamTest() {

        LeaTestRestService leaRestService = (LeaTestRestService) NetworkManager.getInstance().getLeaRestService();
        LeaTestDatabase test_database = leaRestService.getLeaTestDatabase();
        List<Exam> exams = test_database.getMyUsers().getExams();

        int original_exams_size = exams.size();
        int id_current_exam = testRule.getActivity().getId();
        boolean exam_not_found = true;

        onView(withId(R.id.action_remember)).perform(click());

        exams = test_database.getMyUsers().getExams();
        int new_exams_size = exams.size();

        for (Exam ex : exams) {
            if(ex.getId() == id_current_exam) {
                exam_not_found = false;
            }
        }
        assertTrue((new_exams_size)==(original_exams_size-1) && (exam_not_found));
    }

    @Test
    public void menuCanRememberTest()
    {
        boolean subscribed = testRule.getActivity().getCanRememberExam();
        onView(withId(R.id.action_remember)).perform(click());
        assertTrue(subscribed != testRule.getActivity().getCanRememberExam());
    }


    @Test
    public void createQuestionTest()
    {
        onView(withId(R.id.add_question)).perform(click());
        onView(withId(R.id.question_text)).check(matches(withHint("Add question here...")));
    }

/*    @Test
    public void viewQuestionDetailTest()
    {
        onView(withId(R.id.questionRecyclerView))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.display_question_text)).check(matches(withHint("Question")));
    }*/
}
