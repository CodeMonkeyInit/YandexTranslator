package deniskuliev.yandextranslator;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import deniskuliev.yandextranslator.fragments.HistoryTabsFragment;
import deniskuliev.yandextranslator.fragments.TranslationFragment;

public class TranslatorMain extends AppCompatActivity
{
    private FragmentManager _fragmentManager;

    private TranslationFragment _translationFragment;
    private HistoryTabsFragment _historyFragment;

    private Fragment _selectedFragment;
    private BottomNavigationView.OnNavigationItemSelectedListener OnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener()
    {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item)
        {
            switch (item.getItemId())
            {
                case R.id.navigation_translate:

                    if (_selectedFragment != _translationFragment)
                    {
                        addFragmentToContainer(_translationFragment);
                    }

                    return true;
                case R.id.navigation_favorites:

                    if (_selectedFragment != _historyFragment)
                    {
                        addFragmentToContainer(_historyFragment);
                    }
                    return true;
                case R.id.navigation_settings:
                    return true;
            }
            return false;
        }
    };

    private void addFragmentToContainer(Fragment fragment)
    {
        FragmentTransaction fragmentTransaction = _fragmentManager.beginTransaction();

        if (_selectedFragment == fragment)
        {
            return;
        }

        if (_selectedFragment != null)
        {
            fragmentTransaction.remove(_selectedFragment);
        }

        fragmentTransaction.add(R.id.translator_container, fragment);

        fragmentTransaction.commit();

        _selectedFragment = fragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _fragmentManager = getSupportFragmentManager();

        _translationFragment = new TranslationFragment();
        _historyFragment = new HistoryTabsFragment();


        addFragmentToContainer(_translationFragment);
//        _translationFragment.setHistoryAdapter(_historyFragment.getHistoryAdapter());

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(OnNavigationItemSelectedListener);
    }
}