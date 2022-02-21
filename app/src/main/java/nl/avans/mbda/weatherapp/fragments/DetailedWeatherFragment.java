package nl.avans.mbda.weatherapp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import nl.avans.mbda.weatherapp.databinding.FragmentDetailedWeatherBinding;
import nl.avans.mbda.weatherapp.models.onecall.Daily;

public class DetailedWeatherFragment extends Fragment {

    private WeatherViewModel viewModel;
    private FragmentDetailedWeatherBinding binding;
    private Daily daily;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentDetailedWeatherBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(WeatherViewModel.class);
        viewModel.getSelectedItem().observe(getViewLifecycleOwner(), selectedItem -> {
            daily = viewModel.getOneCall().getValue().getDaily().get(selectedItem);
        });
    }
}