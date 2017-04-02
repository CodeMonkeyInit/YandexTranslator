package deniskuliev.yandextranslator.fragments.historyAndFavorites;

import android.support.v7.widget.RecyclerView;

import deniskuliev.yandextranslator.translation.TranslatedText;
import deniskuliev.yandextranslator.translation.TranslationList;

/**
 * Created by deniskuliev on 30.03.17.
 */

public abstract class TranslatedTextRecyclerViewAdapter extends
                                                        RecyclerView.Adapter<AbstractHistoryRecyclerViewViewHolder>
{
    protected TranslationList translatedTexts;

    public TranslatedTextRecyclerViewAdapter()
    {
        initializeTranslatedTexts();
    }

    public abstract void initializeTranslatedTexts();

    public void add(TranslatedText translatedText)
    {
        translatedTexts.add(translatedText);
    }

    public void remove(int position)
    {
        translatedTexts.remove(position);
    }

    @Override
    public void onBindViewHolder(AbstractHistoryRecyclerViewViewHolder holder, int position)
    {
        holder.bind(translatedTexts.get(position));
    }

    @Override
    public int getItemCount()
    {
        return translatedTexts.size();
    }
}
