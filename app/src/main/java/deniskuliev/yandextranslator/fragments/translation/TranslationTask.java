package deniskuliev.yandextranslator.fragments.translation;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import deniskuliev.yandextranslator.R;
import deniskuliev.yandextranslator.translationModel.TranslateLanguages;
import deniskuliev.yandextranslator.translationModel.TranslatedText;
import deniskuliev.yandextranslator.yandexTranslatorApi.YandexTranslator;
import deniskuliev.yandextranslator.yandexTranslatorApi.YandexTranslatorResponseParser;

/**
 * Created by deniskuliev on 02.04.17.
 */

class TranslationTask extends AsyncTask<String, Void, TranslatedText>
{
    final static String EMPTY_STRING = "";

    final static int ORIGINAL_TEXT = 0;
    final static int TRANSLATION_LANGUAGES = 1;
    public TranslatedText translatedText;
    private Activity _activity;
    private TextView _translatedTextField;
    private Spinner _originalLanguageSpinner;
    private ImageButton _favoritesButton;
    private boolean _autoDetectionEnabled;

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
    }

    @Override
    protected TranslatedText doInBackground(String... text)
    {
        String response = null;
        YandexTranslator translator = new YandexTranslator();
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
                YandexTranslatorResponseParser.parseLanguageDetectionResponse(
                        translator.getOriginalLanguage(translatedText.original,
                                                       translatedText.getOriginalLanguage()),
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
            String originalLangugage = backgroundTranslatedText.getOriginalLanguage();

            int originalLangugageCode = TranslateLanguages
                    .getLangugeCodeByString(originalLangugage);

            if (originalLangugageCode != TranslateLanguages.UNKNOWN_LANGUAGE)
            {
                _originalLanguageSpinner.setSelection(originalLangugageCode);
            }
        }

        _favoritesButton.setImageResource(R.drawable.ic_favorites_history);

        Log.d("FavoritesButton", "unset by TranslationTask.onPostExecute()");

        translatedText = backgroundTranslatedText;

        _translatedTextField.setText(backgroundTranslatedText.translated);
    }
}