package deniskuliev.yandextranslator.fragments.translation;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;

import deniskuliev.yandextranslator.R;
import deniskuliev.yandextranslator.yandexTranslatorApi.YandexTranslator;
import deniskuliev.yandextranslator.yandexTranslatorApi.YandexTranslatorResponseParser;

/**
 * Created by deniskuliev on 02.04.17.
 */

class TranslationTask extends AsyncTask<String, Void, String>
{
    final static String EMPTY_STRING = "";

    final static int ORIGINAL_TEXT = 0;
    final static int TRANSLATION_LANGUAGES = 1;
    public String originalText;
    public String translationLanguages;
    private Activity _activity;
    private TextView _translatedTextField;

    public TranslationTask(Activity activity, TextView translatedTextField)
    {
        _activity = activity;
        _translatedTextField = translatedTextField;
    }

    @Override
    protected String doInBackground(String... text)
    {
        String response = null;
        String translatedText = null;
        YandexTranslator translator = new YandexTranslator();

        originalText = text[ORIGINAL_TEXT];
        translationLanguages = text[TRANSLATION_LANGUAGES];

        if (originalText.equals(EMPTY_STRING))
        {
            return EMPTY_STRING;
        }

        if (!isCancelled())
        {
            response = translator
                    .getTranslationPost(originalText, translationLanguages);

            if (response != null)
            {
                translatedText = YandexTranslatorResponseParser.parseResponse(response);
            }
        }

        return translatedText;
    }

    @Override
    protected void onPostExecute(String translatedText)
    {

        if (translatedText == null)
        {
            translatedText = EMPTY_STRING;
            Toast.makeText(_activity,
                           _activity.getResources().getString(R.string.connection_problem),
                           Toast.LENGTH_SHORT).show();
        }

        _translatedTextField.setText(translatedText);
    }
}
