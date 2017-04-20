package deniskuliev.yandextranslator.fragments.historyAndFavorites.history;

import android.support.v7.widget.helper.ItemTouchHelper;

import deniskuliev.yandextranslator.fragments.historyAndFavorites.AbstractHistoryTouchHelperCallback;
import deniskuliev.yandextranslator.fragments.historyAndFavorites.TranslatedTextRecyclerViewAdapter;

class HistoryTouchHelperCallback extends AbstractHistoryTouchHelperCallback
{
    HistoryTouchHelperCallback(TranslatedTextRecyclerViewAdapter adapter)
    {
        super(ItemTouchHelper.LEFT);

        _adapter = adapter;
    }
}
