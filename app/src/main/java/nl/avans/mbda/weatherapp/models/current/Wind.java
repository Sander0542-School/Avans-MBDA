package nl.avans.mbda.weatherapp.models.current;

import com.fasterxml.jackson.annotation.*;

public class Wind {
    private double speed;
    private long deg;
    private double gust;

    @JsonProperty("speed")
    public double getSpeed() { return speed; }
    @JsonProperty("speed")
    public void setSpeed(double value) { this.speed = value; }

    @JsonProperty("deg")
    public long getDeg() { return deg; }
    @JsonProperty("deg")
    public void setDeg(long value) { this.deg = value; }

    @JsonProperty("gust")
    public double getGust() { return gust; }
    @JsonProperty("gust")
    public void setGust(double value) { this.gust = value; }
}
