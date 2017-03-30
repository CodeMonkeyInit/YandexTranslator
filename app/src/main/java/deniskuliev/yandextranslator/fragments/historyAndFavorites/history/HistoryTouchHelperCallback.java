package deniskuliev.yandextranslator.fragments.historyAndFavorites.history;

import android.support.v7.widget.helper.ItemTouchHelper;

import deniskuliev.yandextranslator.fragments.historyAndFavorites.AbstractHistoryTouchHelperCallback;
import deniskuliev.yandextranslator.fragments.historyAndFavorites.TranslatedTextRecyclerViewAdapter;

/**
 * Created by deniskuliev on 30.03.17.
 */

class HistoryTouchHelperCallback extends AbstractHistoryTouchHelperCallback
{
    public HistoryTouchHelperCallback(TranslatedTextRecyclerViewAdapter adapter)
    {
        super(DRAG_DIRECTIONS, ItemTouchHelper.RIGHT);

        _adapter = adapter;
    }
}
