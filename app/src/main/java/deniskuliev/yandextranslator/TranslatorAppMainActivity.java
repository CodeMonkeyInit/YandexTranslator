package deniskuliev.yandextranslator;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import deniskuliev.yandextranslator.fragments.historyAndFavorites.HistoryTabsFragment;
import deniskuliev.yandextranslator.fragments.settings.SettingsFragment;
import deniskuliev.yandextranslator.fragments.translation.TranslationFragment;
import deniskuliev.yandextranslator.translationModel.DatabaseHelperFactory;

public class TranslatorAppMainActivity extends AppCompatActivity
{
    private FragmentManager _fragmentManager;

    private TranslationFragment _translationFragment;
    private HistoryTabsFragment _historyFragment;
    private SettingsFragment _settingsFragment;

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

                    if (_selectedFragment != _settingsFragment)
                    {
                        addFragmentToContainer(_settingsFragment);
                    }

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

        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

        fragmentTransaction.replace(R.id.translator_container, fragment);

        fragmentTransaction.commit();

        _selectedFragment = fragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translation_app_main);

        _fragmentManager = getSupportFragmentManager();

        _translationFragment = new TranslationFragment();
        _historyFragment = new HistoryTabsFragment();
        _settingsFragment = new SettingsFragment();

        addFragmentToContainer(_translationFragment);

        DatabaseHelperFactory.setDatabaseHelper(getApplicationContext());

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(OnNavigationItemSelectedListener);
    }
}