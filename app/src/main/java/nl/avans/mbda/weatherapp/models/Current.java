package nl.avans.mbda.weatherapp.models;

import com.fasterxml.jackson.annotation.*;
import java.util.List;

public class Current {
    private long dt;
    private long sunrise;
    private long sunset;
    private double temp;
    private double feelsLike;
    private long pressure;
    private long humidity;
    private double dewPoint;
    private double uvi;
    private long clouds;
    private long visibility;
    private double windSpeed;
    private long windDeg;
    private double windGust;
    private List<Weather> weather;

    @JsonProperty("dt")
    public long getDt() { return dt; }
    @JsonProperty("dt")
    public void setDt(long value) { this.dt = value; }

    @JsonProperty("sunrise")
    public long getSunrise() { return sunrise; }
    @JsonProperty("sunrise")
    public void setSunrise(long value) { this.sunrise = value; }

    @JsonProperty("sunset")
    public long getSunset() { return sunset; }
    @JsonProperty("sunset")
    public void setSunset(long value) { this.sunset = value; }

    @JsonProperty("temp")
    public double getTemp() { return temp; }
    @JsonProperty("temp")
    public void setTemp(double value) { this.temp = value; }

    @JsonProperty("feels_like")
    public double getFeelsLike() { return feelsLike; }
    @JsonProperty("feels_like")
    public void setFeelsLike(double value) { this.feelsLike = value; }

    @JsonProperty("pressure")
    public long getPressure() { return pressure; }
    @JsonProperty("pressure")
    public void setPressure(long value) { this.pressure = value; }

    @JsonProperty("humidity")
    public long getHumidity() { return humidity; }
    @JsonProperty("humidity")
    public void setHumidity(long value) { this.humidity = value; }

    @JsonProperty("dew_point")
    public double getDewPoint() { return dewPoint; }
    @JsonProperty("dew_point")
    public void setDewPoint(double value) { this.dewPoint = value; }

    @JsonProperty("uvi")
    public double getUvi() { return uvi; }
    @JsonProperty("uvi")
    public void setUvi(double value) { this.uvi = value; }

    @JsonProperty("clouds")
    public long getClouds() { return clouds; }
    @JsonProperty("clouds")
    public void setClouds(long value) { this.clouds = value; }

    @JsonProperty("visibility")
    public long getVisibility() { return visibility; }
    @JsonProperty("visibility")
    public void setVisibility(long value) { this.visibility = value; }

    @JsonProperty("wind_speed")
    public double getWindSpeed() { return windSpeed; }
    @JsonProperty("wind_speed")
    public void setWindSpeed(double value) { this.windSpeed = value; }

    @JsonProperty("wind_deg")
    public long getWindDeg() { return windDeg; }
    @JsonProperty("wind_deg")
    public void setWindDeg(long value) { this.windDeg = value; }

    @JsonProperty("wind_gust")
    public double getWindGust() { return windGust; }
    @JsonProperty("wind_gust")
    public void setWindGust(double value) { this.windGust = value; }

    @JsonProperty("weather")
    public List<Weather> getWeather() { return weather; }
    @JsonProperty("weather")
    public void setWeather(List<Weather> value) { this.weather = value; }
}
