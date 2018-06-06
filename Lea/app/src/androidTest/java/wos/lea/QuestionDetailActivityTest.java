package wos.lea;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;


import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withHint;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;


/**
 * Created by u11s65 on 23.05.2018.
 */
@RunWith(AndroidJUnit4.class)
public class QuestionDetailActivityTest {

    @Rule
    public IntentsTestRule<QuestionDetailActivity> testRule =
            new IntentsTestRule<>(QuestionDetailActivity.class);

    @Test
    public void questionTextFieldExistsTest() {
        onView(withId(R.id.display_question_text)).check(matches(withHint("Question")));
    }
    @Test
    public void addAnswerTest() {

        onView(withId(R.id.add_answer)).perform(click());
        onView(withId(R.id.answerText)).check(matches(withHint("Add your answer here...")));

    }

}