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
        translatedTexts.set(position, translatedText);

        if (_adapter != null)
        {
            _adapter.notifyItemChanged(position);
        }
    }

    public boolean contains(TranslatedText translatedText)
    {
        return translatedTexts.contains(translatedText);
    }

    public int indexOf(TranslatedText translatedText)
    {
        return translatedTexts.indexOf(translatedText);
    }


    public TranslatedText get(int position)
    {
        return translatedTexts.get(position);
    }

    public void remove(int position)
    {
        translatedTexts.remove(position);

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
