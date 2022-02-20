package nl.avans.mbda.weatherapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.io.IOException;

import nl.avans.mbda.weatherapp.R;
import nl.avans.mbda.weatherapp.fragments.WeatherViewModel;
import nl.avans.mbda.weatherapp.models.Converter;
import nl.avans.mbda.weatherapp.models.OneCall;

public class DetailedWeatherActivity extends AppCompatActivity {

    public static final String NAME_ONE_CALL = "OneCall";
    public static final String NAME_SELECTED_ITEM = "SelectedItem";

    private WeatherViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_weather);

        viewModel = new ViewModelProvider(this).get(WeatherViewModel.class);

        try {
            viewModel.setOneCall(Converter.fromJsonString(getIntent().getStringExtra(NAME_ONE_CALL), OneCall.class));
            viewModel.setSelectedItem(getIntent().getIntExtra(NAME_SELECTED_ITEM, 0));
        } catch (IOException e) {
            finish();
            return;
        }

        setSupportActionBar(findViewById(R.id.toolbar));
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}