package wos.lea;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.app.PendingIntent.getActivity;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withHint;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.Assert.assertNotNull;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.StringEndsWith.endsWith;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

/**
 * Created by u11s65 on 16.05.2018.
 */

@RunWith(AndroidJUnit4.class)
public class CreateQuestionActivityTest {
    @Rule
    public IntentsTestRule<CreateQuestionActivity> testRule =
            new IntentsTestRule<>(CreateQuestionActivity.class);

    @Test
    public void questionEditTextExistTest() {
        onView(withId(R.id.question_text)).check(matches(withHint("Add question here...")));
    }


    @Test
    public void questionSaveExistsTest() {
        onView(withId(R.id.save_question)).perform(click());
    }

    @Test
    public void questionTestEmptyClickTest() {
        onView(withId(R.id.save_question)).perform(click());
        onView(withText(R.string.questionTextEmptyToast)).inRoot(withDecorView(not(is(testRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
    }


}