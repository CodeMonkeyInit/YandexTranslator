package deniskuliev.yandextranslator.fragments.historyAndFavorites.history;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import deniskuliev.yandextranslator.R;
import deniskuliev.yandextranslator.fragments.historyAndFavorites.AbstractHistoryRecyclerViewViewHolder;
import deniskuliev.yandextranslator.fragments.historyAndFavorites.TranslatedTextRecyclerViewAdapter;
import deniskuliev.yandextranslator.translationModel.TranslationHistory;

class HistoryRecyclerViewAdapter extends TranslatedTextRecyclerViewAdapter
{
    HistoryRecyclerViewAdapter()
    {
        super();
    }

    @Override
    public void initializeTranslatedTexts()
    {
        translatedTexts = TranslationHistory.getInstance();
        notifyItemRangeInserted(getItemCount(), translatedTexts.size());
        translatedTexts.attachAdapter(this);
    }

    @Override
    public AbstractHistoryRecyclerViewViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View translatedCard = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_history_card, parent, false);

        return new HistoryRecyclerViewViewHolder(translatedCard);
    }
}