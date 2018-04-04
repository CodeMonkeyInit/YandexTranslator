package deniskuliev.yandextranslator.fragments.historyAndFavorites.favorites;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import deniskuliev.yandextranslator.R;
import deniskuliev.yandextranslator.fragments.historyAndFavorites.HistoryAbstractFragment;

public class FavoritesFragment extends HistoryAbstractFragment
{
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        //noinspection ConstantConditions
        _recyclerView = (RecyclerView) getView().findViewById(R.id.favorites_recycler_view);

        super.onViewCreated(view, savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        _adapter = new FavoritesRecyclerViewAdapter();
        _touchHelperCallback = new FavoritesTouchHelperCallback(_adapter);
        _touchHelper = new ItemTouchHelper(_touchHelperCallback);

        return inflater.inflate(R.layout.fragment_favorites, container, false);
    }
}
