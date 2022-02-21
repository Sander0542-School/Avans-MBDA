package nl.avans.mbda.weatherapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.MenuItem;

import java.io.IOException;

import nl.avans.mbda.weatherapp.databinding.ActivityDetailedWeatherBinding;
import nl.avans.mbda.weatherapp.viewmodels.ForecastViewModel;
import nl.avans.mbda.weatherapp.models.Converter;
import nl.avans.mbda.weatherapp.models.onecall.OneCall;

public class DetailedWeatherActivity extends AppCompatActivity {

    public static final String NAME_ONE_CALL = "OneCall";
    public static final String NAME_SELECTED_ITEM = "SelectedItem";

    private ActivityDetailedWeatherBinding binding;

    private ForecastViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailedWeatherBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(ForecastViewModel.class);

        try {
            viewModel.setOneCall(Converter.fromJsonString(getIntent().getStringExtra(NAME_ONE_CALL), OneCall.class));
            viewModel.setSelectedItem(getIntent().getIntExtra(NAME_SELECTED_ITEM, 0));
        } catch (IOException e) {
            finish();
            return;
        }

        setSupportActionBar(binding.toolbar);
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