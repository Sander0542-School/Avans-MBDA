package nl.avans.mbda.weatherapp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import nl.avans.mbda.weatherapp.adapters.DailyWeatherAdapter;
import nl.avans.mbda.weatherapp.common.listeners.RecyclerItemClickListener;
import nl.avans.mbda.weatherapp.databinding.FragmentDailyWeatherBinding;
import nl.avans.mbda.weatherapp.models.onecall.OneCall;
import nl.avans.mbda.weatherapp.viewmodels.ForecastViewModel;

public class DailyWeatherFragment extends Fragment {

    private FragmentDailyWeatherBinding binding;

    private ForecastViewModel viewModel;

    private DailyWeatherAdapter dailyWeatherAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentDailyWeatherBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dailyWeatherAdapter = new DailyWeatherAdapter();

        viewModel = new ViewModelProvider(requireActivity()).get(ForecastViewModel.class);
        viewModel.getOneCall().observe(getViewLifecycleOwner(), oneCall -> dailyWeatherAdapter.setData(oneCall.getDaily()));
        viewModel.getRefreshing().observe(getViewLifecycleOwner(), refreshing -> binding.swipeLayout.setRefreshing(refreshing));
        OneCall oneCall = viewModel.getOneCall().getValue();
        if (oneCall != null) {
            dailyWeatherAdapter.setData(oneCall.getDaily());
        }

        binding.swipeLayout.setOnRefreshListener(() -> viewModel.setRefreshing(true));

        binding.listDailyWeather.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.listDailyWeather.setAdapter(dailyWeatherAdapter);
        binding.listDailyWeather.addOnItemTouchListener(new RecyclerItemClickListener(this.getContext(), binding.listDailyWeather, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                viewModel.setSelectedItem(position);
            }

            @Override
            public void onLongItemClick(View view, int position) {
                Toast.makeText(DailyWeatherFragment.this.getContext(), String.valueOf(position), Toast.LENGTH_SHORT).show();
            }
        }));
    }
}