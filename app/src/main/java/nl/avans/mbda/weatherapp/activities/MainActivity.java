package nl.avans.mbda.weatherapp.activities;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.CancellationTokenSource;

import nl.avans.mbda.weatherapp.R;
import nl.avans.mbda.weatherapp.common.apis.OpenWeatherMap;
import nl.avans.mbda.weatherapp.databinding.ActivityMainBinding;
import nl.avans.mbda.weatherapp.fragments.DetailedWeatherFragment;
import nl.avans.mbda.weatherapp.fragments.WeatherViewModel;
import nl.avans.mbda.weatherapp.models.Converter;

public class MainActivity extends AppCompatActivity {

    private static final double DEFAULT_LATITUDE = 51.4107812;
    private static final double DEFAULT_LONGITUDE = 5.5583965;

    public static final String TAG = MainActivity.class.getName();

    private ActivityMainBinding binding;

    private CancellationTokenSource cancellationSource;

    private FusedLocationProviderClient fusedLocationClient;
    private SharedPreferences preferences;

    private WeatherViewModel viewModel;
    private OpenWeatherMap openWeatherMap;

    private DetailedWeatherFragment detailedWeatherFragment;
    private boolean mDualPanel = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        cancellationSource = new CancellationTokenSource();

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        viewModel = new ViewModelProvider(this).get(WeatherViewModel.class);
        viewModel.getSelectedItem().observe(this, this::daySelected);
        viewModel.getRefreshing().observe(this, this::refreshForecast);

        if (binding.content.frameDetailedWeather != null) {
            mDualPanel = true;

            if (savedInstanceState == null) {
                detailedWeatherFragment = new DetailedWeatherFragment();

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.add(R.id.frame_detailed_weather, detailedWeatherFragment, "frag_detail");
                transaction.commit();
            }
        }

        setSupportActionBar(binding.toolbar);

        openWeatherMap = new OpenWeatherMap(this);
        refreshForecast(true);
    }

    @Override
    protected void onStop() {
        super.onStop();

        cancellationSource.cancel();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void daySelected(Integer selectedItem) {
        if (!mDualPanel) {
            try {
                Intent intent = new Intent(this, DetailedWeatherActivity.class);
                intent.putExtra(DetailedWeatherActivity.NAME_ONE_CALL, Converter.toJsonString(viewModel.getOneCall().getValue()));
                intent.putExtra(DetailedWeatherActivity.NAME_SELECTED_ITEM, selectedItem);

                startActivity(intent);
            } catch (JsonProcessingException e) {
                Log.e(TAG, "Could not parse JSON", e);
            }
        }
    }

    private void refreshForecast(Boolean refreshing) {
        if (refreshing) {
            if (preferences.getBoolean("current_location", false) && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                fusedLocationClient.getCurrentLocation(LocationRequest.PRIORITY_HIGH_ACCURACY, cancellationSource.getToken()).addOnSuccessListener(location -> {
                    if (location != null) {
                        requestWeather(location.getLatitude(), location.getLongitude());
                    } else {
                        requestWeather();
                    }
                });
            } else {
                requestWeather();
            }
        }
    }

    private void requestWeather() {
        String latitude = preferences.getString("latitude", String.valueOf(DEFAULT_LATITUDE));
        String longitude = preferences.getString("longitude", String.valueOf(DEFAULT_LONGITUDE));
        try {
            requestWeather(Double.parseDouble(latitude), Double.parseDouble(longitude));
        } catch (NumberFormatException e) {
            Toast.makeText(this, getString(R.string.invalid_location), Toast.LENGTH_LONG).show();
            requestWeather(DEFAULT_LATITUDE, DEFAULT_LONGITUDE);
        }
    }

    private void requestWeather(double lat, double lon) {
        openWeatherMap.OneCall(lat, lon, response -> {
            viewModel.setOneCall(response);
            viewModel.setRefreshing(false);
        }, error -> Log.e(TAG, error.getMessage(), error.getCause()));
    }
}