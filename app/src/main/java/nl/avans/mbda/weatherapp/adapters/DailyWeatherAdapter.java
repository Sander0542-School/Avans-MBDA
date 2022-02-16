package nl.avans.mbda.weatherapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import nl.avans.mbda.weatherapp.R;

public class DailyWeatherAdapter extends RecyclerView.Adapter<DailyWeatherAdapter.Holder> {

    private final List<Object> forecastList;

    public DailyWeatherAdapter(List<Object> forecastList) {
        this.forecastList = forecastList;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_daily_weather, parent, false);
        return new Holder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        final Object forecast = forecastList.get(position);
    }

    @Override
    public int getItemCount() {
        return forecastList.size();
    }

    class Holder extends RecyclerView.ViewHolder {

        public Holder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
