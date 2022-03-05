package nl.avans.mbda.weatherapp.activities;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.CancellationTokenSource;

import nl.avans.mbda.weatherapp.R;
import nl.avans.mbda.weatherapp.common.Constants;
import nl.avans.mbda.weatherapp.common.apis.OpenWeatherMap;
import nl.avans.mbda.weatherapp.common.utils.NetworkUtil;
import nl.avans.mbda.weatherapp.databinding.ActivityMainBinding;
import nl.avans.mbda.weatherapp.viewmodels.WeatherViewModel;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getName();

    private CancellationTokenSource cancellationSource;

    private FusedLocationProviderClient fusedLocationClient;
    private SharedPreferences preferences;

    private WeatherViewModel viewModel;
    private OpenWeatherMap openWeatherMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        nl.avans.mbda.weatherapp.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        cancellationSource = new CancellationTokenSource();

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        viewModel = new ViewModelProvider(this).get(WeatherViewModel.class);
        viewModel.getRefreshing().observe(this, this::refreshWeather);

        binding.noInternet.setOnRefreshListener(() -> refreshWeather(true));

        setSupportActionBar(binding.toolbar);

        openWeatherMap = new OpenWeatherMap(this);
        refreshWeather(true);
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
        if (item.getItemId() == R.id.menu_item_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void refreshWeather(Boolean refreshing) {
        binding.noInternet.setRefreshing(refreshing);
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
        String latitude = preferences.getString("latitude", String.valueOf(Constants.DEFAULT_LATITUDE));
        String longitude = preferences.getString("longitude", String.valueOf(Constants.DEFAULT_LONGITUDE));
        try {
            requestWeather(Double.parseDouble(latitude), Double.parseDouble(longitude));
        } catch (NumberFormatException e) {
            Toast.makeText(this, getString(R.string.invalid_location), Toast.LENGTH_LONG).show();
            requestWeather(Constants.DEFAULT_LATITUDE, Constants.DEFAULT_LONGITUDE);
        }
    }

    private void requestWeather(double lat, double lon) {
        if (NetworkUtil.isConnected(this)) {
            binding.noInternet.setVisibility(View.GONE);
            binding.fragmentCurrentWeather.setVisibility(View.VISIBLE);
            openWeatherMap.Current(lat, lon, current -> {
                viewModel.setCurrent(current);
                viewModel.setRefreshing(false);
            }, error -> {
                viewModel.setRefreshing(false);
                Toast.makeText(this, getString(R.string.not_loaded), Toast.LENGTH_LONG).show();
                Log.e(TAG, error.getMessage(), error.getCause());
            });
        } else {
            binding.fragmentCurrentWeather.setVisibility(View.GONE);
            binding.noInternet.setVisibility(View.VISIBLE);
            viewModel.setRefreshing(false);
        }
    }
}