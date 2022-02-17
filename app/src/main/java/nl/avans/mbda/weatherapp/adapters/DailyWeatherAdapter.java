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
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_daily_weather, parent, false);
        return new Holder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        final Daily daily = data.get(position);

        Context context = holder.itemView.getContext();
        Resources resources = holder.itemView.getResources();
        Locale locale = resources.getConfiguration().getLocales().get(0);

        final int resourceId = resources.getIdentifier(String.format("weather_%s", daily.getWeather().get(0).getIcon()), "drawable", context.getPackageName());

        holder.weatherIcon.setImageDrawable(ResourcesCompat.getDrawable(resources, resourceId, context.getTheme()));
        holder.weatherDay.setText(new SimpleDateFormat(FORMAT_DAY, locale).format(new Date(daily.getDt() * 1000)));
        holder.weatherTempDay.setText(String.format(locale, FORMAT_TEMP, daily.getTemp().getDay()));
        holder.weatherFeelDay.setText(String.format(locale, FORMAT_TEMP_FEEL, daily.getFeelsLike().getDay()));
        holder.weatherRain.setText(String.format(locale, FORMAT_RAIN, daily.getRain() == null ? 0 : daily.getRain()));
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
        private final TextView weatherDay;
        private final ImageView weatherIcon;
        private final TextView weatherTempDay;
        private final TextView weatherFeelDay;
        private final TextView weatherRain;

        public Holder(@NonNull View itemView) {
            super(itemView);

            weatherDay = itemView.findViewById(R.id.weather_day);
            weatherIcon = itemView.findViewById(R.id.weather_icon);
            weatherTempDay = itemView.findViewById(R.id.weather_temp_day);
            weatherFeelDay = itemView.findViewById(R.id.weather_feel_day);
            weatherRain = itemView.findViewById(R.id.weather_rain);
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
