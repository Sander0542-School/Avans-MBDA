package nl.avans.mbda.weatherapp.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import nl.avans.mbda.weatherapp.R;
import nl.avans.mbda.weatherapp.adapters.DailyWeatherAdapter;
import nl.avans.mbda.weatherapp.common.Formats;
import nl.avans.mbda.weatherapp.databinding.FragmentDetailedWeatherBinding;
import nl.avans.mbda.weatherapp.models.onecall.Daily;
import nl.avans.mbda.weatherapp.viewmodels.ForecastViewModel;

public class DetailedWeatherFragment extends Fragment {

    private ForecastViewModel viewModel;
    private FragmentDetailedWeatherBinding binding;
    private Daily daily;
    private SharedPreferences preferences;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentDetailedWeatherBinding.inflate(inflater, container, false);
        preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(ForecastViewModel.class);
        viewModel.getSelectedItem().observe(getViewLifecycleOwner(), selectedItem -> {
            daily = viewModel.getOneCall().getValue().getDaily().get(selectedItem);

            Resources resources = view.getResources();
            Locale locale = resources.getConfiguration().getLocales().get(0);

            binding.weatherDay.setText(new SimpleDateFormat(Formats.FORMAT_DAY_FULL, locale).format(new Date(daily.getDt() * 1000)));
            binding.temperatureTextView.setText(String.format(locale, Formats.FORMAT_TEMP_EMPTY, daily.getTemp().getDay()));
            binding.humidityTextView.setText(String.format(locale, Formats.FORMAT_HUMIDITY, daily.getHumidity()));
            binding.minMaxTextView.setText(String.format(locale, Formats.FORMAT_MIN_MAX_TEMP, daily.getTemp().getMin(), daily.getTemp().getMax()));

            Button button = binding.button;
            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    String lat  = preferences.getString("latitude", null);
                    String lon  = preferences.getString("longitude", null);
                    String uri = String.format("google.streetview:cbll=%s,%s", lat, lon);
                    Uri gmmIntentUri = Uri.parse(uri);
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    startActivity(mapIntent);
                }
            });
        });

    }
}