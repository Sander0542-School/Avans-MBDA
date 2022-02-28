package nl.avans.mbda.weatherapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import nl.avans.mbda.weatherapp.R;
import nl.avans.mbda.weatherapp.activities.ForecastActivity;
import nl.avans.mbda.weatherapp.common.caches.LruBitmapCache;
import nl.avans.mbda.weatherapp.databinding.FragmentCurrentWeatherBinding;
import nl.avans.mbda.weatherapp.models.current.Current;
import nl.avans.mbda.weatherapp.viewmodels.WeatherViewModel;

public class CurrentWeatherFragment extends Fragment {

    private static final String FORMAT_IMAGE = "https://openweathermap.org/img/wn/%s@2x.png";

    private FragmentCurrentWeatherBinding binding;

    private WeatherViewModel viewModel;
    private ImageLoader imageLoader;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCurrentWeatherBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (imageLoader == null) {
            imageLoader = new ImageLoader(Volley.newRequestQueue(getContext()), new LruBitmapCache());
        }

        viewModel = new ViewModelProvider(requireActivity()).get(WeatherViewModel.class);
        viewModel.getRefreshing().observe(getViewLifecycleOwner(), refreshing -> binding.getRoot().setRefreshing(refreshing));
        viewModel.getCurrent().observe(getViewLifecycleOwner(), this::updateWeather);
        Current current = viewModel.getCurrent().getValue();
        if (current != null) {
            updateWeather(current);
        }

        binding.getRoot().setOnRefreshListener(() -> viewModel.setRefreshing(true));

        binding.forecastButton.setOnClickListener(button -> {
            Current current1 = viewModel.getCurrent().getValue();
            if (current1 != null) {
                Intent intent = new Intent(getActivity(), ForecastActivity.class);
                intent.putExtra(ForecastActivity.NAME_LATITUDE, current1.getCoord().getLat());
                intent.putExtra(ForecastActivity.NAME_LONGITUDE, current1.getCoord().getLon());

                startActivity(intent);
            }
        });
    }

    private void updateWeather(Current current) {
        binding.weatherIcon.setImageUrl(String.format(FORMAT_IMAGE, current.getWeather().get(0).getIcon()), imageLoader);
        binding.weatherTemp.setText(getContext().getString(R.string.weather_temp, current.getMain().getTemp()));
        binding.weatherLocation.setText(current.getName());
        binding.weatherFeelsLike.setText(getContext().getString(R.string.weather_feels_like, current.getMain().getFeelsLike()));
        binding.weatherTempMin.setText(getContext().getString(R.string.weather_temp_min, current.getMain().getTempMin()));
        binding.weatherTempMax.setText(getContext().getString(R.string.weather_temp_max, current.getMain().getTempMax()));
        binding.locationLatLon.setText(getContext().getString(R.string.location_lat_lon, current.getCoord().getLat(), current.getCoord().getLon()));
    }
}