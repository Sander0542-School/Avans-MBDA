package nl.avans.mbda.weatherapp.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;

import nl.avans.mbda.weatherapp.R;

public class SettingsFragment extends PreferenceFragmentCompat {

    private ActivityResultLauncher<String> permissionLauncher;

    private SwitchPreferenceCompat currentLocationPreference;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        permissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
            if (isGranted) {
                currentLocationPreference.setChecked(true);
            }
        });
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);

        currentLocationPreference = findPreference("current_location");
        if (currentLocationPreference != null) {
            if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                currentLocationPreference.setChecked(false);
            }

            currentLocationPreference.setOnPreferenceChangeListener((preference, newValue) -> {
                boolean selected = (boolean) newValue;
                if (selected && ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
                    return false;
                }
                return true;
            });
        }
    }
}
