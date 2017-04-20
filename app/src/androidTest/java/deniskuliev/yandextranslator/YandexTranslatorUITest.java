package deniskuliev.yandextranslator;

import android.content.Intent;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.Switch;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import deniskuliev.yandextranslator.translationModel.TranslateLanguages;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.action.ViewActions.swipeRight;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class YandexTranslatorUITest
{
    private final static String ENGLISH_TEXT = "test";

    private final static String RUSSIAN_TEXT = "тест";

    @Rule
    public final ActivityTestRule<TranslatorAppMainActivity> activityTestRule = new ActivityTestRule<>(
            TranslatorAppMainActivity.class, true, false);

    @SuppressWarnings("WeakerAccess")
    public TranslatorAppMainActivity activity;

    private String getLanguageFromStringsArray(int index)
    {
        return activity.getResources().getStringArray(R.array.translation_languages)[index];
    }

    private void navigateToSettings()
    {
        onView(withId(R.id.navigation_settings)).perform(click());
    }

    private void enableLanguageAutoDetection()
    {
        Switch languageSwitch;

        navigateToSettings();

        languageSwitch = (Switch) activity.findViewById(R.id.detect_language_switch);

        if (!languageSwitch.isChecked())
        {
            onView(withId(R.id.detect_language_switch)).perform(click());
        }
    }

    private void setLanguagesToEnglishRussian()
    {
        navigateToTranslation();

        onView(withId(R.id.original_text_language_spinner)).perform(click());

        onView(
                allOf(
                        withText(getLanguageFromStringsArray(TranslateLanguages.ENGLISH_LANGUAGE)),
                        withId(R.id.spinner_dropdown)
                )
        ).perform(click());

        onView(withId(R.id.translated_text_language_spinner)).perform(click());

        onView(
                allOf(
                        withText(getLanguageFromStringsArray(TranslateLanguages.RUSSIAN_LANGUAGE)),
                        withId(R.id.spinner_dropdown)
                )
        ).perform(click());
    }

    private void navigateToTranslation()
    {
        onView(withId(R.id.navigation_translate)).perform(click());
    }

    private void navigateToHistory()
    {
        //Switch to history/favorites tabs fragment
        onView(withId(R.id.navigation_favorites))
                .perform(click());

        //Switch to history fragment
        onView(withText(R.string.history))
                .perform(click());
    }

    private void navigateToFavorites()
    {
        //Switch to history/favorites tabs fragment
        onView(withId(R.id.navigation_favorites)).perform(click());
    }

    private void removeFromHistory()
    {
        navigateToHistory();

        onView(withId(R.id.history_recycler_view)).perform(
                RecyclerViewActions.actionOnItem(hasDescendant(withText(ENGLISH_TEXT)), swipeLeft())
        );
    }

    private void addToFavoritesInTranslationFragment()
    {
        testTranslation();

        onView(withId(R.id.translation_favorite_button)).perform(click());
    }

    private void removeFromFavorites()
    {
        navigateToFavorites();

        onView(withId(R.id.favorites_recycler_view)).perform(
                RecyclerViewActions
                        .actionOnItem(hasDescendant(withText(ENGLISH_TEXT)), swipeRight())
        );
    }

    private void testTranslation()
    {
        navigateToTranslation();

        setLanguagesToEnglishRussian();

        onView(withId(R.id.original_text))
                .perform(typeText(ENGLISH_TEXT), closeSoftKeyboard());

        onView(withId(R.id.translated_text))
                .check(matches(withText(RUSSIAN_TEXT)));
    }

    @Before
    public void setActivity()
    {
        Intent testModeIntent = new Intent();

        testModeIntent.putExtra(TranslatorAppMainActivity.TEST_MODE_TAG, true);

        activityTestRule.launchActivity(testModeIntent);

        activity = activityTestRule.getActivity();
    }


    @Test
    public void checkHistory()
    {
        testTranslation();

        navigateToHistory();

        onView(withId(R.id.history_recycler_view))
                .perform(
                        RecyclerViewActions.scrollTo(hasDescendant(withText(ENGLISH_TEXT)))
                )
                .check(
                        matches(hasDescendant(withText(RUSSIAN_TEXT)))
                );

        removeFromHistory();
    }

    @Test
    public void clearHistoryButton()
    {
        testTranslation();

        navigateToHistory();

        onView(withId(R.id.clear_history_button)).perform(click());

        onView(withText(R.string.yes)).perform(click());

        onView(allOf(withId(R.id.history_recycler_view), hasDescendant(withText(ENGLISH_TEXT))))
                .check(doesNotExist());
    }

    @Test
    public void checkFavorites()
    {
        addToFavoritesInTranslationFragment();

        navigateToFavorites();

        onView(withId(R.id.favorites_recycler_view))
                .perform(
                        RecyclerViewActions.scrollTo(hasDescendant(withText(ENGLISH_TEXT)))
                )
                .check(
                        matches(hasDescendant(withText(RUSSIAN_TEXT))
                        )
                );

        removeFromFavorites();
    }

    @Test
    public void testLanguageAutoDetection()
    {
        final String russianLanguageString = getLanguageFromStringsArray(
                TranslateLanguages.RUSSIAN_LANGUAGE);

        enableLanguageAutoDetection();

        navigateToTranslation();

        onView(withId(R.id.original_text)).perform(click());

        onView(withId(R.id.original_text)).perform(replaceText(RUSSIAN_TEXT), closeSoftKeyboard());

        onView(withId(R.id.original_text_language_spinner))
                .check(matches(
                        hasDescendant(withText(russianLanguageString))
                       )
                );
    }

    @Test
    public void languageSwapButtonTest()
    {
        final String russianLanguageString = getLanguageFromStringsArray(
                TranslateLanguages.RUSSIAN_LANGUAGE);
        final String englishLanguageString = getLanguageFromStringsArray(
                TranslateLanguages.ENGLISH_LANGUAGE);

        navigateToTranslation();

        setLanguagesToEnglishRussian();

        onView(withId(R.id.swap_translation_languages_button)).perform(click());

        onView(withId(R.id.original_text_language_spinner))
                .check(matches(
                        hasDescendant(withText(russianLanguageString))
                       )
                );

        onView(withId(R.id.translated_text_language_spinner))
                .check(matches(
                        hasDescendant(withText(englishLanguageString))
                       )
                );
    }
}