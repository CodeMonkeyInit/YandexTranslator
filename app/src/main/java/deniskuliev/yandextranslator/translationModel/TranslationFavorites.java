package deniskuliev.yandextranslator.translationModel;

import com.google.common.collect.Lists;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TranslationFavorites extends TranslationList
{
    private static TranslationFavorites instance;

    private TranslationFavorites()
    {
        super();
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
    protected void initializeCollections()
    {
        List<TranslatedText> translatedFavoritesTexts = null;

        //noinspection Convert2Diamond
        translatedTexts = new ArrayList<TranslatedText>();
        reversedTranslatedTexts = Lists.reverse(translatedTexts);

        try
        {
            translatedFavoritesTexts = historyDAO.getFavorites();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        if (translatedFavoritesTexts != null)
        {
            for (TranslatedText translatedText : translatedFavoritesTexts)
            {
                super.add(translatedText);
            }
        }

    }

    @Override
    public void add(TranslatedText translatedText)
    {
        if (contains(translatedText))
        {
            return;
        }

        try
        {
            historyDAO.createOrUpdate(translatedText);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        super.add(translatedText);
    }

    @Override
    public void set(int position, TranslatedText translatedText)
    {
        try
        {
            historyDAO.update(translatedText);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        super.set(position, translatedText);
    }

    @Override
    public void remove(int position)
    {
        TranslationHistory translationHistory = TranslationHistory.getInstance();
        TranslatedText translatedText = get(position);
        int positionInTranslationHistory;

        translatedText.isFavorite = false;

        if (translationHistory.contains(translatedText))
        {
            positionInTranslationHistory = translationHistory.indexOf(translatedText);

            translationHistory.set(positionInTranslationHistory, translatedText);
        }

        try
        {
            historyDAO.remove(translatedText);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        super.remove(position);
    }

    @Override
    public void empty()
    {
        for (TranslatedText translatedText : translatedTexts)
        {
            translatedText.isFavorite = false;
        }

        super.empty();
    }
}
