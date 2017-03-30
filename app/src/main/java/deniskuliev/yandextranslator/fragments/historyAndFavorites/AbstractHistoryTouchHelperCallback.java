package deniskuliev.yandextranslator.fragments.historyAndFavorites;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * Created by deniskuliev on 29.03.17.
 */

public abstract class AbstractHistoryTouchHelperCallback extends ItemTouchHelper.SimpleCallback
{
    protected final static int DRAG_DIRECTIONS = 0;
    protected TranslatedTextRecyclerViewAdapter _adapter;


    public AbstractHistoryTouchHelperCallback(int dragDirections, int swipeDirections)
    {
        super(dragDirections, swipeDirections);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target)
    {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction)
    {
        _adapter.remove(viewHolder.getAdapterPosition());
    }
}