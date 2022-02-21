package nl.avans.mbda.weatherapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.fasterxml.jackson.core.JsonProcessingException;

import nl.avans.mbda.weatherapp.R;
import nl.avans.mbda.weatherapp.common.Constants;
import nl.avans.mbda.weatherapp.common.apis.OpenWeatherMap;
import nl.avans.mbda.weatherapp.databinding.ActivityForecastBinding;
import nl.avans.mbda.weatherapp.fragments.DetailedWeatherFragment;
import nl.avans.mbda.weatherapp.viewmodels.ForecastViewModel;
import nl.avans.mbda.weatherapp.models.Converter;

public class ForecastActivity extends AppCompatActivity {

    public static final String TAG = ForecastActivity.class.getName();

    public static final String NAME_LATITUDE = "latitude";
    public static final String NAME_LONGITUDE = "longitude";

    private ActivityForecastBinding binding;

    private double latitude;
    private double longitude;

    private ForecastViewModel viewModel;
    private OpenWeatherMap openWeatherMap;

    private DetailedWeatherFragment detailedWeatherFragment;
    private boolean mDualPanel = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForecastBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        latitude = getIntent().getDoubleExtra(NAME_LATITUDE, Constants.DEFAULT_LATITUDE);
        longitude = getIntent().getDoubleExtra(NAME_LONGITUDE, Constants.DEFAULT_LONGITUDE);

        viewModel = new ViewModelProvider(this).get(ForecastViewModel.class);
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
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        openWeatherMap = new OpenWeatherMap(this);
        refreshForecast(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
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
            requestOneCall();
        }
    }

    private void requestOneCall() {
        openWeatherMap.OneCall(latitude, longitude, oneCall -> {
            viewModel.setOneCall(oneCall);
            viewModel.setRefreshing(false);
        }, error -> Log.e(TAG, error.getMessage(), error.getCause()));
    }
}