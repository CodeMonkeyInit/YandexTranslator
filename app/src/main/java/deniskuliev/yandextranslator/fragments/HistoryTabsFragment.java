package deniskuliev.yandextranslator.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import deniskuliev.yandextranslator.R;
import deniskuliev.yandextranslator.fragments.historyAndFavorites.TranslatedTextRecyclerViewAdapter;
import deniskuliev.yandextranslator.fragments.historyAndFavorites.favorites.FavoritesFragment;
import deniskuliev.yandextranslator.fragments.historyAndFavorites.history.HistoryFragment;

public class HistoryTabsFragment extends Fragment
{
    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;

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
        super.onViewCreated(view, savedInstanceState);

        initializeFragments();

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) getView().findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) getView().findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

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
                    return _historyFragment;

                case 1:
                    return _favoritesFragment;
            }
            return null;
        }

        @Override
        public int getCount()
        {
            // Show 2 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position)
        {
            switch (position)
            {
                case 0:
                    return getString(R.string.history);
                case 1:
                    return getString(R.string.title_favorite);
            }
            return null;
        }
    }
}