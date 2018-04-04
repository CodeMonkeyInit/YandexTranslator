package deniskuliev.yandextranslator.fragments.translation;

import android.os.AsyncTask;

import java.util.concurrent.ExecutionException;

import deniskuliev.yandextranslator.translationModel.TranslatedText;
import deniskuliev.yandextranslator.translationModel.TranslationHistory;


class AddToHistoryTask extends AsyncTask<TranslationTask, Void, TranslatedText>
{
    private static final int TRANSLATION_TASK = 0;
    private static TranslatedText lastTranslatedText;

    @Override
    protected TranslatedText doInBackground(TranslationTask... translationTasks)
    {
        try
        {
            if (translationTasks[TRANSLATION_TASK] != null)
            {
                return translationTasks[TRANSLATION_TASK].get();
            }
        }
        catch (InterruptedException | ExecutionException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(TranslatedText translatedText)
    {
        TranslationHistory translationHistory = TranslationHistory.getInstance();

        if (translatedText != null
                && !translatedText.original.isEmpty()
                && translatedText.translated != null
                && !translatedText.fieldsEqual(lastTranslatedText))
        {
            translationHistory.add(translatedText);
            lastTranslatedText = translatedText;
        }
    }
}