package deniskuliev.yandextranslator.translation;

import java.util.ArrayList;

/**
 * Created by deniskuliev on 30.03.17.
 */

public class TranslationFavorites extends TranslationList
{
    private static TranslationFavorites instance;

    private TranslationFavorites()
    {
        translatedTexts = new ArrayList<>();
    }

    public static TranslationFavorites getInstance()
    {
        if (instance == null)
        {
            instance = new TranslationFavorites();
        }

        return instance;
    }

    @Override
    public void remove(int position)
    {
        TranslationHistory translationHistory = TranslationHistory.getInstance();
        TranslatedText translatedText = get(position);
        int postionInTranslationHistory;

        if (translationHistory.contains(translatedText))
        {
            postionInTranslationHistory = translationHistory.indexOf(translatedText);

            translatedText.isFavorite = false;

            translationHistory.set(postionInTranslationHistory, translatedText);
        }

        super.remove(position);

    }

}
