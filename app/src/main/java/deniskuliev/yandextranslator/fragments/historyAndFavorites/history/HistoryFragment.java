package deniskuliev.yandextranslator.fragments.historyAndFavorites.history;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import deniskuliev.yandextranslator.R;
import deniskuliev.yandextranslator.fragments.historyAndFavorites.HistoryAbstractFragment;
import deniskuliev.yandextranslator.translationModel.TranslationHistory;

public class HistoryFragment extends HistoryAbstractFragment
{
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        final FloatingActionButton clearHistoryButton = (FloatingActionButton) view
                .findViewById(R.id.clear_history_button);

        clearHistoryButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                final AlertDialog.Builder confirmationDialogBuilder = new AlertDialog.Builder(
                        view.getContext());

                confirmationDialogBuilder.setMessage(R.string.clear_history_confirmation);

                confirmationDialogBuilder.setPositiveButton(
                        R.string.yes,
                        new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                TranslationHistory translationHistory = TranslationHistory
                                        .getInstance();

                                translationHistory.empty();
                            }
                        });

                confirmationDialogBuilder.setNegativeButton(
                        R.string.no,
                        new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                //DO nothing
                            }
                        });

                confirmationDialogBuilder.show();
            }
        });


        //noinspection ConstantConditions
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