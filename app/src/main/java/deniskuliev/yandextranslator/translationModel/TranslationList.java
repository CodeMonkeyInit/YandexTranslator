package deniskuliev.yandextranslator.translationModel;

import java.sql.SQLException;
import java.util.List;

import deniskuliev.yandextranslator.fragments.historyAndFavorites.TranslatedTextRecyclerViewAdapter;

/**
 * Created by deniskuliev on 30.03.17.
 */

public abstract class TranslationList
{
    protected TranslatedTextRecyclerViewAdapter _adapter;

    protected List<TranslatedText> translatedTexts;
    protected List<TranslatedText> reversedTranslatedTexts;

    protected HistoryDAO historyDAO;

    public TranslationList()
    {
        initializeDataAccessObject();
        initializeCollections();
    }

    protected abstract void initializeCollections();

    protected void initializeDataAccessObject()
    {
        try
        {
            historyDAO = DatabaseHelperFactory.getDatabaseHelper().getHistoryDAO();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public int size()
    {
        return translatedTexts.size();
    }

    public void add(TranslatedText translatedText)
    {
        translatedTexts.add(translatedText);

        if (_adapter != null)
        {
            _adapter.notifyDataSetChanged();
        }
    }

    public void set(int position, TranslatedText translatedText)
    {
        reversedTranslatedTexts.set(position, translatedText);

        if (_adapter != null)
        {
            _adapter.notifyItemChanged(position);
        }
    }

    public boolean contains(TranslatedText translatedText)
    {
        return reversedTranslatedTexts.contains(translatedText);
    }

    public int indexOf(TranslatedText translatedText)
    {
        return reversedTranslatedTexts.indexOf(translatedText);
    }

    public TranslatedText get(int position)
    {
        return reversedTranslatedTexts.get(position);
    }

    public void remove(int position)
    {
        reversedTranslatedTexts.remove(position);

        if (_adapter != null)
        {
            _adapter.notifyItemRemoved(position);
        }
    }

    public void attachAdapter(TranslatedTextRecyclerViewAdapter adapter)
    {
        _adapter = adapter;
    }

    public void detachAdapter()
    {
        _adapter = null;
    }
}
