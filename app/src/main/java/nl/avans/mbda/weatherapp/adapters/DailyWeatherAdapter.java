package nl.avans.mbda.weatherapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import nl.avans.mbda.weatherapp.R;
import nl.avans.mbda.weatherapp.models.Daily;
import nl.avans.mbda.weatherapp.models.Temp;

public class DailyWeatherAdapter extends RecyclerView.Adapter<DailyWeatherAdapter.Holder> {

    private static final String FORMAT_DAY = "EE";
    private static final String FORMAT_TEMP = "%.1f°";
    private static final String FORMAT_TEMP_FEEL = "RealFeel " + FORMAT_TEMP;
    private static final String FORMAT_RAIN = "%.2f mm";

    private final List<Daily> forecastList;

    public DailyWeatherAdapter(List<Daily> forecastList) {
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
        Locale locale = holder.itemView.getContext().getResources().getConfiguration().getLocales().get(0);

        final Daily daily = forecastList.get(position);
        final Temp temp = daily.getTemp();

//        holder.weatherIcon.
        holder.weatherDay.setText(new SimpleDateFormat(FORMAT_DAY, locale).format(new Date(daily.getDt() * 1000)));
        holder.weatherTempDay.setText(String.format(locale, FORMAT_TEMP, daily.getTemp().getDay()));
        holder.weatherFeelDay.setText(String.format(locale, FORMAT_TEMP_FEEL, daily.getFeelsLike().getDay()));
        holder.weatherRain.setText(String.format(locale, FORMAT_RAIN, daily.getRain() == null ? 0 : daily.getRain()));
    }

    @Override
    public int getItemCount() {
        return forecastList.size();
    }

    public void setItems(List<Daily> forecastList) {
        this.forecastList.clear();
        this.forecastList.addAll(forecastList);
        this.notifyDataSetChanged();
    }

    class Holder extends RecyclerView.ViewHolder {
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
}
