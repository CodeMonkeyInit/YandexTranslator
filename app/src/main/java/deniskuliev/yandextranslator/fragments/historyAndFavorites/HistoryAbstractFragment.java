package deniskuliev.yandextranslator.fragments.historyAndFavorites;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

/**
 * Created by deniskuliev on 28.03.17.
 */

public abstract class HistoryAbstractFragment extends Fragment
{
    protected TranslatedTextRecyclerViewAdapter _adapter;
    protected RecyclerView.LayoutManager _layoutManager;
    protected ItemTouchHelper _touchHelper;
    protected RecyclerView _recyclerView;
    protected AbstractHistoryTouchHelperCallback _touchHelperCallback;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        _layoutManager = new LinearLayoutManager(getActivity());

        _recyclerView.setAdapter(_adapter);
        _recyclerView.setLayoutManager(_layoutManager);

        _touchHelper.attachToRecyclerView(_recyclerView);
    }

    public TranslatedTextRecyclerViewAdapter getAdapter()
    {
        return _adapter;
    }
}