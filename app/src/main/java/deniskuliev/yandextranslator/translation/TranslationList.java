package deniskuliev.yandextranslator.translation;

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

    public TranslationList()
    {
        initializeLists();
    }

    protected abstract void initializeLists();

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
}
