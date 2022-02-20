package nl.avans.mbda.weatherapp.fragments;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import nl.avans.mbda.weatherapp.R;

public class SettingsFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
    }
}
