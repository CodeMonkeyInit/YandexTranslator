package deniskuliev.yandextranslator.fragments.historyAndFavorites.history;

import android.view.View;

import deniskuliev.yandextranslator.R;
import deniskuliev.yandextranslator.fragments.historyAndFavorites.AbstractHistoryRecyclerViewViewHolder;
import deniskuliev.yandextranslator.translation.TranslatedText;
import deniskuliev.yandextranslator.translation.TranslationFavorites;
import deniskuliev.yandextranslator.translation.TranslationHistory;

/**
 * Created by deniskuliev on 30.03.17.
 */

class HistoryRecyclerViewViewHolder extends AbstractHistoryRecyclerViewViewHolder
{
    public HistoryRecyclerViewViewHolder(View itemView)
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
                TranslationHistory translationHistory = TranslationHistory.getInstance();
                TranslationFavorites translationFavorites = TranslationFavorites.getInstance();
                TranslatedText translatedText = translationHistory.get(position);

                if (translatedText.isFavorite)
                {
                    int favoritePostion = translationFavorites.indexOf(translatedText);

                    translationFavorites.remove(favoritePostion);
                    translatedText.isFavorite = false;
                    _favoriteButton.setImageResource(R.drawable.ic_favorites_history);
                }
                else
                {
                    translationFavorites.add(translatedText);
                    translatedText.isFavorite = true;
                    _favoriteButton.setImageResource(R.drawable.ic_favorites_history_activated);
                }

                translationHistory.set(position, translatedText);
            }
        });

    }
}
