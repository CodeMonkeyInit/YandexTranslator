package deniskuliev.yandextranslator.translationModel;

import com.google.common.collect.Lists;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by deniskuliev on 30.03.17.
 */
public class TranslationHistory extends TranslationList
{
    private static TranslationHistory instance;

    private TranslationHistory()
    {
        super();

    }

    public static TranslationHistory getInstance()
    {
        if (instance == null)
        {
            instance = new TranslationHistory();
        }

        return instance;
    }

    @Override
    protected void initializeCollections()
    {
        List<TranslatedText> translatedHistoryTexts = null;

        translatedTexts = new ArrayList<TranslatedText>();
        reversedTranslatedTexts = Lists.reverse(translatedTexts);

        try
        {
            translatedHistoryTexts = historyDAO.getTranslationHistory();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        if (translatedHistoryTexts != null)
        {
            for (TranslatedText translatedText : translatedHistoryTexts)
            {
                translatedTexts.add(translatedText);
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

        translatedText.isInHistory = true;

        try
        {
            historyDAO.create(translatedText);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        super.add(translatedText);
    }

    public void empty()
    {
        for (TranslatedText translatedText : translatedTexts)
        {
            translatedText.isInHistory = false;
        }

        try
        {
            historyDAO.empty(translatedTexts);
            initializeCollections();

            if (_adapter != null)
            {
                _adapter.notifyDataSetChanged();
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void remove(int position)
    {
        try
        {
            TranslatedText translatedText = get(position);

            translatedText.isInHistory = false;

            historyDAO.remove(translatedText);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        super.remove(position);
    }

    @Override
    public void set(int position, TranslatedText translatedText)
    {
        super.set(position, translatedText);

        try
        {
            historyDAO.update(translatedText);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public HistoryDAO getHistoryDAO()
    {
        return historyDAO;
    }

}

