package deniskuliev.yandextranslator.fragments.historyAndFavorites;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

public abstract class AbstractHistoryTouchHelperCallback extends ItemTouchHelper.SimpleCallback
{
    private final static int DRAG_DIRECTIONS = 0;
    protected TranslatedTextRecyclerViewAdapter _adapter;


    @SuppressWarnings("WeakerAccess")
    public AbstractHistoryTouchHelperCallback(int swipeDirections)
    {
        super(DRAG_DIRECTIONS, swipeDirections);
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