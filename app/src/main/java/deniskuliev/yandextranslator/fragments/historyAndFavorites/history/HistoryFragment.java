package deniskuliev.yandextranslator.fragments.historyAndFavorites.history;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import deniskuliev.yandextranslator.R;
import deniskuliev.yandextranslator.fragments.historyAndFavorites.HistoryAbstractFragment;

/**
 * Created by deniskuliev on 30.03.17.
 */

public class HistoryFragment extends HistoryAbstractFragment
{
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        _recyclerView = (RecyclerView) getView().findViewById(R.id.history_recycler_view);

        super.onViewCreated(view, savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        _adapter = new HistoryRecyclerViewAdapter();
        _touchHelperCallback = new HistoryTouchHelperCallback(_adapter);
        _touchHelper = new ItemTouchHelper(_touchHelperCallback);

        return inflater.inflate(R.layout.fragment_history, container, false);
    }
}