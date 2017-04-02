package deniskuliev.yandextranslator.fragments.historyAndFavorites;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import deniskuliev.yandextranslator.R;
import deniskuliev.yandextranslator.translation.TranslatedText;

/**
 * Created by deniskuliev on 29.03.17.
 */

public abstract class AbstractHistoryRecyclerViewViewHolder extends RecyclerView.ViewHolder
{
    protected ImageButton _favoriteButton;
    private TextView _originalText;
    private TextView _translatedText;

    public AbstractHistoryRecyclerViewViewHolder(View itemView)
    {
        super(itemView);

        _originalText = (TextView) itemView.findViewById(R.id.history_original_text);
        _translatedText = (TextView) itemView.findViewById(R.id.history_translated_text);

        _favoriteButton = (ImageButton) itemView.findViewById(R.id.history_favorite_button);

        attachImageButtonHandler();
    }

    protected abstract void attachImageButtonHandler();

    public void bind(TranslatedText translatedText)
    {
        _originalText.setText(translatedText.original);
        _translatedText.setText(translatedText.translated);

        if (translatedText.isFavorite)
        {
            _favoriteButton.setImageResource(R.drawable.ic_favorites_history_activated);
        }
        else
        {
            _favoriteButton.setImageResource(R.drawable.ic_favorites_history);
        }
    }
}
