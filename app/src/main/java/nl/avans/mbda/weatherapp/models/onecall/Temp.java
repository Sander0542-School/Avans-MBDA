package nl.avans.mbda.weatherapp.models.onecall;

import com.fasterxml.jackson.annotation.*;

public class Temp {
    private double day;
    private double min;
    private double max;
    private double night;
    private double eve;
    private double morn;

    @JsonProperty("day")
    public double getDay() { return day; }
    @JsonProperty("day")
    public void setDay(double value) { this.day = value; }

    @JsonProperty("min")
    public double getMin() { return min; }
    @JsonProperty("min")
    public void setMin(double value) { this.min = value; }

    @JsonProperty("max")
    public double getMax() { return max; }
    @JsonProperty("max")
    public void setMax(double value) { this.max = value; }

    @JsonProperty("night")
    public double getNight() { return night; }
    @JsonProperty("night")
    public void setNight(double value) { this.night = value; }

    @JsonProperty("eve")
    public double getEve() { return eve; }
    @JsonProperty("eve")
    public void setEve(double value) { this.eve = value; }

    @JsonProperty("morn")
    public double getMorn() { return morn; }
    @JsonProperty("morn")
    public void setMorn(double value) { this.morn = value; }
}
