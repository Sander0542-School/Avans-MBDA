package nl.avans.mbda.weatherapp.models.onecall;

import com.fasterxml.jackson.annotation.*;
import java.util.List;

public class Daily {
    private long dt;
    private long sunrise;
    private long sunset;
    private long moonrise;
    private long moonset;
    private double moonPhase;
    private Temp temp;
    private FeelsLike feelsLike;
    private long pressure;
    private long humidity;
    private double dewPoint;
    private double windSpeed;
    private long windDeg;
    private double windGust;
    private List<Weather> weather;
    private long clouds;
    private double pop;
    private Double rain;
    private double uvi;
    private Double snow;

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

    @JsonProperty("moonrise")
    public long getMoonrise() { return moonrise; }
    @JsonProperty("moonrise")
    public void setMoonrise(long value) { this.moonrise = value; }

    @JsonProperty("moonset")
    public long getMoonset() { return moonset; }
    @JsonProperty("moonset")
    public void setMoonset(long value) { this.moonset = value; }

    @JsonProperty("moon_phase")
    public double getMoonPhase() { return moonPhase; }
    @JsonProperty("moon_phase")
    public void setMoonPhase(double value) { this.moonPhase = value; }

    @JsonProperty("temp")
    public Temp getTemp() { return temp; }
    @JsonProperty("temp")
    public void setTemp(Temp value) { this.temp = value; }

    @JsonProperty("feels_like")
    public FeelsLike getFeelsLike() { return feelsLike; }
    @JsonProperty("feels_like")
    public void setFeelsLike(FeelsLike value) { this.feelsLike = value; }

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

    @JsonProperty("clouds")
    public long getClouds() { return clouds; }
    @JsonProperty("clouds")
    public void setClouds(long value) { this.clouds = value; }

    @JsonProperty("pop")
    public double getPop() { return pop; }
    @JsonProperty("pop")
    public void setPop(double value) { this.pop = value; }

    @JsonProperty("rain")
    public Double getRain() { return rain; }
    @JsonProperty("rain")
    public void setRain(Double value) { this.rain = value; }

    @JsonProperty("uvi")
    public double getUvi() { return uvi; }
    @JsonProperty("uvi")
    public void setUvi(double value) { this.uvi = value; }

    @JsonProperty("snow")
    public Double getSnow() { return snow; }
    @JsonProperty("snow")
    public void setSnow(Double value) { this.snow = value; }
}
