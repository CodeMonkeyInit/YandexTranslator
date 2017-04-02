package deniskuliev.yandextranslator.fragments.translation;

import deniskuliev.yandextranslator.translation.TranslatedText;
import deniskuliev.yandextranslator.translation.TranslationHistory;

/**
 * Created by deniskuliev on 02.04.17.
 */

class AddToHistoryRunnable implements Runnable
{
    private static TranslatedText lastTranslatedText;
    private TranslationTask _translationTask;

    public AddToHistoryRunnable(TranslationTask translationTask)
    {
        _translationTask = translationTask;
    }

    @Override
    public void run()
    {
        TranslationHistory translationHistory = TranslationHistory.getInstance();

        try
        {
            String translationLanguages = _translationTask.translationLanguages;
            String originalText = _translationTask.originalText;
            String translatedText = _translationTask.get();

            TranslatedText newTranslatedText = new TranslatedText(originalText,
                                                                  translatedText,
                                                                  translationLanguages);

            if (!originalText.isEmpty() && !newTranslatedText.fieldsEqual(lastTranslatedText))
            {
                translationHistory.add(newTranslatedText);
                lastTranslatedText = newTranslatedText;
            }
        } catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
}
