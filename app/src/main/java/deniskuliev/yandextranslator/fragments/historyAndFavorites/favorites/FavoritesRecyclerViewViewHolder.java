package deniskuliev.yandextranslator.fragments.historyAndFavorites.favorites;

import android.view.View;

import deniskuliev.yandextranslator.fragments.historyAndFavorites.AbstractHistoryRecyclerViewViewHolder;
import deniskuliev.yandextranslator.translationModel.TranslationFavorites;

/**
 * Created by deniskuliev on 30.03.17.
 */

class FavoritesRecyclerViewViewHolder extends AbstractHistoryRecyclerViewViewHolder
{

    public FavoritesRecyclerViewViewHolder(View itemView)
    {
        super(itemView);
    }

    @Override
    protected void attachImageButtonHandler()
    {
        _favoriteButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int position = getAdapterPosition();
                TranslationFavorites translationFavorites = TranslationFavorites.getInstance();

                translationFavorites.remove(position);
            }
        });
    }
}
