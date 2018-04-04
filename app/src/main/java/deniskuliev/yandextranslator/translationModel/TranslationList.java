package deniskuliev.yandextranslator.translationModel;

import java.sql.SQLException;
import java.util.List;

import deniskuliev.yandextranslator.fragments.historyAndFavorites.TranslatedTextRecyclerViewAdapter;

public abstract class TranslationList
{
    @SuppressWarnings("WeakerAccess")
    protected HistoryDAO historyDAO;

    @SuppressWarnings("WeakerAccess")
    protected TranslatedTextRecyclerViewAdapter _adapter;

    @SuppressWarnings("WeakerAccess")
    protected List<TranslatedText> translatedTexts;
    @SuppressWarnings("WeakerAccess")
    protected List<TranslatedText> reversedTranslatedTexts;

    @SuppressWarnings("WeakerAccess")
    public TranslationList()
    {
        initializeDataAccessObject();
        initializeCollections();
    }

    private void initializeDataAccessObject()
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

    protected abstract void initializeCollections();

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

    @SuppressWarnings("WeakerAccess")
    public void set(int position, TranslatedText translatedText)
    {
        reversedTranslatedTexts.set(position, translatedText);

        if (_adapter != null)
        {
            _adapter.notifyItemChanged(position);
        }
    }

    @SuppressWarnings("WeakerAccess")
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

    @SuppressWarnings("WeakerAccess")
    public void empty()
    {
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

    public void attachAdapter(TranslatedTextRecyclerViewAdapter adapter)
    {
        _adapter = adapter;
    }

    @SuppressWarnings("unused")
    public void detachAdapter()
    {
        _adapter = null;
    }
}
