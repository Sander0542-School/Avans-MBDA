package nl.avans.mbda.weatherapp.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import nl.avans.mbda.weatherapp.R;
import nl.avans.mbda.weatherapp.databinding.ItemDailyWeatherBinding;
import nl.avans.mbda.weatherapp.models.Daily;

public class DailyWeatherAdapter extends RecyclerView.Adapter<DailyWeatherAdapter.Holder> {

    private static final String FORMAT_DAY = "EE";
    private static final String FORMAT_TEMP = "%.1f°";
    private static final String FORMAT_TEMP_FEEL = "RealFeel " + FORMAT_TEMP;
    private static final String FORMAT_RAIN = "%.1f mm";

    private final List<Daily> data = new ArrayList<>();

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(ItemDailyWeatherBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        final Daily daily = data.get(position);

        Context context = holder.itemView.getContext();
        Resources resources = holder.itemView.getResources();
        Locale locale = resources.getConfiguration().getLocales().get(0);

        final int resourceId = resources.getIdentifier(String.format("weather_%s", daily.getWeather().get(0).getIcon()), "drawable", context.getPackageName());

        holder.binding.weatherIcon.setImageDrawable(ResourcesCompat.getDrawable(resources, resourceId, context.getTheme()));
        holder.binding.weatherDay.setText(new SimpleDateFormat(FORMAT_DAY, locale).format(new Date(daily.getDt() * 1000)));
        holder.binding.weatherTempDay.setText(String.format(locale, FORMAT_TEMP, daily.getTemp().getDay()));
        holder.binding.weatherFeelDay.setText(String.format(locale, FORMAT_TEMP_FEEL, daily.getFeelsLike().getDay()));
        holder.binding.weatherRain.setText(String.format(locale, FORMAT_RAIN, daily.getRain() == null ? 0 : daily.getRain()));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<Daily> data) {
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DailyDiffCallback(this.data, data));

        this.data.clear();
        this.data.addAll(data);
        result.dispatchUpdatesTo(this);
    }

    static class Holder extends RecyclerView.ViewHolder {
        private final ItemDailyWeatherBinding binding;

        public Holder(@NonNull ItemDailyWeatherBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }
    }

    static class DailyDiffCallback extends DiffUtil.Callback {
        DailyDiffCallback(List<Daily> oldForecast, List<Daily> newForecast) {
            this.oldForecast = oldForecast;
            this.newForecast = newForecast;
        }

        private final List<Daily> oldForecast, newForecast;

        @Override
        public int getOldListSize() {
            return oldForecast.size();
        }

        @Override
        public int getNewListSize() {
            return newForecast.size();
        }

        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            return oldForecast.get(oldItemPosition).getDt() == newForecast.get(newItemPosition).getDt();
        }

        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            final Daily oldItem = oldForecast.get(oldItemPosition);
            final Daily newItem = newForecast.get(newItemPosition);

            return oldItem.getTemp().getDay() == newItem.getTemp().getDay() &&
                    oldItem.getFeelsLike().getDay() == newItem.getFeelsLike().getDay() &&
                    oldItem.getRain() == newItem.getRain();
        }
    }
}
