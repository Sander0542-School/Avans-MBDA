package nl.avans.mbda.weatherapp.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.io.IOException;

import nl.avans.mbda.weatherapp.R;
import nl.avans.mbda.weatherapp.common.utils.BackgroundUtil;
import nl.avans.mbda.weatherapp.common.utils.MenuUtil;
import nl.avans.mbda.weatherapp.databinding.ActivityDetailedWeatherBinding;
import nl.avans.mbda.weatherapp.models.Converter;
import nl.avans.mbda.weatherapp.models.onecall.OneCall;
import nl.avans.mbda.weatherapp.viewmodels.ForecastViewModel;

public class DetailedWeatherActivity extends AppCompatActivity {

    public static final String NAME_ONE_CALL = "OneCall";
    public static final String NAME_SELECTED_ITEM = "SelectedItem";

    private ForecastViewModel viewModel;
    private BackgroundUtil backgroundUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        nl.avans.mbda.weatherapp.databinding.ActivityDetailedWeatherBinding binding = ActivityDetailedWeatherBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        backgroundUtil = new BackgroundUtil(this, binding.getRoot());

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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detailed_weather, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        if (MenuUtil.openMaps(this, item.getItemId(), viewModel.getOneCall().getValue())) {
            return true;
        }
        if (MenuUtil.changeBackground(backgroundUtil, item.getItemId())) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}