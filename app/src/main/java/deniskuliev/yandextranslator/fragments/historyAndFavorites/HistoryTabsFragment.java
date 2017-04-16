package deniskuliev.yandextranslator.fragments.historyAndFavorites;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import deniskuliev.yandextranslator.R;
import deniskuliev.yandextranslator.customViews.CustomViewPager;
import deniskuliev.yandextranslator.customViews.SwipeDirection;
import deniskuliev.yandextranslator.fragments.historyAndFavorites.favorites.FavoritesFragment;
import deniskuliev.yandextranslator.fragments.historyAndFavorites.history.HistoryFragment;
import deniskuliev.yandextranslator.translationModel.TranslationHistory;

public class HistoryTabsFragment extends Fragment
{
    private final static int FRAGMENTS_COUNT = 2;
    private SectionsPagerAdapter _sectionsPagerAdapter;

    private CustomViewPager _viewPager;

    private HistoryFragment _historyFragment;
    private FavoritesFragment _favoritesFragment;


    private void initializeFragments()
    {
        _historyFragment = new HistoryFragment();
        _favoritesFragment = new FavoritesFragment();

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        final FloatingActionButton clearHistoryButton = (FloatingActionButton) view
                .findViewById(R.id.clear_history_button);

        super.onViewCreated(view, savedInstanceState);

        initializeFragments();

        _sectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager());

        _viewPager = (CustomViewPager) getView().findViewById(R.id.container);
        _viewPager.setAdapter(_sectionsPagerAdapter);
        _viewPager.setAllowedDirection(SwipeDirection.right);

        _viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
            {
            }

            @Override
            public void onPageSelected(int position)
            {
                switch (position)
                {
                    case 0:
                        _viewPager.setAllowedDirection(SwipeDirection.right);
                        break;

                    case 1:
                        _viewPager.setAllowedDirection(SwipeDirection.left);
                        break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state)
            {

            }
        });

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

        TabLayout tabLayout = (TabLayout) getView().findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(_viewPager);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_history_tabs, container, false);
    }

    public TranslatedTextRecyclerViewAdapter getHistoryAdapter()
    {
        return _historyFragment.getAdapter();
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter
    {
        public SectionsPagerAdapter(FragmentManager fm)
        {
            super(fm);
        }

        @Override
        public Fragment getItem(int position)
        {
            switch (position)
            {
                case 0:
                    return _favoritesFragment;

                case 1:
                    return _historyFragment;
            }
            return null;
        }

        @Override
        public int getCount()
        {
            return FRAGMENTS_COUNT;
        }

        @Override
        public CharSequence getPageTitle(int position)
        {
            switch (position)
            {
                case 0:
                    return getString(R.string.title_favorite);

                case 1:
                    return getString(R.string.history);
            }
            return null;
        }
    }
}