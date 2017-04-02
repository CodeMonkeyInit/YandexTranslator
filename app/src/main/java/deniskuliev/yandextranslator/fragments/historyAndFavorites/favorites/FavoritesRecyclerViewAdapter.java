package deniskuliev.yandextranslator.fragments.historyAndFavorites.favorites;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import deniskuliev.yandextranslator.R;
import deniskuliev.yandextranslator.fragments.historyAndFavorites.AbstractHistoryRecyclerViewViewHolder;
import deniskuliev.yandextranslator.fragments.historyAndFavorites.TranslatedTextRecyclerViewAdapter;
import deniskuliev.yandextranslator.translation.TranslationFavorites;

/**
 * Created by deniskuliev on 30.03.17.
 */

class FavoritesRecyclerViewAdapter extends TranslatedTextRecyclerViewAdapter
{
    public FavoritesRecyclerViewAdapter()
    {
        super();
    }

    @Override
    public void initializeTranslatedTexts()
    {
        translatedTexts = TranslationFavorites.getInstance();
        translatedTexts.attachAdapter(this);
        notifyItemRangeInserted(getItemCount(), translatedTexts.size());
    }

    @Override
    public AbstractHistoryRecyclerViewViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View translatedCard = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_history_card, parent, false);

        return new FavoritesRecyclerViewViewHolder(translatedCard);
    }
}
