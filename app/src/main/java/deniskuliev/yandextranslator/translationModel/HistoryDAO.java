package deniskuliev.yandextranslator.translationModel;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

@SuppressWarnings("WeakerAccess")
public class HistoryDAO extends BaseDaoImpl<TranslatedText, Integer>
{
    HistoryDAO(ConnectionSource connectionSource, Class<TranslatedText> dataClass) throws
                                                                                   SQLException
    {
        super(connectionSource, dataClass);
    }

    public List<TranslatedText> getTranslationHistory() throws SQLException
    {

        return queryBuilder().where().eq("isInHistory", true).query();
    }

    public List<TranslatedText> getFavorites() throws SQLException
    {

        return queryBuilder().where().eq("isFavorite", true).query();
    }

    @SuppressWarnings("UnusedReturnValue")
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
