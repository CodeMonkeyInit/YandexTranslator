package deniskuliev.yandextranslator.fragments.historyAndFavorites.favorites;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import deniskuliev.yandextranslator.R;
import deniskuliev.yandextranslator.fragments.historyAndFavorites.AbstractHistoryRecyclerViewViewHolder;
import deniskuliev.yandextranslator.fragments.historyAndFavorites.TranslatedTextRecyclerViewAdapter;
import deniskuliev.yandextranslator.translationModel.TranslationFavorites;


class FavoritesRecyclerViewAdapter extends TranslatedTextRecyclerViewAdapter
{
    FavoritesRecyclerViewAdapter()
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
