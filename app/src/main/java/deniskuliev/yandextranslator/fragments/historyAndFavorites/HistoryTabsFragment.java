package deniskuliev.yandextranslator.fragments.historyAndFavorites;

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
import deniskuliev.yandextranslator.customViews.CustomViewPager;
import deniskuliev.yandextranslator.customViews.SwipeDirection;
import deniskuliev.yandextranslator.fragments.historyAndFavorites.favorites.FavoritesFragment;
import deniskuliev.yandextranslator.fragments.historyAndFavorites.history.HistoryFragment;

public class HistoryTabsFragment extends Fragment
{
    private final static int FRAGMENTS_COUNT = 2;

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
        super.onViewCreated(view, savedInstanceState);

        initializeFragments();

        SectionsPagerAdapter _sectionsPagerAdapter = new SectionsPagerAdapter(
                getChildFragmentManager());

        //noinspection ConstantConditions
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

        TabLayout tabLayout = (TabLayout) getView().findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(_viewPager);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_history_tabs, container, false);
    }

    private class SectionsPagerAdapter extends FragmentPagerAdapter
    {
        SectionsPagerAdapter(FragmentManager fm)
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