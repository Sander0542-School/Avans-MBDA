package nl.avans.mbda.weatherapp.models;

import com.fasterxml.jackson.annotation.*;
import java.util.List;

public class OneCall {
    private double lat;
    private double lon;
    private String timezone;
    private long timezoneOffset;
    private Current current;
    private List<Daily> daily;

    @JsonProperty("lat")
    public double getLat() { return lat; }
    @JsonProperty("lat")
    public void setLat(double value) { this.lat = value; }

    @JsonProperty("lon")
    public double getLon() { return lon; }
    @JsonProperty("lon")
    public void setLon(double value) { this.lon = value; }

    @JsonProperty("timezone")
    public String getTimezone() { return timezone; }
    @JsonProperty("timezone")
    public void setTimezone(String value) { this.timezone = value; }

    @JsonProperty("timezone_offset")
    public long getTimezoneOffset() { return timezoneOffset; }
    @JsonProperty("timezone_offset")
    public void setTimezoneOffset(long value) { this.timezoneOffset = value; }

    @JsonProperty("current")
    public Current getCurrent() { return current; }
    @JsonProperty("current")
    public void setCurrent(Current value) { this.current = value; }

    @JsonProperty("daily")
    public List<Daily> getDaily() { return daily; }
    @JsonProperty("daily")
    public void setDaily(List<Daily> value) { this.daily = value; }
}
