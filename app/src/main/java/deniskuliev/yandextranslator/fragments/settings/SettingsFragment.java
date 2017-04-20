package deniskuliev.yandextranslator.fragments.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import deniskuliev.yandextranslator.R;

public class SettingsFragment extends Fragment
{
    public final static String SETTINGS_TAG = "app_settings";

    public final static String LANGUAGE_AUTODETECT_TAG = "autodetect_language";

    private void restoreSwitches(View view)
    {
        SharedPreferences sharedPreferences = getActivity()
                .getSharedPreferences(SETTINGS_TAG, Context.MODE_PRIVATE);

        Switch autoDetectSwitch = (Switch) view.findViewById(R.id.detect_language_switch);

        Boolean autodetectLanguage = sharedPreferences.getBoolean(LANGUAGE_AUTODETECT_TAG, false);

        autoDetectSwitch.setChecked(autodetectLanguage);
    }

    private void setSwitchesHandlers(View view)
    {
        Switch autoDetectSwitch = (Switch) view.findViewById(R.id.detect_language_switch);

        autoDetectSwitch.setOnCheckedChangeListener(new AutoDetectSwitchChangeListener());
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        restoreSwitches(view);

        setSwitchesHandlers(view);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    private class AutoDetectSwitchChangeListener implements CompoundButton.OnCheckedChangeListener
    {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
        {
            SharedPreferences.Editor sharedPreferencesEdit = getActivity()
                    .getSharedPreferences(SETTINGS_TAG, Context.MODE_PRIVATE).edit();

            sharedPreferencesEdit.putBoolean(LANGUAGE_AUTODETECT_TAG, isChecked);

            sharedPreferencesEdit.apply();
        }
    }
}
