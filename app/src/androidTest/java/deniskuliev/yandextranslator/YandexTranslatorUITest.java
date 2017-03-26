package deniskuliev.yandextranslator;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@LargeTest
@RunWith(AndroidJUnit4.class)
public class YandexTranslatorUITest
{
    @Rule
    public ActivityTestRule<TranslatorMain> activityTestRule = new ActivityTestRule<>(
            TranslatorMain.class, false, true);

    public TranslatorMain activity;

    @Before
    public void setActivity()
    {
        activity = activityTestRule.getActivity();
    }

    @Test
    public void testTranslation() throws Exception
    {
        String MESSAGE = "test";
        String TRANSLATION = "тест";

        onView(withId(R.id.original_text)).perform(typeText(MESSAGE), closeSoftKeyboard());

        onView(withId(R.id.translated_text)).check(matches(withText(TRANSLATION)));
    }
}
