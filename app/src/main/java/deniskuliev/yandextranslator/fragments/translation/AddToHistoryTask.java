package deniskuliev.yandextranslator.fragments.translation;

import android.os.AsyncTask;

import java.util.concurrent.ExecutionException;

import deniskuliev.yandextranslator.translationModel.TranslatedText;
import deniskuliev.yandextranslator.translationModel.TranslationHistory;

/**
 * Created by deniskuliev on 02.04.17.
 */

class AddToHistoryTask extends AsyncTask<TranslationTask, Void, TranslatedText>
{
    private static TranslatedText lastTranslatedText;

    @Override
    protected TranslatedText doInBackground(TranslationTask... translationTasks)
    {
        try
        {
            return translationTasks[0].get();
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

        if (translatedText != null && !translatedText.original.isEmpty() && !translatedText
                .fieldsEqual(lastTranslatedText))
        {
            translationHistory.add(translatedText);
            lastTranslatedText = translatedText;
        }
    }
}
