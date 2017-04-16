package deniskuliev.yandextranslator.translationModel;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

/**
 * Created by deniskuliev on 06.04.17.
 */
public class HistoryDAO extends BaseDaoImpl<TranslatedText, Integer>
{
    HistoryDAO(ConnectionSource connectionSource, Class<TranslatedText> dataClass) throws
                                                                                   SQLException
    {
        super(connectionSource, dataClass);
    }

    public List<TranslatedText> getTranslationHistory() throws SQLException
    {
        List<TranslatedText> translatedTexts = queryBuilder().where().eq("isInHistory", true)
                .query();

        return translatedTexts;
    }

    public List<TranslatedText> getFavorites() throws SQLException
    {
        List<TranslatedText> favoriteTexts = queryBuilder().where().eq("isFavorite", true).query();

        return favoriteTexts;
    }

    public boolean remove(TranslatedText translatedText) throws SQLException
    {
        if (!translatedText.isInHistory && !translatedText.isFavorite)
        {
            delete(translatedText);

            return true;
        }

        update(translatedText);
        return false;
    }

    public void empty(Collection<TranslatedText> translatedTexts) throws SQLException
    {
        for (TranslatedText translatedText : translatedTexts)
        {
            remove(translatedText);
        }
    }
}
