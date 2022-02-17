package nl.avans.mbda.weatherapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.fasterxml.jackson.core.JsonProcessingException;

import nl.avans.mbda.weatherapp.R;
import nl.avans.mbda.weatherapp.common.apis.OpenWeatherMap;
import nl.avans.mbda.weatherapp.fragments.DetailedWeatherFragment;
import nl.avans.mbda.weatherapp.fragments.WeatherViewModel;
import nl.avans.mbda.weatherapp.models.Converter;
import nl.avans.mbda.weatherapp.models.Daily;
import nl.avans.mbda.weatherapp.models.OneCall;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getName();

    private WeatherViewModel viewModel;
    private OpenWeatherMap openWeatherMap;

    private DetailedWeatherFragment detailedWeatherFragment;
    private boolean mDualPanel = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = new ViewModelProvider(this).get(WeatherViewModel.class);
        viewModel.getSelectedItem().observe(this, this::daySelected);
        viewModel.getRefreshing().observe(this, this::refreshForecast);

        if (findViewById(R.id.frame_detailed_weather) != null) {
            mDualPanel = true;

            if (savedInstanceState == null) {
                detailedWeatherFragment = new DetailedWeatherFragment();

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.add(R.id.frame_detailed_weather, detailedWeatherFragment, "frag_detail");
                transaction.commit();
            }
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        openWeatherMap = new OpenWeatherMap(this);
        refreshForecast(true);
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

    public void refreshForecast(Boolean refreshing) {
        if (refreshing) {
            openWeatherMap.OneCall(51.4107812, 5.5583965, response -> {
                viewModel.setOneCall(response);
                viewModel.setRefreshing(false);
            }, error -> Log.e(TAG, error.getMessage(), error.getCause()));
        }
    }
}