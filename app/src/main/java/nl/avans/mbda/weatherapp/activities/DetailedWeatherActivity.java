package nl.avans.mbda.weatherapp.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.io.IOException;
import java.util.Locale;

import nl.avans.mbda.weatherapp.R;
import nl.avans.mbda.weatherapp.databinding.ActivityDetailedWeatherBinding;
import nl.avans.mbda.weatherapp.models.Converter;
import nl.avans.mbda.weatherapp.models.onecall.OneCall;
import nl.avans.mbda.weatherapp.viewmodels.ForecastViewModel;

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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        if (item.getItemId() == R.id.menu_item_maps) {
            OneCall oneCall = viewModel.getOneCall().getValue();
            if (oneCall == null) return true;
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(String.format(Locale.US, "google.streetview:cbll=%f,%f", oneCall.getLat(), oneCall.getLon())));
            intent.setPackage("com.google.android.apps.maps");
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}