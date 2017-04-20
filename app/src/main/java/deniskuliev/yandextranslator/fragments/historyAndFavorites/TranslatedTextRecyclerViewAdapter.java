package deniskuliev.yandextranslator.fragments.historyAndFavorites;

import android.support.v7.widget.RecyclerView;

import deniskuliev.yandextranslator.translationModel.TranslatedText;
import deniskuliev.yandextranslator.translationModel.TranslationList;

public abstract class TranslatedTextRecyclerViewAdapter extends
                                                        RecyclerView.Adapter<AbstractHistoryRecyclerViewViewHolder>
{
    protected TranslationList translatedTexts;

    @SuppressWarnings("WeakerAccess")
    public TranslatedTextRecyclerViewAdapter()
    {
        initializeTranslatedTexts();
    }

    protected abstract void initializeTranslatedTexts();

    @SuppressWarnings("unused")
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
