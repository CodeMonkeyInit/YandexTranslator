package deniskuliev.yandextranslator.fragments.historyAndFavorites;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

public abstract class HistoryAbstractFragment extends Fragment
{
    protected TranslatedTextRecyclerViewAdapter _adapter;
    @SuppressWarnings("WeakerAccess")
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
}