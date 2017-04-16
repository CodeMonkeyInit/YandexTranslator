package deniskuliev.yandextranslator.fragments.historyAndFavorites.favorites;

import android.support.v7.widget.helper.ItemTouchHelper;

import deniskuliev.yandextranslator.fragments.historyAndFavorites.AbstractHistoryTouchHelperCallback;
import deniskuliev.yandextranslator.fragments.historyAndFavorites.TranslatedTextRecyclerViewAdapter;

/**
 * Created by deniskuliev on 30.03.17.
 */

class FavoritesTouchHelperCallback extends AbstractHistoryTouchHelperCallback
{
    private static final int SWIPE_DIRECTIONS = ItemTouchHelper.RIGHT;

    public FavoritesTouchHelperCallback(TranslatedTextRecyclerViewAdapter adapter)
    {
        super(DRAG_DIRECTIONS, SWIPE_DIRECTIONS);

        _adapter = adapter;
    }
}
