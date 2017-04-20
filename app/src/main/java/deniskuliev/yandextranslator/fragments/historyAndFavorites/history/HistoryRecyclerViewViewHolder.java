package deniskuliev.yandextranslator.fragments.historyAndFavorites.history;

import android.view.View;

import deniskuliev.yandextranslator.R;
import deniskuliev.yandextranslator.fragments.historyAndFavorites.AbstractHistoryRecyclerViewViewHolder;
import deniskuliev.yandextranslator.translationModel.TranslatedText;
import deniskuliev.yandextranslator.translationModel.TranslationFavorites;
import deniskuliev.yandextranslator.translationModel.TranslationHistory;

class HistoryRecyclerViewViewHolder extends AbstractHistoryRecyclerViewViewHolder
{
    HistoryRecyclerViewViewHolder(View itemView)
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
                    int favoritePosition = translationFavorites.indexOf(translatedText);

                    translationFavorites.remove(favoritePosition);
                    translatedText.isFavorite = false;

                    _favoriteButton.setImageResource(R.drawable.ic_favorites_history);
                }
                else
                {
                    translatedText.isFavorite = true;

                    _favoriteButton.setImageResource(R.drawable.ic_favorites_history_activated);

                    translationFavorites.add(translatedText);
                }

                translationHistory.set(position, translatedText);
            }
        });

    }
}
