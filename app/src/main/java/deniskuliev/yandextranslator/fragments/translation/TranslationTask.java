package deniskuliev.yandextranslator.fragments.translation;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import javax.inject.Inject;

import deniskuliev.yandextranslator.R;
import deniskuliev.yandextranslator.TranslatorAppMainActivity;
import deniskuliev.yandextranslator.translationModel.TranslateLanguages;
import deniskuliev.yandextranslator.translationModel.TranslatedText;
import deniskuliev.yandextranslator.yandexTranslatorApi.TranslatorApi;
import deniskuliev.yandextranslator.yandexTranslatorApi.YandexTranslatorResponseParser;

@SuppressWarnings("WeakerAccess")
public class TranslationTask extends AsyncTask<String, Void, TranslatedText>
{
    private final static String EMPTY_STRING = "";

    private final static int ORIGINAL_TEXT = 0;
    private final static int TRANSLATION_LANGUAGES = 1;

    private final Activity _activity;
    private final TextView _translatedTextField;
    private final Spinner _originalLanguageSpinner;
    private final ImageButton _favoritesButton;
    private final boolean _autoDetectionEnabled;

    public TranslatedText translatedText;

    @SuppressWarnings("WeakerAccess")
    @Inject
    protected TranslatorApi translator;

    public TranslationTask(Activity activity,
                           TextView translatedTextField,
                           Spinner originalLanguageSpinner,
                           ImageButton favoritesButton,
                           boolean autoDetectLanguage)
    {
        _activity = activity;
        _translatedTextField = translatedTextField;
        _originalLanguageSpinner = originalLanguageSpinner;
        _autoDetectionEnabled = autoDetectLanguage;
        _favoritesButton = favoritesButton;

        ((TranslatorAppMainActivity) activity).getActivityComponent().injectInto(this);
    }

    @Override
    protected TranslatedText doInBackground(String... text)
    {
        String response;
        TranslatedText translatedText = new TranslatedText();

        translatedText.original = text[ORIGINAL_TEXT].trim();
        translatedText.translationLanguages = text[TRANSLATION_LANGUAGES];

        if (translatedText.original.isEmpty())
        {
            translatedText.translated = EMPTY_STRING;

            return translatedText;
        }

        if (!isCancelled())
        {
            if (_autoDetectionEnabled)
            {
                String serverLanguageDetectionJSONResponse = translator
                        .getOriginalLanguage(translatedText.original,
                                             translatedText.getOriginalLanguage());

                YandexTranslatorResponseParser.parseLanguageDetectionResponse(
                        serverLanguageDetectionJSONResponse,
                        translatedText);
            }

            response = translator
                    .getTranslationPost(translatedText.original,
                                        translatedText.translationLanguages);

            if (response != null)
            {
                YandexTranslatorResponseParser.parseTranslationResponse(response, translatedText);
            }
        }

        return translatedText;
    }

    @Override
    protected void onPostExecute(TranslatedText backgroundTranslatedText)
    {

        if (backgroundTranslatedText.translated == null)
        {
            Toast.makeText(_activity,
                           _activity.getResources().getString(R.string.connection_problem),
                           Toast.LENGTH_SHORT).show();

            _translatedTextField.setText(EMPTY_STRING);

            return;
        }

        if (_autoDetectionEnabled)
        {
            String originalLanguage = backgroundTranslatedText.getOriginalLanguage();

            int originalLanguageCode = TranslateLanguages
                    .getLanguageCodeByString(originalLanguage);

            if (originalLanguageCode != TranslateLanguages.UNKNOWN_LANGUAGE)
            {
                _originalLanguageSpinner.setSelection(originalLanguageCode);
            }
        }

        _favoritesButton.setImageResource(R.drawable.ic_favorites_history);

        translatedText = backgroundTranslatedText;

        _translatedTextField.setText(backgroundTranslatedText.translated);
    }
}