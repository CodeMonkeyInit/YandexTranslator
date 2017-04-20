package deniskuliev.yandextranslator.fragments.historyAndFavorites.favorites;

import android.support.v7.widget.helper.ItemTouchHelper;

import deniskuliev.yandextranslator.fragments.historyAndFavorites.AbstractHistoryTouchHelperCallback;
import deniskuliev.yandextranslator.fragments.historyAndFavorites.TranslatedTextRecyclerViewAdapter;

class FavoritesTouchHelperCallback extends AbstractHistoryTouchHelperCallback
{
    private static final int SWIPE_DIRECTIONS = ItemTouchHelper.RIGHT;

    FavoritesTouchHelperCallback(TranslatedTextRecyclerViewAdapter adapter)
    {
        super(SWIPE_DIRECTIONS);

        _adapter = adapter;
    }
}
